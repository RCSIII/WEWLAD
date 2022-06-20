package me.wewlad.ExplosiveHandler;

import com.mojang.logging.LogUtils;
import me.wewlad.Blocks.ExplosiveBlocks.ExplosiveType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;
import javax.annotation.Nullable;

public class ExplosiveHandler {

    private static final Logger LOGGER = LogUtils.getLogger();

    public static void HandleExplosion(@Nullable Entity expEntity, Level level, double x, double y, double z, ExplosiveType expType, String expModifiers){
        switch (expType){
            case DOUBLETNT:
                doubleTNTExplosion(expEntity, level, x, y, z);
        }
    }

    private static void doubleTNTExplosion(@Nullable Entity expEntity, Level level, double x, double y, double z){
        fastExplode1(expEntity, level, x, y, z, 8, Explosion.BlockInteraction.DESTROY);
    }

    private static void fastExplode1(@Nullable Entity expEntity, Level level, double x, double y, double z, int radius, Explosion.BlockInteraction interaction){
        boolean b2d[][] = new boolean[radius*2+1][radius*2+1];
        int relativeCenterX = radius;
        int relativeCenterZ = radius;
        double angle;
        int approxX;
        BlockPos newPos, originPos = new BlockPos(x,y,z);
        BlockState lbs;

        if(radius == 0){
            return;
        }

        for(int h = 0; h <= Math.round(radius*0.8); h++){
            angle = Math.sinh((double) h/radius);
            approxX = (int) Math.round(Math.cos(angle)*radius);
            b2d[relativeCenterX+approxX][relativeCenterZ+h] = true;
            b2d[relativeCenterX-approxX][relativeCenterZ-h] = true;
            b2d[relativeCenterZ+h][relativeCenterX+approxX] = true;
            b2d[relativeCenterZ-h][relativeCenterX-approxX] = true;
            b2d[relativeCenterX-approxX][relativeCenterZ+h] = true;
            b2d[relativeCenterX+approxX][relativeCenterZ-h] = true;
            b2d[relativeCenterZ+h][relativeCenterX-approxX] = true;
            b2d[relativeCenterZ-h][relativeCenterX+approxX] = true;
        }

        for(int ax = 0; ax < radius*2+1; ax++){
            for(int az = 0; az < radius*2+1; az++) {
                if(b2d[ax][az]){
                    while(az <= radius){
                        b2d[ax][az] = true;
                        b2d[ax][radius*2-az] = true;
                        az++;
                    }
                    break;
                }
            }

        }

        fastExplode1(expEntity, level, x, y-1, z, radius-1, interaction);

        for(int ax = 0; ax < radius*2+1; ax++){
            for(int az = 0; az < radius*2+1; az++){
                if(b2d[ax][az]) {
                    newPos = originPos.offset(ax-radius,0,az-radius);
                    lbs = level.getBlockState(newPos);
                    lbs.onBlockExploded(level, newPos, null);
                }
            }
        }
    }

    private static void fastExplode2(@Nullable Entity expEntity, Level level, double x, double y, double z, Explosion.BlockInteraction interaction){

    }
}
