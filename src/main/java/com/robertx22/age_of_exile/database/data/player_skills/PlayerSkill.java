package com.robertx22.age_of_exile.database.data.player_skills;

import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.capability.player.PlayerSkills;
import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.player_skills.enchants.BonusSkillLootEnchant;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.ITooltipList;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PlayerSkill implements ISerializedRegistryEntry<PlayerSkill>, IAutoGson<PlayerSkill>, ITooltipList {
    public static PlayerSkill SERIALIZER = new PlayerSkill();

    public PlayerSkillEnum type_enum = PlayerSkillEnum.MINING;
    public String id;
    public int exp_per_action = 0;
    public int order = 0;

    public float loot_chance_per_action_exp = 0.1F;

    public List<SkillDropReward> drop_rewards = new ArrayList<>();
    public List<SkillStatReward> stat_rewards = new ArrayList<>();

    public List<BlockBreakExp> block_break_exp = new ArrayList<>();

    public List<ItemCraftExp> item_craft_exp = new ArrayList<>();

    public Identifier getIcon() {
        return Ref.id("textures/gui/skills/icons/" + id + ".png");
    }

    public List<SkillStatReward> getClaimedStats(int lvl) {
        return stat_rewards.stream()
            .filter(r -> lvl >= r.lvl_req)
            .collect(Collectors.toList());
    }

    public int getExpForBlockBroken(Block block) {

        Optional<BlockBreakExp> opt = block_break_exp.stream()
            .filter(x -> x.getBlock() == block)
            .findFirst();

        if (opt.isPresent()) {
            return (int) opt.get().exp;
        } else {
            return 0;
        }

    }

    public List<ItemStack> getExtraDropsFor(PlayerSkills skills, int expForAction) {

        List<ItemStack> list = new ArrayList<>();

        float chance = loot_chance_per_action_exp * expForAction;

        float chanceMulti = BonusSkillLootEnchant.getBonusLootChanceMulti(skills.player, this.type_enum);

        chance *= chanceMulti;

        if (RandomUtils.roll(chance)) {
            List<SkillDropReward> possible = drop_rewards
                .stream()
                .filter(x -> skills.getLevel(type_enum) >= x.lvl_req)
                .collect(Collectors.toList());

            if (!possible.isEmpty()) {
                list.add(RandomUtils.weightedRandom(possible)
                    .getRewardStack());
            }
        }

        return list;

    }

    @Override
    public SlashRegistryType getSlashRegistryType() {
        return SlashRegistryType.PLAYER_SKILLS;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public Class<PlayerSkill> getClassForSerialization() {
        return PlayerSkill.class;
    }

    @Override
    public List<Text> GetTooltipString(TooltipInfo info) {

        List<Text> list = new ArrayList<>();

        int lvl = Load.playerSkills(info.player)
            .getLevel(type_enum);

        list.add(this.type_enum.word.locName()
            .formatted(type_enum.format));

        list.add(new LiteralText(""));

        List<OptScaleExactStat> stats = new ArrayList<>();

        for (SkillStatReward x : getClaimedStats(lvl)) {
            stats.addAll(x.stats);
        }
        OptScaleExactStat.combine(stats);

        if (!stats.isEmpty()) {
            list.add(Words.Stats.locName()
                .append(": "));
            stats.forEach(x -> list.addAll(x.GetTooltipString(info)));
        }

        List<OptScaleExactStat> nextstats = new ArrayList<>();

        Optional<SkillStatReward> opt = stat_rewards.stream()
            .filter(x -> x.lvl_req > lvl)
            .sorted(Comparator.comparingInt(x -> x.lvl_req))
            .findFirst();

        if (opt.isPresent()) {
            list.add(new LiteralText(""));
            nextstats.addAll(opt.get().stats);
            OptScaleExactStat.combine(stats);
            list.add(new LiteralText("Level " + opt.get().lvl_req + " unlocks:"));
            nextstats.forEach(x -> list.addAll(x.GetTooltipString(info)));
        }

        if (lvl >= Load.Unit(info.player)
            .getLevel()) {
            if (lvl < ModConfig.get().Server.MAX_LEVEL) {
                list.add(new LiteralText(""));
                list.add(new LiteralText("Skill level Capped to Combat level.").formatted(Formatting.RED));
                list.add(new LiteralText("Level your Combat level to progress further.").formatted(Formatting.RED));
            }
        }

        return list;
    }
}