package me.wewlad.Entities;

import me.wewlad.Entities.ExplosiveBlocks.BaseExplosiveBlockEntity;
import me.wewlad.WEWLAD;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class WEWEntityTypes {
    public static final DeferredRegister<EntityType<?>> WENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, WEWLAD.MODID);

    public static final RegistryObject<EntityType<BaseExplosiveBlockEntity>> BASE_EXPLOSIVE_BLOCK = WENTITIES.register("base_explosive", () -> EntityType.Builder.<BaseExplosiveBlockEntity>of(BaseExplosiveBlockEntity::new, MobCategory.MISC).sized(1.0f,1.0f).clientTrackingRange(12).fireImmune().build(new ResourceLocation(WEWLAD.MODID, "base_explosive").toString()));

    public static void register(IEventBus iebus){
        WENTITIES.register(iebus);
    }
}
