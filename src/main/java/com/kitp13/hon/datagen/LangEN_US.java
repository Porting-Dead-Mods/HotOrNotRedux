package com.kitp13.hon.datagen;

import com.kitp13.hon.HotOrNot;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class LangEN_US extends LanguageProvider {
    public LangEN_US(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @Override
    protected void addTranslations() {
        add(HotOrNot.WOODEN_TONGS.get(), "Wooden Tongs");
        add(HotOrNot.IRON_TONGS.get(), "Iron Tongs");
        add(HotOrNot.MITTS.get(), "Mitts");
        add("tooltip.hon.cold", "This Item feels Cold to the touch");
        add("tooltip.hon.hot", "This Item feels Hot to the touch");
        add("tooltip.hon.protectivegear", "This Item protects from negative effect from hot/cold items, put in offhand to use");
        add("item_group.hon.hon", "Hot Or Not Tab");
    }
}
