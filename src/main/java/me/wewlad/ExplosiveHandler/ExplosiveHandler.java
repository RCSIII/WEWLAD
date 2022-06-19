package me.wewlad.ExplosiveHandler;

import me.wewlad.Blocks.ExplosiveBlocks.ExplosiveType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class ExplosiveHandler {
    public static void HandleExplosion(@Nullable Entity expEntity, Level level, double x, double y, double z, ExplosiveType expType, String expModifiers){
        switch (expType){
            case DOUBLETNT:
                doubleTNTExplosion(expEntity, level, x, y, z);
        }
    }

    private static void doubleTNTExplosion(@Nullable Entity expEntity, Level level, double x, double y, double z){
        level.explode(expEntity, x, y, z, 64.0F, Explosion.BlockInteraction.BREAK);
    }

    private static void fastExplode1(@Nullable Entity expEntity, Level level, double x, double y, double z, Explosion.BlockInteraction interaction){

    }

    private static void fastExplode2(@Nullable Entity expEntity, Level level, double x, double y, double z, Explosion.BlockInteraction interaction){

    }
}
