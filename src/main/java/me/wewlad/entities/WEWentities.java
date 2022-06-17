package me.wewlad.entities;

import me.wewlad.WEWLAD;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class WEWentities {
    public static final DeferredRegister<EntityType<?>> WENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, WEWLAD.MODID);

    public static void register(IEventBus iebus){
        WENTITIES.register(iebus);
    }
}
