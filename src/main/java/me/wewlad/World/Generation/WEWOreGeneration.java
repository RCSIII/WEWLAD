package me.wewlad.World.Generation;

import me.wewlad.World.Feature.WEWPlacedFeatures;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import java.util.List;

public class WEWOreGeneration {

    public static void generateOres(final BiomeLoadingEvent event){
        List<Holder<PlacedFeature>> base = event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES);

        base.add(WEWPlacedFeatures.TUNGSTEN_ORE_PLACED);
    }
}
