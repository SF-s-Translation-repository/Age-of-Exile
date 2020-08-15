package com.robertx22.age_of_exile.uncommon.utilityclasses;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.interfaces.IWeighted;
import net.minecraft.item.Item;

import java.util.Arrays;
import java.util.List;

public class ItemUtils {
    public static Item.Settings getDefaultGearProperties() {

        Item.Settings prop = new Item.Settings().group(CreativeTabs.MyModTab);

        return prop;
    }

    public static Item randomMagicEssence() {
        List<IWeighted> list = Arrays.asList((IWeighted) ModRegistry.MISC_ITEMS.MAGIC_ESSENCE, (IWeighted) ModRegistry.MISC_ITEMS.RARE_MAGIC_ESSENCE);
        return (Item) RandomUtils.weightedRandom(list);

    }
}