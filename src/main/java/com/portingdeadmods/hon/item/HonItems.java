package com.portingdeadmods.hon.item;

import com.portingdeadmods.hon.HotOrNot;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class HonItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, HotOrNot.MODID);

    public static RegistryObject<Item> MITTS = ITEMS.register("mittens", () -> new HonItem(1023));
    public static RegistryObject<Item> RIGHT_HAND_MITTS = ITEMS.register("right_hand_mittens", () -> new HonItem(1023));
    public static final RegistryObject<Item> WOODEN_TONGS = ITEMS.register("wooden_tongs", () -> new HonItem(127));
    public static final RegistryObject<Item> IRON_TONGS = ITEMS.register("iron_tongs", () -> new HonItem(511));
}
