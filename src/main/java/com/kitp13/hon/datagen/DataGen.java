package com.kitp13.hon.datagen;

import com.kitp13.hon.HotOrNot;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGen {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        generator.addProvider(event.includeClient(), new LangEN_US(generator.getPackOutput(), HotOrNot.MODID, "en_us"));
        generator.addProvider(event.includeClient(), new ItemModel(generator.getPackOutput(), HotOrNot.MODID, event.getExistingFileHelper()));
        generator.addProvider(event.includeServer(), new Recipes(generator.getPackOutput()));
    }
}
