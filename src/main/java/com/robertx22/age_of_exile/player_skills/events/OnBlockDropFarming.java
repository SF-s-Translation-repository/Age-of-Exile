package com.robertx22.age_of_exile.player_skills.events;

import com.robertx22.age_of_exile.capability.player.PlayerSkills;
import com.robertx22.age_of_exile.database.data.player_skills.PlayerSkill;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.GourdBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

public class OnBlockDropFarming {

    public static void run(LootContext ctx, CallbackInfoReturnable<List<ItemStack>> ci) {

        try {
            if (!ctx.hasParameter(LootContextParameters.BLOCK_STATE)) {
                return;
            }
            if (!ctx.hasParameter(LootContextParameters.ORIGIN)) {
                return;
            }
            if (!ctx.hasParameter(LootContextParameters.THIS_ENTITY)) {
                return;
            }

            BlockState state = ctx.get(LootContextParameters.BLOCK_STATE);

            Block block = state
                .getBlock();

            if (block instanceof CropBlock) {
                CropBlock crop = (CropBlock) block;
                if (!crop.isMature(state)) {
                    return;
                }
            } else if (block instanceof GourdBlock) {

            } else {
                return;
            }

            Entity en = ctx.get(LootContextParameters.THIS_ENTITY);

            PlayerEntity player = null;
            if (en instanceof PlayerEntity) {
                player = (PlayerEntity) en;
            } else {
                return;
            }
            if (player.world.isClient) {
                return;
            }

            PlayerSkill skill = SlashRegistry.PlayerSkills()
                .get(PlayerSkillEnum.FARMING.id);

            PlayerSkills skills = Load.playerSkills(player);

            int exp = skill.getExpForBlockBroken(block);
            skills.addExp(skill.type_enum, exp);
            List<ItemStack> list = skill.getExtraDropsFor(skills, exp);

            ci.getReturnValue()
                .addAll(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

