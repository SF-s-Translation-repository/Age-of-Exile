package com.robertx22.age_of_exile.aoe_data.database.affixes.adders;

import com.robertx22.age_of_exile.aoe_data.database.affixes.AffixBuilder;
import com.robertx22.age_of_exile.aoe_data.database.affixes.ElementalAffixBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotTag;
import com.robertx22.age_of_exile.database.data.stats.types.generated.AttackDamage;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ChanceToApplyEffect;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.CriticalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.CriticalHit;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;

public class WeaponPrefixes implements ISlashRegistryInit {
    @Override
    public void registerAll() {

        ElementalAffixBuilder.start()
            .guid(x -> x.guidName + "_wep_dmg")
            .add(Elements.Fire, "Scorched")
            .add(Elements.Water, "Chilled")
            .add(Elements.Nature, "Poisoned")
            .add(Elements.Physical, "Tyrannical")
            .add(Elements.Light, "Holy")
            .add(Elements.Dark, "Cursed")
            .tier(1, x -> Arrays.asList(new StatModifier(1.5F, 2, 2.5F, 2, new AttackDamage(x), ModType.FLAT)))
            .tier(2, x -> Arrays.asList(new StatModifier(1, 1.25F, 1.25F, 1.5F, new AttackDamage(x), ModType.FLAT)))
            .tier(3, x -> Arrays.asList(new StatModifier(0.25F, 0.5F, 0.5F, 1F, new AttackDamage(x), ModType.FLAT)))
            .includesTags(SlotTag.weapon_family)
            .Prefix()
            .Build();

        AffixBuilder.Normal("cruel")
            .Named("Cruel")
            .tier(1, new StatModifier(16, 20, new AttackDamage(Elements.Physical), ModType.LOCAL_INCREASE))
            .tier(2, new StatModifier(14, 16, new AttackDamage(Elements.Physical), ModType.LOCAL_INCREASE))
            .tier(3, new StatModifier(10, 14, new AttackDamage(Elements.Physical), ModType.LOCAL_INCREASE))
            .tier(4, new StatModifier(7, 10, new AttackDamage(Elements.Physical), ModType.LOCAL_INCREASE))
            .tier(5, new StatModifier(5, 7, new AttackDamage(Elements.Physical), ModType.LOCAL_INCREASE))
            .includesTags(SlotTag.weapon_family)
            .Prefix()
            .Build();

        ///// chance of effect
        AffixBuilder.Normal("snakes")
            .Named("Snake's")
            .tier(1, new StatModifier(10, 15, ChanceToApplyEffect.POISON, ModType.FLAT))
            .tier(2, new StatModifier(8, 10, ChanceToApplyEffect.POISON, ModType.FLAT))
            .tier(3, new StatModifier(5, 8, ChanceToApplyEffect.POISON, ModType.FLAT))
            .includesTags(SlotTag.weapon_family)
            .Weight(50)
            .Prefix()
            .Build();

        AffixBuilder.Normal("yetis")
            .Named("Yeti's")
            .tier(1, new StatModifier(10, 15, ChanceToApplyEffect.FROSTBURN, ModType.FLAT))
            .tier(2, new StatModifier(8, 10, ChanceToApplyEffect.FROSTBURN, ModType.FLAT))
            .tier(3, new StatModifier(5, 8, ChanceToApplyEffect.FROSTBURN, ModType.FLAT))
            .includesTags(SlotTag.weapon_family)
            .Weight(50)
            .Prefix()
            .Build();

        AffixBuilder.Normal("drakes")
            .Named("Drakes's")
            .tier(1, new StatModifier(10, 15, ChanceToApplyEffect.BURN, ModType.FLAT))
            .tier(2, new StatModifier(8, 10, ChanceToApplyEffect.BURN, ModType.FLAT))
            .tier(3, new StatModifier(5, 8, ChanceToApplyEffect.BURN, ModType.FLAT))
            .includesTags(SlotTag.weapon_family)
            .Weight(50)
            .Prefix()
            .Build();

        ///// chance of effect

        AffixBuilder.Normal("true_hit")
            .Named("True Hit")
            .tier(1, new StatModifier(5, 10, CriticalHit.getInstance(), ModType.FLAT))
            .tier(2, new StatModifier(3, 5, CriticalHit.getInstance(), ModType.FLAT))
            .tier(3, new StatModifier(2, 3, CriticalHit.getInstance(), ModType.FLAT))
            .includesTags(SlotTag.weapon_family)
            .Prefix()
            .Build();

        AffixBuilder.Normal("crit_prefix")
            .Named("Critical")
            .tier(1, new StatModifier(9, 12, CriticalDamage.getInstance(), ModType.FLAT))
            .tier(2, new StatModifier(6, 9, CriticalDamage.getInstance(), ModType.FLAT))
            .tier(3, new StatModifier(4, 6, CriticalDamage.getInstance(), ModType.FLAT))
            .includesTags(SlotTag.weapon_family)
            .Prefix()
            .Build();

    }

}
