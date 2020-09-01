package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.vanilla_mc.items.GemItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;

public class GemItemRegister extends BaseItemRegistrator {

    public HashMap<GemItem.GemType, HashMap<GemItem.GemRank, GemItem>> MAP = new HashMap<>();

    public GemItemRegister() {

        for (GemItem.GemType type : GemItem.GemType.values()) {
            for (GemItem.GemRank rank : GemItem.GemRank.values()) {

                GemItem item = new GemItem(type, rank);

                Registry.register(Registry.ITEM, new Identifier(Ref.MODID, item.GUID()), item);

                if (!MAP.containsKey(type)) {
                    MAP.put(type, new HashMap<>());
                }

                MAP.get(type)
                    .put(rank, item);

            }
        }

    }

}
