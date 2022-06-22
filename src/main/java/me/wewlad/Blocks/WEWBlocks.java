package me.wewlad.Blocks;

import me.wewlad.Blocks.ExplosiveBlocks.DoubleTNTBlock;
import me.wewlad.Items.WEWCreativeModeTab;
import me.wewlad.Items.WEWItems;
import me.wewlad.WEWLAD;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class WEWBlocks {
    public static final DeferredRegister<Block> WBLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, WEWLAD.MODID);

    // Tungsten
    public static final RegistryObject<Block> TUNGSTEN_ORE = registerBlock("tungsten_ore", () -> new OreBlock(BlockBehaviour.Properties.of(Material.STONE).strength(5f, 5f).requiresCorrectToolForDrops()), WEWCreativeModeTab.WEWTAB);
    public static final RegistryObject<Block> DEEPSLATE_TUNGSTEN_ORE = registerBlock("deepslate_tungsten_ore", () -> new OreBlock(BlockBehaviour.Properties.copy(TUNGSTEN_ORE.get()).color(MaterialColor.DEEPSLATE).strength(6f, 5f).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)), WEWCreativeModeTab.WEWTAB);
    public static final RegistryObject<Block> TUNGSTEN_BLOCK = registerBlock("tungsten_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL).strength(9f, 9f).requiresCorrectToolForDrops()), WEWCreativeModeTab.WEWTAB);

    // Sulfur
    public static final RegistryObject<Block> SULFUR_ORE = registerBlock("sulfur_ore", () -> new OreBlock(BlockBehaviour.Properties.of(Material.STONE).strength(3f, 3f).requiresCorrectToolForDrops(), UniformInt.of(2, 5)), WEWCreativeModeTab.WEWTAB);
    public static final RegistryObject<Block> DEEPSLATE_SULFUR_ORE = registerBlock("deepslate_sulfur_ore", () -> new OreBlock(BlockBehaviour.Properties.copy(SULFUR_ORE.get()).color(MaterialColor.DEEPSLATE).strength(4f, 5f).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE), UniformInt.of(2, 5)), WEWCreativeModeTab.WEWTAB);
    public static final RegistryObject<Block> SULFUR_BLOCK = registerBlock("sulfur_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL).strength(5f, 5f).requiresCorrectToolForDrops()), WEWCreativeModeTab.WEWTAB);

    // Explosives
    public static final RegistryObject<Block> DOUBLE_TNT = registerBlock("double_tnt", () -> new DoubleTNTBlock(BlockBehaviour.Properties.of(Material.EXPLOSIVE).strength(1f).sound(SoundType.GRASS)), WEWCreativeModeTab.WEWTAB);
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
