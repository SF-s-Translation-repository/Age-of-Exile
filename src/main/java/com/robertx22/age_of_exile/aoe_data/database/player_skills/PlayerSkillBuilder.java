package com.robertx22.age_of_exile.aoe_data.database.player_skills;

import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.player_skills.*;
import com.robertx22.age_of_exile.database.data.stats.types.misc.BonusExp;
import com.robertx22.age_of_exile.database.data.stats.types.offense.TotalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.professions.all.BonusSkillYield;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.Arrays;

public class PlayerSkillBuilder {

    public PlayerSkill skill = new PlayerSkill();

    public static PlayerSkillBuilder of(int order, PlayerSkillEnum se) {
        PlayerSkillBuilder b = new PlayerSkillBuilder();
        b.skill.id = se.id;
        b.skill.type_enum = se;
        b.skill.order = order;
        return b;
    }

    public PlayerSkillBuilder stat(SkillStatReward stat) {
        skill.stat_rewards.add(stat);
        return this;
    }

    public PlayerSkillBuilder masteryStat(MasteryStatReward stat) {
        skill.mastery_stat_reward.add(stat);
        return this;
    }

    public PlayerSkillBuilder totalDamage(int lvl, float num) {
        stat(new SkillStatReward(lvl,
            new OptScaleExactStat(num, TotalDamage.getInstance(), ModType.FLAT)
        ));
        return this;
    }

    public PlayerSkillBuilder bonusSalvagingYield(int lvl, float num) {
        stat(new SkillStatReward(lvl,
            new OptScaleExactStat(num, new BonusSkillYield(PlayerSkillEnum.SALVAGING), ModType.FLAT)

        ));
        return this;
    }

    public PlayerSkillBuilder hpMsMana(int lvl, float num) {
        stat(new SkillStatReward(lvl,
            new OptScaleExactStat(num, Health.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(num, Mana.getInstance(), ModType.LOCAL_INCREASE)

        ));
        return this;
    }

    public PlayerSkillBuilder regens(int lvl, float num) {
        stat(new SkillStatReward(lvl,
            new OptScaleExactStat(num, HealthRegen.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(num, ManaRegen.getInstance(), ModType.LOCAL_INCREASE)
        ));
        return this;
    }

    public PlayerSkillBuilder blockExp(Block block, int exp) {
        skill.block_break_exp.add(new BlockBreakExp(exp, block));
        return this;
    }

    public PlayerSkillBuilder smeltExp(Item item, int exp) {
        skill.item_smelt_exp.add(new ItemCraftExp(exp, item));
        return this;
    }

    public PlayerSkillBuilder itemCraftExp(Item block, int exp) {
        skill.item_craft_exp.add(new ItemCraftExp(exp, block));
        return this;
    }

    public PlayerSkillBuilder addSalvagingBonusYield() {
        bonusSalvagingYield(10, 5);
        bonusSalvagingYield(20, 10);
        bonusSalvagingYield(30, 10);
        bonusSalvagingYield(40, 15);
        bonusSalvagingYield(50, 20);
        return this;
    }

    public PlayerSkillBuilder addDefaultHpMsMana() {
        hpMsMana(10, 2);
        hpMsMana(20, 3);
        hpMsMana(30, 4);
        hpMsMana(40, 5);
        hpMsMana(50, 5);
        return this;
    }

    public PlayerSkillBuilder addBonusYieldMasteryLevelStats(PlayerSkillEnum skill) {
        this.masteryStat(
            new MasteryStatReward(Arrays.asList(new OptScaleExactStat(1, new BonusSkillYield(skill))), 10)
        );
        return this;
    }

    public PlayerSkillBuilder addDefaultBonusExpRewards() {
        stat(new SkillStatReward(5, new OptScaleExactStat(10, BonusExp.getInstance())));
        stat(new SkillStatReward(15, new OptScaleExactStat(15, BonusExp.getInstance())));
        stat(new SkillStatReward(25, new OptScaleExactStat(15, BonusExp.getInstance())));
        stat(new SkillStatReward(35, new OptScaleExactStat(20, BonusExp.getInstance())));
        stat(new SkillStatReward(45, new OptScaleExactStat(25, BonusExp.getInstance())));
        return this;
    }

    public PlayerSkill build() {
        skill.addToSerializables();
        return skill;
    }

}
