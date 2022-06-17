package me.wewlad.Items;

import me.wewlad.WEWLAD;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class WEWItems {
    public static final DeferredRegister<Item> WITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, WEWLAD.MODID);

    public static final RegistryObject<Item> TUNGSTEN_INGOT = WITEMS.register("tungsten_ingot",() -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)) );

    public static void register(IEventBus iebus){
        WITEMS.register(iebus);
    }
}
