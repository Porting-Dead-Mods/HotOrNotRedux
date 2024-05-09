package com.portingdeadmods.hon.utils;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

public class Item {
    public static boolean checkItem(@NotNull ItemStack itemstack, @NotNull RegistryObject<net.minecraft.world.item.Item> toCheck){
        return itemstack.getItem().equals(toCheck.get());
    }
    public static void damageStack(@NotNull ItemStack stack, Player player, EquipmentSlot slot){
        stack.hurtAndBreak(1,player, (e) -> e.broadcastBreakEvent(slot));
    }
}
