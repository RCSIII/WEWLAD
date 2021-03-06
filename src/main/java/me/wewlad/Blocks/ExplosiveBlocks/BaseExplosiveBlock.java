package me.wewlad.Blocks.ExplosiveBlocks;

import me.wewlad.Entities.ExplosiveBlocks.BaseExplosiveBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Optional;

public class BaseExplosiveBlock extends Block {
    public static final BooleanProperty UNSTABLE = BlockStateProperties.UNSTABLE;
    public ExplosiveType expType;
    private VoxelShape voxelShape;

    public BaseExplosiveBlock(Properties properties, ExplosiveType explosiveType, VoxelShape shape) {
        super(properties);
        expType = explosiveType;
        voxelShape = shape;
        this.registerDefaultState(this.defaultBlockState().setValue(UNSTABLE, false));
    }

    public BaseExplosiveBlock(Properties properties, ExplosiveType explosiveType) {
        super(properties);
        expType = explosiveType;
        this.registerDefaultState(this.defaultBlockState().setValue(UNSTABLE, false));
    }

    @Override
    public void onCaughtFire(BlockState state, Level world, BlockPos pos, @Nullable net.minecraft.core.Direction face, @Nullable LivingEntity igniter) {
        primeExplosive(world, pos, igniter);
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        if (!pOldState.is(pState.getBlock())) {
            if (pLevel.hasNeighborSignal(pPos)) {
                onCaughtFire(pState, pLevel, pPos, null, null);
            }
        }
    }

    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        if (pLevel.hasNeighborSignal(pPos)) {
            onCaughtFire(pState, pLevel, pPos, null, null);
        }
    }

    @Override
    public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        if (!pLevel.isClientSide() && !pPlayer.isCreative() && pState.getValue(UNSTABLE)) {
            onCaughtFire(pState, pLevel, pPos, null, null);
        }

        super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
    }

    @Override
    public void wasExploded(Level pLevel, BlockPos pPos, Explosion pExplosion) {
        if (!pLevel.isClientSide) {
            BaseExplosiveBlockEntity primedExplosive = new BaseExplosiveBlockEntity(pLevel, (double)pPos.getX() + 0.5D, pPos.getY(), (double)pPos.getZ() + 0.5D, 80, expType);
            int i = primedExplosive.getFuse();
            primedExplosive.setFuse((short)(pLevel.random.nextInt(i / 4) + i / 8));
            pLevel.addFreshEntity(primedExplosive);
        }
    }

    public void primeExplosive(Level pLevel, BlockPos pPos, @Nullable LivingEntity pEntity) {
        if (!pLevel.isClientSide) {
            pLevel.removeBlock(pPos, false);
            BaseExplosiveBlockEntity primedExplosive = new BaseExplosiveBlockEntity(pLevel, (double)pPos.getX() + 0.5D, pPos.getY(), (double)pPos.getZ() + 0.5D, 80, expType);
            pLevel.addFreshEntity(primedExplosive);
            pLevel.playSound(null, primedExplosive.getX(), primedExplosive.getY(), primedExplosive.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
            pLevel.gameEvent(pEntity, GameEvent.PRIME_FUSE, pPos);
        }
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (!itemstack.is(Items.FLINT_AND_STEEL) && !itemstack.is(Items.FIRE_CHARGE)) {
            return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
        } else {
            onCaughtFire(pState, pLevel, pPos, pHit.getDirection(), pPlayer);
            pLevel.setBlock(pPos, Blocks.AIR.defaultBlockState(), 11);
            Item item = itemstack.getItem();
            if (!pPlayer.isCreative()) {
                if (itemstack.is(Items.FLINT_AND_STEEL)) {
                    itemstack.hurtAndBreak(1, pPlayer, (p_57425_) -> p_57425_.broadcastBreakEvent(pHand));
                } else {
                    itemstack.shrink(1);
                }
            }

            pPlayer.awardStat(Stats.ITEM_USED.get(item));
            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }
    }

    @Override
    public void onProjectileHit(Level pLevel, BlockState pState, BlockHitResult pHit, Projectile pProjectile) {
        if (!pLevel.isClientSide) {
            BlockPos blockpos = pHit.getBlockPos();
            Entity entity = pProjectile.getOwner();
            if (pProjectile.isOnFire() && pProjectile.mayInteract(pLevel, blockpos)) {
                onCaughtFire(pState, pLevel, blockpos, null, entity instanceof LivingEntity ? (LivingEntity)entity : null);
            }
        }

    }

    public static void explode(Entity pEntity, Level level, double x, double y, double z) {
        level.explode(pEntity, x, y, z, 0.0F, Explosion.BlockInteraction.NONE);
    }

    @Override
    public boolean dropFromExplosion(Explosion pExplosion) {
        return false;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(UNSTABLE);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return  voxelShape == null ? Shapes.block() : voxelShape;
    }
}
