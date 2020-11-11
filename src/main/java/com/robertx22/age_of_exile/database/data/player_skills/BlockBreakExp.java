package com.robertx22.age_of_exile.database.data.player_skills;

import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockBreakExp {

    public float exp = 0;
    public String block_id = "";

    public BlockBreakExp(float exp, Block block) {
        this.exp = exp;
        this.block_id = Registry.BLOCK.getId(block)
            .toString();
    }

    public Block getBlock() {
        return Registry.BLOCK.get(new Identifier(block_id));
    }

    public BlockBreakExp() {
    }
}
