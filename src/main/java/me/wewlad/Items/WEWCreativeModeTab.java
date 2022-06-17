package me.wewlad.Items;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class WEWCreativeModeTab {
    public static final CreativeModeTab WEWTAB = new CreativeModeTab("wewtab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(WEWItems.TUNGSTEN_INGOT.get());
        }
    };
}
