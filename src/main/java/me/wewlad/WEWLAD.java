package me.wewlad;

import com.mojang.logging.LogUtils;
import me.wewlad.Blocks.WEWBlocks;
import me.wewlad.Effect.WEWEffects;
import me.wewlad.Entities.ExplosiveBlocks.BaseExplosiveBlockRenderer;
import me.wewlad.Entities.WEWEntityTypes;
import me.wewlad.Items.WEWItems;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(WEWLAD.MODID)
public class WEWLAD {

    // Directly reference a slf4j logger
    public static final String MODID = "wewlad";
    private static final Logger LOGGER = LogUtils.getLogger();

    public WEWLAD() {
        // Register the setup method for mod-loading
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::clientSetup);
        // Register the enqueueIMC method for mod-loading
        modEventBus.addListener(this::enqueueIMC);
        // Register the processIMC method for mod-loading
        modEventBus.addListener(this::processIMC);

        WEWItems.register(modEventBus);
        WEWBlocks.register(modEventBus);
        WEWEntityTypes.register(modEventBus);
        WEWEffects.register(modEventBus);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        // Some pre init code
        LOGGER.info("HELLO FROM PRE INIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    private void clientSetup(final FMLCommonSetupEvent event){
        EntityRenderers.register(WEWEntityTypes.BASE_EXPLOSIVE_BLOCK.get(), BaseExplosiveBlockRenderer::new);
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        // Some example code to dispatch IMC to another mod
        InterModComms.sendTo("WEWLAD", "helloworld", () -> {
            LOGGER.info("Hello world from the MDK");
            return "Hello world";
        });
    }

    private void processIMC(final InterModProcessEvent event) {
        // Some example code to receive and process InterModCommunications from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m -> m.messageSupplier().get()).
                collect(Collectors.toList()));
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // Register a new block here
            LOGGER.info("HELLO from Register Block");
        }
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}
