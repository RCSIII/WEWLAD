package me.wewlad.ExplosiveHandler;

import com.mojang.logging.LogUtils;
import me.wewlad.Blocks.ExplosiveBlocks.ExplosiveType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.slf4j.Logger;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

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

    public static double calcDistance(BlockPos bpo, BlockPos bpt){
        return Math.sqrt(Math.pow(bpt.getX()-bpo.getX(), 2) + Math.pow(bpt.getY()-bpo.getY(), 2) + Math.pow(bpt.getZ()-bpo.getZ(), 2));
    }

    private static void doubleTNTExplosion(@Nullable Entity expEntity, Level level, double x, double y, double z){
        fastCircularExplodeFull(expEntity, level, new BlockPos(x,y,z),100, 2, 0);
    }

    private static void fastCircularExplodeStripped(@Nullable Entity expEntity, Level level, BlockPos expOrigin, int radius, int expShape, int iteration){
        int nRadius = 0;
        switch (expShape) {
            case 0 -> nRadius = radius;
            case 1 -> nRadius = radius - iteration;
            case 2 -> {
                double angle = Math.sinh((double) iteration / radius);
                nRadius = (int) Math.round(Math.cos(angle) * radius);
            }
        }
        double angle;
        int approxXZ;

        if(iteration == radius){
            return;
        }

        for(int h = 0; h <= Math.round(nRadius*0.75); h++){
            angle = Math.sinh((double) h/nRadius);
            approxXZ = (int) Math.round(Math.cos(angle)*nRadius);
            for(int s = 0; s < switchingArray.length; s++) {
                for (int xzB = approxXZ; xzB >= 0; xzB--) {
                    level.setBlock(expOrigin.offset(xzB * switchingArray[s][0], iteration * switchingArray[s][1], h * switchingArray[s][2]), Blocks.AIR.defaultBlockState(), 3);
                    level.setBlock(expOrigin.offset(h * switchingArray[s][0], iteration * switchingArray[s][1], xzB * switchingArray[s][2]), Blocks.AIR.defaultBlockState(), 3);
                }
            }
        }

        fastCircularExplodeStripped(expEntity, level, expOrigin, radius, expShape, iteration+1);
    }

    private static void fastCircularExplodeFull(@Nullable Entity expEntity, Level level, BlockPos expOrigin, int radius, int expShape, int iteration){
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
        double angle, dist, bExpRes;
        int approxXZ, exPower;
        BlockState stateCheck;
        List<Entity> entList;

        if(iteration == radius){
            entList = level.getEntities(expEntity,new AABB(expOrigin.getX()-radius,expOrigin.getY()-radius,expOrigin.getZ()-radius,
                    expOrigin.getX()+radius,expOrigin.getY()+radius,expOrigin.getZ()+radius));
            for(int i = 0; i < entList.size(); i++){
                dist = calcDistance(expOrigin, new BlockPos(entList.get(i).getX(),entList.get(i).getY(),entList.get(i).getZ()));
                entList.get(i).hurt(DamageSource.GENERIC,(float)(radius-dist));
            }
            return;
        }

        for(int h = 0; h <= Math.round(nRadius*0.75); h++){
            angle = Math.sinh((double) h/nRadius);
            approxXZ = (int) Math.round(Math.cos(angle)*nRadius);
            for(int s = 0; s < switchingArray.length; s++) {
                for (int xzB = approxXZ; xzB >= 0; xzB--) {
                    stateCheck = level.getBlockState(expOrigin.offset(xzB * switchingArray[s][0], iteration * switchingArray[s][1], h * switchingArray[s][2]));
                    bExpRes = stateCheck.getExplosionResistance(null,null,null);
                    dist = calcDistance(expOrigin,expOrigin.offset(xzB * switchingArray[s][0], iteration * switchingArray[s][1], h * switchingArray[s][2]));

                    exPower = (int) ((radius - dist + 1)*100);
                    if(exPower > bExpRes && !(xzB >= (approxXZ-1) && Math.random() <= 0.5)) {
                        stateCheck.onBlockExploded(level,expOrigin.offset(xzB * switchingArray[s][0], iteration * switchingArray[s][1], h * switchingArray[s][2]),null);
                    }
                    stateCheck = level.getBlockState(expOrigin.offset(h * switchingArray[s][0], iteration * switchingArray[s][1], xzB * switchingArray[s][2]));
                    bExpRes = stateCheck.getExplosionResistance(null,null,null);
                    dist = calcDistance(expOrigin,expOrigin.offset(h * switchingArray[s][0], iteration * switchingArray[s][1], xzB * switchingArray[s][2]));
                    exPower = (int) ((radius - dist + 1)*100);
                    if(exPower > bExpRes && !(xzB >= (approxXZ-1) && Math.random() <= 0.5)) {
                        stateCheck.onBlockExploded(level,expOrigin.offset(h * switchingArray[s][0], iteration * switchingArray[s][1], xzB * switchingArray[s][2]),null);
                    }
                }
            }
        }

        fastCircularExplodeFull(expEntity, level, expOrigin, radius, expShape, iteration+1);
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
