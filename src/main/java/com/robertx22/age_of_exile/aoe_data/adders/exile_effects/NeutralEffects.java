package com.robertx22.age_of_exile.aoe_data.adders.exile_effects;

import com.robertx22.age_of_exile.database.data.exile_effects.EffectType;
import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffectBuilder;
import com.robertx22.age_of_exile.database.data.exile_effects.VanillaStatData;
import com.robertx22.age_of_exile.database.data.spells.components.ComponentPart.PartBuilder;
import com.robertx22.age_of_exile.database.data.spells.components.Spell.Builder;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import net.minecraft.particle.ParticleTypes;

import java.util.UUID;

import static net.minecraft.entity.attribute.EntityAttributes.GENERIC_MOVEMENT_SPEED;

public class NeutralEffects implements ISlashRegistryInit {

    public static String GOOD_FOR_BOSS = "neutral/" + 0;
    public static String BAD_FOR_PLAYER = "neutral/" + 1;

    @Override
    public void registerAll() {

        ExileEffectBuilder.of(GOOD_FOR_BOSS, "Boss Prepare", EffectType.NEUTRAL)
            .vanillaStat(VanillaStatData.create(GENERIC_MOVEMENT_SPEED, -1, ModType.GLOBAL_INCREASE, UUID.fromString("bu9d32fa-c8c2-455c-92aa-4a94c2a70cd8")))
            .spell(Builder.forEffect()
                .onTick(PartBuilder.aoeParticles(ParticleTypes.HAPPY_VILLAGER, 10D, 1D)
                    .onTick(10D))
                .buildForEffect())
            .build();

        ExileEffectBuilder.of(BAD_FOR_PLAYER, "Boss Angry", EffectType.NEUTRAL)
            .vanillaStat(VanillaStatData.create(GENERIC_MOVEMENT_SPEED, -1, ModType.GLOBAL_INCREASE, UUID.fromString("bu9d32fa-c8c2-455c-92aa-4a94c2a70cd8")))
            .spell(Builder.forEffect()
                .onTick(PartBuilder.aoeParticles(ParticleTypes.WITCH, 10D, 1D)
                    .onTick(10D))
                .buildForEffect())
            .build();

    }
}
