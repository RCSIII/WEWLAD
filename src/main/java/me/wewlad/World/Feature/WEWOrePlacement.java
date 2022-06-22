package me.wewlad.World.Feature;

import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class WEWOrePlacement {

    public static List<PlacementModifier> orePlacement(PlacementModifier placementModifier, PlacementModifier p_195348_) {
        return List.of(placementModifier, InSquarePlacement.spread(), p_195348_, BiomeFilter.biome());
    }

    public static List<PlacementModifier> commonOrePlacement(int p_195344_, PlacementModifier placementModifier) {
        return orePlacement(CountPlacement.of(p_195344_), placementModifier);
    }

    public static List<PlacementModifier> rareOrePlacement(int p_195350_, PlacementModifier p_195351_) {
        return orePlacement(RarityFilter.onAverageOnceEvery(p_195350_), p_195351_);
    }
}
