package com.robertx22.age_of_exile.database.data.stats.types.spell_calc;

import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellModEnum;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseSpellCalcEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpellStatsCalcEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class ProjectileSpeedStat extends Stat {

    private ProjectileSpeedStat() {
        this.max_val = 200;

        this.statEffect = new Effect();
    }

    public static ProjectileSpeedStat getInstance() {
        return ProjectileSpeedStat.SingletonHolder.INSTANCE;
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public Elements getElement() {
        return Elements.All;
    }

    @Override
    public String locDescForLangFile() {
        return "Makes spell projectiles faster";
    }

    @Override
    public String locNameForLangFile() {
        return "Faster Projectiles";
    }

    @Override
    public String GUID() {
        return "faster_projectiles";
    }

    static class Effect extends BaseSpellCalcEffect {

        @Override
        public SpellStatsCalcEffect activate(SpellStatsCalcEffect effect, StatData data, Stat stat) {
            effect.data.add(SpellModEnum.PROJECTILE_SPEED, data.getAverageValue());
            return effect;
        }

    }

    private static class SingletonHolder {
        private static final ProjectileSpeedStat INSTANCE = new ProjectileSpeedStat();
    }
}

