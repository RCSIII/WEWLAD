package me.wewlad.Entities;

import me.wewlad.Entities.Explosives.BaseExplosiveEntity;
import me.wewlad.WEWLAD;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class WEWEntityTypes {
    public static final DeferredRegister<EntityType<?>> WENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, WEWLAD.MODID);

    public static final RegistryObject<EntityType<BaseExplosiveEntity>> BASE_EXPLOSIVE = WENTITIES.register("base_explosive", () -> EntityType.Builder.<BaseExplosiveEntity>of(BaseExplosiveEntity::new, MobCategory.MISC).sized(1.0f,1.0f).clientTrackingRange(12).fireImmune().build(new ResourceLocation(WEWLAD.MODID, "base_explosive").toString()));

    public static void register(IEventBus iebus){
        WENTITIES.register(iebus);
    }
}
