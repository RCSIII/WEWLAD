package me.wewlad.Effect;

import me.wewlad.WEWLAD;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class WEWEffects {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, WEWLAD.MODID);

    public static final RegistryObject<MobEffect> PHOSPHORUS = MOB_EFFECTS.register("phosphorus", () -> new PhosphorusEffect(MobEffectCategory.HARMFUL, 16777215));

    public static void register(IEventBus iEventBus){
        MOB_EFFECTS.register(iEventBus);
    }


}
