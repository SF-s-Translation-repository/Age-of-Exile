package com.robertx22.age_of_exile.mobs.mob_stats;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.generated.WeaponDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.ManaBurn;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.robertx22.age_of_exile.mmorpg.ModRegistry.ENTITIES;

public class SpecialMobStats {

    public List<StatModifier> stats;

    public SpecialMobStats(StatModifier... stats) {
        this.stats = Arrays.asList(stats);
    }

    public static SpecialMobStats INSTANCE;

    public static SpecialMobStats EMPTY = new SpecialMobStats();

    public static SpecialMobStats FIRE = new SpecialMobStats(new StatModifier(1, 1, new WeaponDamage(Elements.Fire), ModType.FLAT));
    public static SpecialMobStats WATER = new SpecialMobStats(new StatModifier(1, 1, new WeaponDamage(Elements.Water), ModType.FLAT));
    public static SpecialMobStats NATURE = new SpecialMobStats(new StatModifier(1, 1, new WeaponDamage(Elements.Nature), ModType.FLAT));
    public static SpecialMobStats THUNDER = new SpecialMobStats(new StatModifier(1, 1, new WeaponDamage(Elements.Thunder), ModType.FLAT));

    public static SpecialMobStats MANA_BURN = new SpecialMobStats(new StatModifier(1, 1, ManaBurn.getInstance(), ModType.FLAT));

    private HashMap<EntityType, SpecialMobStats> MAP = new HashMap<>();

    public static void init() {
        INSTANCE = new SpecialMobStats();
        INSTANCE.MAP.clear();

        INSTANCE.register(ENTITIES.FIRE_SLIME, FIRE);
        INSTANCE.register(ENTITIES.WATER_SLIME, WATER);
        INSTANCE.register(ENTITIES.NATURE_SLIME, NATURE);
        INSTANCE.register(ENTITIES.THUNDER_SLIME, THUNDER);
        INSTANCE.register(ENTITIES.ARCANE_SLIME, MANA_BURN);

        INSTANCE.register(ENTITIES.FIRE_SPIDER, FIRE);
        INSTANCE.register(ENTITIES.WATER_SPIDER, WATER);
        INSTANCE.register(ENTITIES.NATURE_SPIDER, NATURE);
        INSTANCE.register(ENTITIES.THUNDER_SPIDER, THUNDER);
        INSTANCE.register(ENTITIES.ARCANE_SPIDER, MANA_BURN);

    }

    public void register(EntityType type, SpecialMobStats stats) {
        MAP.put(type, stats);
    }

    public static SpecialMobStats getStatsFor(Entity entity) {

        EntityType<?> type = entity.getType();

        if (INSTANCE.MAP.containsKey(type)) {
            return INSTANCE.MAP.get(type);
        }
        return INSTANCE.EMPTY;
    }
}
