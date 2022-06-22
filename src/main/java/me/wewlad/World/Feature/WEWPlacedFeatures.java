package me.wewlad.World.Feature;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class WEWPlacedFeatures {

    public static final Holder<PlacedFeature> TUNGSTEN_ORE_PLACED = PlacementUtils.register("tungsten_ore_placed",
            WEWConfiguredFeatures.TUNGSTEN_ORE, WEWOrePlacement.commonOrePlacement(7,
                    HeightRangePlacement.triangle(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(80))));

    public static final Holder<PlacedFeature> SULFUR_ORE_PLACED = PlacementUtils.register("sulfur_ore_placed",
            WEWConfiguredFeatures.SULFUR_ORE, WEWOrePlacement.commonOrePlacement(7,
                    HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(40), VerticalAnchor.aboveBottom(100))));
}
