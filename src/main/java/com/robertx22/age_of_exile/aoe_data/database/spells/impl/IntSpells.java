package com.robertx22.age_of_exile.aoe_data.database.spells.impl;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.BeneficialEffects;
import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemTag;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.conditions.EffectCondition;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.saveclasses.spells.calc.ValueCalculationData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;

import java.util.Arrays;

import static com.robertx22.age_of_exile.mmorpg.ModRegistry.*;

public class IntSpells implements ISlashRegistryInit {

    public static String FROSTBALL_ID = "frostball";
    public static String FIREBALL_ID = "fireball";
    public static String POISONBALL_ID = "poison_ball";
    public static String THUNDERSPEAR_ID = "thunder_spear";
    public static String HEALING_AURA_ID = "healing_aura";
    public static String HEART_OF_ICE_ID = "heart_of_ice";

    static SpellConfiguration HIGH_AOE_LONG_CD() {
        return SpellConfiguration.Builder.nonInstant(30, 120 * 20, 40);
    }

    public static SpellConfiguration SINGLE_TARGET_PROJ_CONFIG() {
        return SpellConfiguration.Builder.instant(7, 20);
    }

    @Override
    public void registerAll() {

        SpellBuilder.of(FROSTBALL_ID, SpellConfiguration.Builder.instant(7, 20), "Ice Ball",
            Arrays.asList(SkillGemTag.PROJECTILE, SkillGemTag.DAMAGE))
            .weaponReq(CastingWeapon.MAGE_WEAPON)

            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.SNOWBALL, 1D, 0.5D, ENTITIES.SIMPLE_PROJECTILE, 60D, false)))
            .onTick(PartBuilder.particleOnTick(3D, ParticleTypes.ITEM_SNOWBALL, 3D, 0.15D))
            .onHit(PartBuilder.damage(ValueCalculationData.base(8), Elements.Water))

            .build();

        SpellBuilder.of(FIREBALL_ID, SpellConfiguration.Builder.instant(7, 20), "Fire Ball",
            Arrays.asList(SkillGemTag.PROJECTILE, SkillGemTag.DAMAGE))
            .weaponReq(CastingWeapon.MAGE_WEAPON)

            .onCast(PartBuilder.playSound(SoundEvents.ITEM_FIRECHARGE_USE, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.FIRE_CHARGE, 1D, 0.5D, ENTITIES.SIMPLE_PROJECTILE, 60D, false)))
            .onTick(PartBuilder.particleOnTick(3D, ParticleTypes.FLAME, 3D, 0.15D))
            .onHit(PartBuilder.damage(ValueCalculationData.base(8), Elements.Fire))

            .build();

        SpellBuilder.of(POISONBALL_ID, SpellConfiguration.Builder.instant(7, 20), "Poison Ball",
            Arrays.asList(SkillGemTag.PROJECTILE, SkillGemTag.DAMAGE))
            .weaponReq(CastingWeapon.MAGE_WEAPON)

            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.SLIME_BALL, 1D, 0.5D, ENTITIES.SIMPLE_PROJECTILE, 60D, false)))
            .onTick(PartBuilder.particleOnTick(3D, ParticleTypes.ITEM_SLIME, 3D, 0.15D))
            .onHit(PartBuilder.damage(ValueCalculationData.base(8), Elements.Nature))

            .build();

        SpellBuilder.of("thunder_storm", HIGH_AOE_LONG_CD(), "Thunderstorm",
            Arrays.asList(SkillGemTag.AREA, SkillGemTag.DAMAGE))
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(ENTITIES.SIMPLE_PROJECTILE, 100D, 4D)))
            .onTick(PartBuilder.tickCloudParticle(2D, ParticleTypes.CLOUD, 20D, 4D))
            .onTick(PartBuilder.tickCloudParticle(2D, ParticleTypes.FALLING_WATER, 20D, 4D))
            .onTick(PartBuilder.onTickDamageInAoe(20D, ValueCalculationData.base(2), Elements.Thunder, 4D)
                .addPerEntityHit(PartBuilder.empty()
                    .addActions(SpellAction.SUMMON_LIGHTNING_STRIKE.create())
                    .addCondition(EffectCondition.CHANCE.create(20D))))
            .build();

        SpellBuilder.of("whirlpool", SpellConfiguration.Builder.multiCast(30, 120 * 20, 60, 6), "Whirlpool",
            Arrays.asList(SkillGemTag.AREA, SkillGemTag.DAMAGE))
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.BLOCK_BUBBLE_COLUMN_BUBBLE_POP, 1D, 1D))
            .onCast(PartBuilder.groundParticles(ParticleTypes.BUBBLE, 200D, 3.5D, 0.5D))
            .onCast(PartBuilder.groundParticles(ParticleTypes.BUBBLE_POP, 250D, 3.5D, 0.5D))
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_DROWNED_HURT, 0.5D, 1D))
            .onCast(PartBuilder.damageInAoe(ValueCalculationData.base(3), Elements.Water, 3.5D)
                .addPerEntityHit(PartBuilder.playSoundPerTarget(SoundEvents.ENTITY_DROWNED_HURT, 1D, 1D)))
            .build();

        SpellBuilder.of("blizzard", HIGH_AOE_LONG_CD(), "Blizzard",
            Arrays.asList(SkillGemTag.AREA, SkillGemTag.DAMAGE))
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_EVOKER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(ENTITIES.SIMPLE_PROJECTILE, 100D, 4D)))
            .onTick(PartBuilder.tickCloudParticle(2D, ParticleTypes.CLOUD, 20D, 4D))
            .onTick(PartBuilder.tickCloudParticle(2D, ParticleTypes.ITEM_SNOWBALL, 20D, 4D))
            .onTick(PartBuilder.onTickDamageInAoe(20D, ValueCalculationData.base(2), Elements.Water, 4D))
            .build();
        SpellBuilder.of("teleport", SpellConfiguration.Builder.instant(20, 20 * 30), "Teleport",
            Arrays.asList(SkillGemTag.DAMAGE)
        )
            .onCast(PartBuilder.playSound(SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.TP_CASTER_IN_DIRECTION.create(12D)))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.WITCH, 30D, 2D))

            .onCast(PartBuilder.damageInAoe(ValueCalculationData.base(8), Elements.Elemental, 2D)
                .addPerEntityHit(PartBuilder.playSound(SoundEvents.ENTITY_ENDERMAN_HURT, 1D, 1D))
            )
            .onCast(PartBuilder.giveSelfExileEffect(BeneficialEffects.ELE_RESIST, 20 * 10D)
            )

            .build();
        SpellBuilder.of(HEART_OF_ICE_ID, SpellConfiguration.Builder.instant(15, 160 * 20), "Heart of Ice",
            Arrays.asList(SkillGemTag.HEALING))
            .weaponReq(CastingWeapon.ANY_WEAPON)
            .onCast(PartBuilder.playSound(SOUNDS.FREEZE, 1D, 1D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.CLOUD, 40D, 1.5D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.HEART, 12D, 1.5D))
            .onCast(PartBuilder.healCaster(ValueCalculationData.base(15)))
            .onCast(PartBuilder.addExileEffectToEnemiesInAoe(NegativeEffects.FROSTBURN, 5D, 20D * 10D))
            .build();

        // it falls into ground
        SpellBuilder.of("lightning_totem", SpellConfiguration.Builder.nonInstant(12, 45 * 20, 20), "Lightning Totem",
            Arrays.asList(SkillGemTag.AREA, SkillGemTag.DAMAGE))
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.TOTEM_OF_UNDYING, 1D, 0.5D, ENTITIES.SIMPLE_PROJECTILE, 120D, true)
                .put(MapField.EXPIRE_ON_HIT, false)))
            .onTick(PartBuilder.particleOnTick(20D, PARTICLES.THUNDER, 80D, 1.5D))
            .onTick(PartBuilder.playSoundEveryTicks(20D, SoundEvents.BLOCK_REDSTONE_TORCH_BURNOUT, 1D, 1D))
            .onTick(PartBuilder.onTickDamageInAoe(20D, ValueCalculationData.base(3), Elements.Thunder, 1.5D))
            .build();

        SpellBuilder.of(THUNDERSPEAR_ID, SINGLE_TARGET_PROJ_CONFIG(), "Thunder Spear",
            Arrays.asList(SkillGemTag.PROJECTILE, SkillGemTag.DAMAGE))
            .weaponReq(CastingWeapon.MAGE_WEAPON)

            .onCast(PartBuilder.playSound(SoundEvents.ITEM_TRIDENT_THROW, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.createTrident(1D, 1.25D, 80D)))
            .onHit(PartBuilder.damage(ValueCalculationData.base(6), Elements.Thunder))
            .onHit(PartBuilder.playSound(SoundEvents.ITEM_TRIDENT_HIT, 1D, 1D))
            .build();

        SpellBuilder.of("spear_of_judgement", SpellConfiguration.Builder.nonInstant(15, 20 * 45, 40), "Spear of Judgement",
            Arrays.asList(SkillGemTag.PROJECTILE, SkillGemTag.DAMAGE))
            .onCast(PartBuilder.playSound(SoundEvents.ITEM_TRIDENT_THROW, 1D, 1D))

            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.createTrident(1D, 1.25D, 80D)))
            .onHit(PartBuilder.damage(ValueCalculationData.base(6), Elements.Thunder))
            .onHit(PartBuilder.playSound(SoundEvents.ITEM_TRIDENT_HIT, 1D, 1D))
            .onHit(PartBuilder.addExileEffectToEnemiesInAoe(NegativeEffects.JUDGEMENT, 1D, 80D))
            .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.CLOUD, 15D, 0.015D))
            .build();

        SpellBuilder.of(HEALING_AURA_ID, SpellConfiguration.Builder.multiCast(15, 20 * 30, 60, 3), "Healing Atmosphere",
            Arrays.asList(SkillGemTag.HEALING))
            .weaponReq(CastingWeapon.ANY_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.ITEM_HOE_TILL, 1D, 1D))
            .onCast(PartBuilder.groundParticles(ParticleTypes.COMPOSTER, 50D, 2D, 0.2D))
            .onCast(PartBuilder.groundParticles(ParticleTypes.HEART, 20D, 2D, 0.2D))
            .onCast(PartBuilder.healInAoe(ValueCalculationData.base(4), 2D))
            .build();

        SpellBuilder.of("blazing_inferno", SpellConfiguration.Builder.multiCast(20, 20 * 30, 60, 3), "Ring of Fire",
            Arrays.asList(SkillGemTag.AREA, SkillGemTag.DAMAGE))
            .onCast(PartBuilder.playSound(SoundEvents.BLOCK_REDSTONE_TORCH_BURNOUT, 1D, 1D))
            .onCast(PartBuilder.groundEdgeParticles(ParticleTypes.FLAME, 100D, 2.8D, 0.2D))
            .onCast(PartBuilder.groundEdgeParticles(ParticleTypes.FLAME, 50D, 2D, 0.2D))
            .onCast(PartBuilder.groundEdgeParticles(ParticleTypes.FLAME, 25D, 1D, 0.2D))
            .onCast(PartBuilder.groundEdgeParticles(ParticleTypes.SMOKE, 200D, 3D, 0.2D))
            .onCast(PartBuilder.damageInAoe(ValueCalculationData.base(3), Elements.Fire, 3D))
            .build();

        SpellBuilder.of("awaken_mana", SpellConfiguration.Builder.instant(0, 300 * 20), "Awaken Mana",
            Arrays.asList(SkillGemTag.HEALING)
        )
            .weaponReq(CastingWeapon.ANY_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, 1D, 1D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.WITCH, 40D, 1.5D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.HEART, 12D, 1.5D))
            .onCast(PartBuilder.restoreManaToCaster(ValueCalculationData.base(30)))

            .build();
        SpellBuilder.of("meteor", SpellConfiguration.Builder.instant(18, 20 * 30), "Meteor",
            Arrays.asList(SkillGemTag.AREA, SkillGemTag.DAMAGE)
        )
            .weaponReq(CastingWeapon.MELEE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(ENTITIES.SIMPLE_PROJECTILE, 1D, 6D)))
            .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(Blocks.MAGMA_BLOCK, 200D)
                .put(MapField.ENTITY_NAME, "block")
                .put(MapField.BLOCK_FALL_SPEED, -0.03D)
                .put(MapField.FIND_NEAREST_SURFACE, false)
                .put(MapField.IS_BLOCK_FALLING, true)))
            .onTick("block", PartBuilder.particleOnTick(2D, ParticleTypes.LAVA, 2D, 0.5D))
            .onExpire("block", PartBuilder.damageInAoe(ValueCalculationData.base(10), Elements.Fire, 3D))
            .onExpire("block", PartBuilder.aoeParticles(ParticleTypes.LAVA, 150D, 3D))
            .onExpire("block", PartBuilder.aoeParticles(ParticleTypes.ASH, 25D, 3D))
            .onExpire("block", PartBuilder.aoeParticles(ParticleTypes.EXPLOSION, 1D, 1D))
            .onExpire("block", PartBuilder.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1D, 1D))
            .build();

        SpellBuilder.of("nature_balm", SpellConfiguration.Builder.instant(15, 60 * 20), "Nature's Balm",
            Arrays.asList(SkillGemTag.HEALING))
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.giveSelfExileEffect(BeneficialEffects.REGENERATE, 20 * 15D))
            .build();

        SpellBuilder.of("volcano", HIGH_AOE_LONG_CD(), "Volcano",
            Arrays.asList(SkillGemTag.AREA, SkillGemTag.DAMAGE))
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(PartBuilder.playSound(SOUNDS.FIREBALL, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(ENTITIES.SIMPLE_PROJECTILE, 100D, 4D)))
            .onTick(PartBuilder.tickGroundParticle(1D, ParticleTypes.SMOKE, 10D, 3.5D, 0.5D))
            .onTick(PartBuilder.tickGroundParticle(1D, ParticleTypes.LAVA, 10D, 3.5D, 0.5D))
            .onTick(PartBuilder.tickGroundParticle(1D, ParticleTypes.FALLING_LAVA, 10D, 3.5D, 0.5D))
            .onTick(PartBuilder.onTickDamageInAoe(20D, ValueCalculationData.base(3), Elements.Fire, 3.5D))
            .build();

        SpellBuilder.of("gorgons_gaze", SpellConfiguration.Builder.instant(15, 200 * 20), "Gorgon's Gaze",
            Arrays.asList(SkillGemTag.AREA, SkillGemTag.DAMAGE))
            .onCast(PartBuilder.playSound(SOUNDS.STONE_CRACK, 1D, 1D))
            .onCast(PartBuilder.addExileEffectToEnemiesInFront(NegativeEffects.PETRIFY, 15D, 3D, 20 * 5D))
            .build();

        SpellBuilder.of("fire_bombs", SpellConfiguration.Builder.multiCast(15, 20 * 30, 60, 3), "Fire Bombs",
            Arrays.asList(SkillGemTag.AREA, SkillGemTag.DAMAGE))
            .weaponReq(CastingWeapon.MAGE_WEAPON)

            .onCast(PartBuilder.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.COAL, 1D, 0.5D, ENTITIES.SIMPLE_PROJECTILE, 80D, true)))
            .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.SMOKE, 45D, 1D))
            .onHit(PartBuilder.damageInAoe(ValueCalculationData.base(9), Elements.Fire, 2D))
            .build();
    }
}