package com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases;

import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;

public interface ICreateSpecific<T> {

    public void create(GearItemData gear, T t);

}
