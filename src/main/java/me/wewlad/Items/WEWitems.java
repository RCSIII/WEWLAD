package me.wewlad.Items;

import me.wewlad.WEWLAD;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.TntBlock;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class WEWitems {
    public static final DeferredRegister<Item> WITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, WEWLAD.MODID);

    public static void register(IEventBus iebus){
        WITEMS.register(iebus);
    }
}
