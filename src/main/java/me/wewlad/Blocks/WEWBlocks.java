package me.wewlad.Blocks;

import me.wewlad.Items.WEWItems;
import me.wewlad.WEWLAD;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class WEWBlocks {
    public static final DeferredRegister<Block> WBLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, WEWLAD.MODID);

    public static final RegistryObject<Block> TUNGSTEN_BLOCK = registerBlock("tungsten_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL).strength(9f).requiresCorrectToolForDrops()), CreativeModeTab.TAB_MATERIALS);

    private static <T extends Block> RegistryObject<T> registerBlock(String bName, Supplier<T> block, CreativeModeTab tab){
        RegistryObject<T> ret = WBLOCKS.register(bName, block);
        registerBlockItem(bName, ret, tab);
        return ret;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String iName, RegistryObject<T> block, CreativeModeTab tab){
        return WEWItems.WITEMS.register(iName, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus iebus){
        WBLOCKS.register(iebus);
    }
}
