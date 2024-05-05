package com.kitp13.hon;

import com.kitp13.hon.config.HonConfig;
import com.kitp13.hon.enchant.HonEnchants;
import com.kitp13.hon.item.HonItems;
import com.kitp13.hon.tab.HonTab;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod(HotOrNot.MODID)
public class HotOrNot {
    public static final String MODID = "hon";
    public static final String MODNAME = "Hot Or Not Redux";
    public static final String VERSION = "1.0.1";
    public static Logger LOGGER = LogManager.getLogger(MODID);

    public HotOrNot() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        HonEnchants.ENCHANTMENTS.register(modEventBus);
        HonItems.ITEMS.register(modEventBus);
        HonTab.HONTABS.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, HonConfig.SPEC);
    }
}