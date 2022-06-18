package me.wewlad.Entities.Explosives;

import me.wewlad.Blocks.Explosives.ExplosiveType;
import me.wewlad.Entities.WEWEntityTypes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class BaseExplosiveEntity extends Entity {

    private static final EntityDataAccessor<Integer> TIMER_ID = SynchedEntityData.defineId(BaseExplosiveEntity.class, EntityDataSerializers.INT);
    private static int expTime = 80;
    public ExplosiveType expType = ExplosiveType.NONE;
    public BaseExplosiveEntity(EntityType<? extends BaseExplosiveEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.blocksBuilding = true;

    }

    public BaseExplosiveEntity(Level level, double x, double y, double z, ExplosiveType explosiveType){
        this(WEWEntityTypes.BASE_EXPLOSIVE.get(), level);
        this.setPos(x, y, z);
        double d0 = level.random.nextDouble() * (double)((float)Math.PI * 2F);
        this.setDeltaMovement(-Math.sin(d0) * 0.02D, (double)0.2F, -Math.cos(d0) * 0.02D);
        this.setFuse(expTime);
        this.xo = x;
        this.yo = y;
        this.zo = z;
        expType = explosiveType;
    }

    public void tick() {
        if (!this.isNoGravity()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.04D, 0.0D));
        }

        this.move(MoverType.SELF, this.getDeltaMovement());
        this.setDeltaMovement(this.getDeltaMovement().scale(0.98D));
        if (this.onGround) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.7D, -0.5D, 0.7D));
        }

        int i = this.getFuse() - 1;
        this.setFuse(i);
        if (i <= 0) {
            this.discard();
            if (!this.level.isClientSide) {
                this.explode();
            }
        } else {
            this.updateInWaterStateAndDoFluidPushing();
            if (this.level.isClientSide) {
                this.level.addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.5D, this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }

    }

    protected void explode() {
        this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 8.0F, Explosion.BlockInteraction.BREAK);
    }

    public void setFuse(int time){ this.entityData.set(TIMER_ID, time); }

    public int getFuse(){ return this.entityData.get((TIMER_ID)); }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(TIMER_ID, 80);
    }

    protected Entity.MovementEmission getMovementEmission() {
        return Entity.MovementEmission.NONE;
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putShort("Fuse", (short)this.getFuse());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        this.setFuse(pCompound.getShort("Fuse"));
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

    public boolean isPickable() {
        return !this.isRemoved();
    }

}
