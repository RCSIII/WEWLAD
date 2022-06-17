package me.wewlad.Blocks;

import me.wewlad.WEWLAD;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class WEWBlocks {
    public static final DeferredRegister<Block> WBLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, WEWLAD.MODID);

    public static void register(IEventBus iebus){
        WBLOCKS.register(iebus);
    }
}
