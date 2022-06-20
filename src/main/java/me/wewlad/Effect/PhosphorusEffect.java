package me.wewlad.Effect;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class PhosphorusEffect extends MobEffect {

    protected PhosphorusEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {

        if(!pLivingEntity.level.isClientSide()){
            pLivingEntity.hurt(new DamageSource("phosphorus"), 2F);
            pLivingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 80, 4));
            pLivingEntity.setSecondsOnFire(2);
        }
        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier){
        int j = 25 >> pAmplifier;
        if (j > 0) {
            return pDuration % j == 0;
        } else {
            return true;
        }
    }
}


