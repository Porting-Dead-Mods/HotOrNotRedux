package com.portingdeadmods.hon.enchant;

import com.portingdeadmods.hon.HotOrNot;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class HonEnchants {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, HotOrNot.MODID);
    public static final RegistryObject<Enchantment> TERMAL_PROTECTION = ENCHANTMENTS.register(
            "thermal_protection",
            () -> new ThermalProtection(Enchantment.Rarity.RARE, EnchantmentCategory.ARMOR,
                    new EquipmentSlot[]{
                            EquipmentSlot.FEET,
                            EquipmentSlot.LEGS,
                            EquipmentSlot.CHEST,
                            EquipmentSlot.HEAD
                    }));
}
