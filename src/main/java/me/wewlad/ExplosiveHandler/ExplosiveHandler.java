package me.wewlad.ExplosiveHandler;

import me.wewlad.Blocks.ExplosiveBlocks.ExplosiveType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;

public class ExplosiveHandler {
    public static void HandleExplosion(Entity expEntity, Level level, double x, double y, double z, ExplosiveType expType, String expModifiers){
        switch (expType){
            case DOUBLETNT:
                doubleTNTExplosion(expEntity, level, x, y, z);
        }
    }

    private static void doubleTNTExplosion(Entity expEntity, Level level, double x, double y, double z){
        level.explode(expEntity, x, y, z, 16.0F, Explosion.BlockInteraction.BREAK);
    }
}
