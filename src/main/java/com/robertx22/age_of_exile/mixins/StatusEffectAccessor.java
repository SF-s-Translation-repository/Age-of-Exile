package com.robertx22.age_of_exile.mixins;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(StatusEffect.class)
public interface StatusEffectAccessor {
    @Accessor
    StatusEffectType getType();
}
