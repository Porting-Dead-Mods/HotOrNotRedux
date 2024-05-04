package com.kitp13.hon.client;

import com.kitp13.hon.HotOrNot;
import com.kitp13.hon.config.HonConfig;
import com.kitp13.hon.utils.Temperature;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = HotOrNot.MODID,bus= Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class HonTooltip {
    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (HonConfig.tooltips && !stack.isEmpty() && !HonConfig.blacklistColdItems.contains(stack.getItem()) && !HonConfig.blacklistHotItems.contains(stack.getItem())){
            if (HonConfig.blacklistHotItems.contains(stack.getItem()) || HonConfig.blacklistColdItems.contains(stack.getItem())){
            } else if (stack.getItem().equals(HotOrNot.MITTS.get()) || stack.getItem().equals(HotOrNot.IRON_TONGS.get()) || stack.getItem().equals(HotOrNot.WOODEN_TONGS.get())){
                ProtectiveItem(event,stack);
            } else if (stack.getItem().equals(HotOrNot.RIGHT_HAND_MITTS.get()) && HonConfig.bothHandsMitts) {
                RightHandMittenActive(event, stack);
            } else if (stack.getItem().equals(HotOrNot.RIGHT_HAND_MITTS.get()) && !HonConfig.bothHandsMitts) {
                RightHandMittenInactive(event, stack);
            }
            switch (Temperature.getTemp(stack)) {
                case COLD -> ColdItem(event,stack);
                case HOT -> HotItem(event,stack);
            }
        }
    }
    private static void HotItem(ItemTooltipEvent event, ItemStack stack){
        addTooltip(event, Tooltip.HOT);
    }
    private static void ColdItem(ItemTooltipEvent event, ItemStack stack){
        addTooltip(event, Tooltip.COLD);
    }
    private static void RightHandMittenActive(ItemTooltipEvent event, ItemStack stack) {
        addTooltip(event, Tooltip.RIGHT_HAND_MITTEN_ACTIVE);
    }
    private static void RightHandMittenInactive(ItemTooltipEvent event, ItemStack stack) {
        addTooltip(event, Tooltip.RIGHT_HAND_MITTEN_INACTIVE);
    }
    private static void ProtectiveItem(ItemTooltipEvent event, ItemStack stack) {
        addTooltip(event, Tooltip.PROTECTIONITEM);
    }

    private static void addTooltip(ItemTooltipEvent event, Tooltip tooltip){
        event.getToolTip().add(Component.translatable(tooltip.tooltip).withStyle(Style.EMPTY.withColor(tooltip.color)));
    }

    private enum Tooltip {

        COLD(TextColor.parseColor("#4085f5"), "tooltip.hon.cold"),
        HOT(TextColor.parseColor("#ff2121"), "tooltip.hon.hot"),
        PROTECTIONITEM(TextColor.parseColor("#d4d404"), "tooltip.hon.protectivegear"),
        RIGHT_HAND_MITTEN_INACTIVE(TextColor.parseColor("#c2b38a"), "tooltip.hon.right_hand_mitten_inactive"),
        RIGHT_HAND_MITTEN_ACTIVE(TextColor.parseColor("#d4d404"), "tooltip.hon.right_hand_mitten_active");

        public final TextColor color;
        public final String tooltip;
        Tooltip(TextColor color, String tooltip) {
            this.color = color;
            this.tooltip = tooltip;
        }
    }
}
