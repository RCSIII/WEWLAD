package me.wewlad.Blocks.ExplosiveBlocks;

import me.wewlad.Entities.ExplosiveBlocks.BaseExplosiveBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

import javax.annotation.Nullable;

public class DoubleTNTBlock extends BaseExplosiveBlock{

    public DoubleTNTBlock(Properties properties) {
        super(properties, ExplosiveType.DOUBLETNT);
    }

}
