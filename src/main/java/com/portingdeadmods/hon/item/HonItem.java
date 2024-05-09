package com.portingdeadmods.hon.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;

public class HonItem extends Item {
    public HonItem(int durability) {
        super(new Properties().defaultDurability(durability));
    }
}
