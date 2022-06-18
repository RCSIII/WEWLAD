package me.wewlad.Blocks;

import me.wewlad.Blocks.Explosives.BaseExplosiveBlock;
import me.wewlad.Blocks.Explosives.ExplosiveType;
import me.wewlad.Items.WEWCreativeModeTab;
import me.wewlad.Items.WEWItems;
import me.wewlad.WEWLAD;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class WEWBlocks {
    public static final DeferredRegister<Block> WBLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, WEWLAD.MODID);

    public static final RegistryObject<Block> TUNGSTEN_BLOCK = registerBlock("tungsten_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL).strength(9f).requiresCorrectToolForDrops()), WEWCreativeModeTab.WEWTAB);
    public static final RegistryObject<Block> TUNGSTEN_ORE = registerBlock("tungsten_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(5f).requiresCorrectToolForDrops()), WEWCreativeModeTab.WEWTAB);
    public static final RegistryObject<Block> DEEPSLATE_TUNGSTEN_ORE = registerBlock("deepslate_tungsten_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(6f).requiresCorrectToolForDrops()), WEWCreativeModeTab.WEWTAB);
    public static final RegistryObject<Block> BASE_EXPLOSIVE = registerBlock("base_explosive", () -> new BaseExplosiveBlock(BlockBehaviour.Properties.of(Material.EXPLOSIVE).strength(1f), ExplosiveType.DOUBLETNT), WEWCreativeModeTab.WEWTAB);
    private static <T extends Block> RegistryObject<T> registerBlock(String bName, Supplier<T> block, CreativeModeTab tab){
        RegistryObject<T> ret = WBLOCKS.register(bName, block);
        registerBlockItem(bName, ret, tab);
        return ret;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String iName, RegistryObject<T> block, CreativeModeTab tab){
        return WEWItems.WITEMS.register(iName, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String iName, RegistryObject<T> block, CreativeModeTab tab, String toolTipKey){
        return WEWItems.WITEMS.register(iName, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)){
            @Override
            public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pTooltipFlag){
                pTooltip.add(new TranslatableComponent(toolTipKey));
            }
        });
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String bName, Supplier<T> block, CreativeModeTab tab, String toolTipKey){
        RegistryObject<T> ret = WBLOCKS.register(bName, block);
        registerBlockItem(bName, ret, tab, toolTipKey);
        return ret;
    }

    public static void register(IEventBus iebus){
        WBLOCKS.register(iebus);
    }
}
