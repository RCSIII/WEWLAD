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
import java.util.ArrayList;

public class ExplosiveHandler {

    private static final Logger LOGGER = LogUtils.getLogger();

    public static void HandleExplosion(@Nullable Entity expEntity, Level level, double x, double y, double z, ExplosiveType expType, String expModifiers){
        LOGGER.debug("yPos="+String.valueOf(y));
        switch (expType){
            case DOUBLETNT:
                doubleTNTExplosion(expEntity, level, x, y, z);
        }
    }

    private static void doubleTNTExplosion(@Nullable Entity expEntity, Level level, double x, double y, double z){
        fastExplode1(expEntity, level, x, y, z, 5, Explosion.BlockInteraction.DESTROY);
    }

    private static void fastExplode1(@Nullable Entity expEntity, Level level, double x, double y, double z, int radius, Explosion.BlockInteraction interaction){
        boolean b2d[][] = new boolean[radius*2+1][radius*2+1];
        int relativeCenterX = radius;
        int relativeCenterZ = radius;
        double angle;
        int approxX;
        BlockPos newPos, originPos = new BlockPos(x,y,z);
        BlockState lbs;

        for(int i = 0; i < radius*2+1; i++){
            b2d[radius][i] = true;
            b2d[i][radius] = true;
        }

        for(int h = 1; h < radius+1; h++){
            angle = Math.sinh((double) h/radius);
            approxX = (int) Math.round(Math.cos(angle)*radius);
            b2d[relativeCenterX+approxX][relativeCenterZ+h] = true;
            b2d[relativeCenterX-approxX][relativeCenterZ+h] = true;
            b2d[relativeCenterX+approxX][relativeCenterZ-h] = true;
            b2d[relativeCenterX-approxX][relativeCenterZ-h] = true;
        }


        for(int ax = 0; ax < radius*2+1; ax++){
            for(int az = 0; az < radius*2+1; az++){
                if(b2d[ax][az]) {
                    newPos = originPos.offset(ax-radius,y,az-radius);
                    lbs = level.getBlockState(newPos);
                    lbs.onBlockExploded(level, newPos, null);
                    LOGGER.debug(String.valueOf(b2d[ax][az]) + ":" + newPos.toString());
                }else{
                    LOGGER.debug(String.valueOf(b2d[ax][az]));
                }
            }
        }
    }

    private static void fastExplode2(@Nullable Entity expEntity, Level level, double x, double y, double z, int radius, Explosion.BlockInteraction interaction){
        ArrayList<Integer>[] blocks = new ArrayList[radius*radius];
        ArrayList<Integer>[] points;
        int  loops = 0, length = radius;

        while(length > 4)
        {
            length = length >> 1;
            loops++;
        }

        points = new ArrayList[2];
        points[0].add((int)x+radius);
        points[1].add((int)x+radius);
        points[0].add((int)z+radius);
        points[1].add((int)z+radius);

        for(int i = 0; i < loops; i++)
        {
            points = calculatePoints(points, loops-i);
        }


    }

    //add midpoints with their offsets to points
    private static ArrayList<Integer>[] calculatePoints(ArrayList<Integer>[] points, int offset)
    {
        ArrayList<Integer>[] midpoints = calculateMidpoints(points);
        for(int i = 0; i < midpoints.length; i++)
        {
            points[0].add(midpoints[0].get(i)+offset/2);
            points[1].add(midpoints[1].get(i)+offset-(offset/2));
        }

        return points;
    }

    //get midpoints of all existing points
    private static ArrayList<Integer>[] calculateMidpoints(ArrayList<Integer>[] points)
    {
        ArrayList<Integer>[] midPoints = new ArrayList[2];
        for(int i = 1; i < points.length; i++)
        {
            midPoints[0].add((points[0].get(i-1) + points[0].get(i))/2);
            midPoints[1].add((points[1].get(i-1) + points[1].get(i))/2);
        }

        return midPoints;
    }
}
