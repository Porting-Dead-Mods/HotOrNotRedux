package com.kitp13.hon.tab;

import com.kitp13.hon.HotOrNot;
import com.kitp13.hon.item.HonItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class HonTab {
    public static final DeferredRegister<CreativeModeTab> HONTABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, HotOrNot.MODID);

    public static final RegistryObject<CreativeModeTab> HOT_OR_NOT_TAB = HONTABS.register("hon", () ->
            CreativeModeTab.builder()
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .title(Component.translatable("item_group."+ HotOrNot.MODID+".hon"))
                    .icon(()->new ItemStack(Items.LAVA_BUCKET))
                    .displayItems((p,out) -> {
                        out.accept(Items.LAVA_BUCKET);
                        out.accept(Items.WATER_BUCKET);
                        out.accept(HonItems.RIGHT_HAND_MITTS.get());
                        out.accept(HonItems.MITTS.get());
                        out.accept(HonItems.WOODEN_TONGS.get());
                        out.accept(HonItems.IRON_TONGS.get());
                    })
                    .build());
}
