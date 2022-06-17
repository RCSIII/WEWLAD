package me.wewlad.World;

import me.wewlad.WEWLAD;
import me.wewlad.World.Generation.WEWOreGeneration;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = WEWLAD.MODID)
public class WEWWorldEvents {

    @SubscribeEvent
    public static void biomeLoadingEvent(final BiomeLoadingEvent event){
        WEWOreGeneration.generateOres(event);
    }
}
