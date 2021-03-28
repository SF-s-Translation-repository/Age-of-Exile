package com.robertx22.age_of_exile.player_skills.recipe_types.recipe_types;

import com.robertx22.age_of_exile.player_skills.recipe_types.base.StationShapeless;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;

public class TabletSerializer extends StationShapeless.Serializer<TabletShapeless> {

    @Override
    public TabletShapeless createNew(Identifier id, String group, ItemStack output, DefaultedList input) {
        return new TabletShapeless(id, group, output, input);
    }

}
