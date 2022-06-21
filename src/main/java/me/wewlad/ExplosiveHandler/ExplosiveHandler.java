package me.wewlad.ExplosiveHandler;

import com.mojang.logging.LogUtils;
import me.wewlad.Blocks.ExplosiveBlocks.ExplosiveType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;
import javax.annotation.Nullable;
import java.util.ArrayList;

public class ExplosiveHandler {
    public static int[][] switchingArray = {
            {1,1,1},
            {-1,1,1},
            {1,1,-1},
            {-1,1,-1},
            {1,-1,1},
            {-1,-1,1},
            {1,-1,-1},
            {-1,-1,-1}
    };

    private static final Logger LOGGER = LogUtils.getLogger();

    public static void HandleExplosion(@Nullable Entity expEntity, Level level, double x, double y, double z, ExplosiveType expType, String expModifiers){
        switch (expType){
            case DOUBLETNT:
                doubleTNTExplosion(expEntity, level, x, y, z);
        }
    }

    private static void doubleTNTExplosion(@Nullable Entity expEntity, Level level, double x, double y, double z){
        fastCircularExplode(expEntity, level, new BlockPos(x,y,z),100, Explosion.BlockInteraction.DESTROY, 1, 0);
    }

    private static void fastCircularExplode(@Nullable Entity expEntity, Level level, BlockPos expOrigin, int radius, Explosion.BlockInteraction interaction, int expShape, int iteration){
        int nRadius = 0;
        switch (expShape){
            case 0: nRadius = radius;
                    break;
            case 1: nRadius = radius - iteration;
                    break;
            case 2:
                double angle = Math.sinh((double) iteration/radius);
                nRadius = (int) Math.round(Math.cos(angle)*radius);
        }
        double angle;
        int approxXZ;

        if(iteration == radius){
            return;
        }

        for(int h = 0; h <= Math.round(nRadius*0.8); h++){
            angle = Math.sinh((double) h/nRadius);
            approxXZ = (int) Math.round(Math.cos(angle)*nRadius);
            for(int s = 0; s < switchingArray.length; s++) {
                for (int xzB = approxXZ; xzB >= 0; xzB--) {
                    level.setBlock(expOrigin.offset(xzB*switchingArray[s][0], iteration*switchingArray[s][1], h*switchingArray[s][2]), Blocks.AIR.defaultBlockState(), 3);
                    level.setBlock(expOrigin.offset(h*switchingArray[s][0], iteration*switchingArray[s][1], xzB*switchingArray[s][2]), Blocks.AIR.defaultBlockState(), 3);
                }
            }
        }

        fastCircularExplode(expEntity, level, expOrigin, radius, interaction, expShape, iteration+1);
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
