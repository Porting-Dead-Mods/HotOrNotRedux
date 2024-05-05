package com.kitp13.hon.client;

import com.kitp13.hon.HotOrNot;
import com.kitp13.hon.config.HonConfig;
import com.kitp13.hon.enchant.HonEnchants;
import com.kitp13.hon.item.HonItems;
import com.kitp13.hon.utils.Item;
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
            } else if (Item.checkItem(stack, HonItems.MITTS) || Item.checkItem(stack, HonItems.IRON_TONGS) || Item.checkItem(stack, HonItems.WOODEN_TONGS)){
                ProtectiveItem(event);
            } else if (Item.checkItem(stack, HonItems.RIGHT_HAND_MITTS) ) {
                if (HonConfig.bothHandsMitts) {
                    RightHandMittenActive(event);
                }
                RightHandMittenInactive(event);
            } else if (stack.getEnchantmentLevel(HonEnchants.TERMAL_PROTECTION.get()) >= 1) {
                ProtectiveEnchantment(event);
            }
            switch (Temperature.getTemp(stack)) {
                case COLD -> ColdItem(event);
                case HOT -> HotItem(event);
            }
        }
    }
    private static void HotItem(ItemTooltipEvent event){
        addTooltip(event, Tooltip.HOT);
    }
    private static void ColdItem(ItemTooltipEvent event){
        addTooltip(event, Tooltip.COLD);
    }
    private static void RightHandMittenActive(ItemTooltipEvent event) {
        addTooltip(event, Tooltip.RIGHT_HAND_MITTEN_ACTIVE);
    }
    private static void RightHandMittenInactive(ItemTooltipEvent event) {
        addTooltip(event, Tooltip.RIGHT_HAND_MITTEN_INACTIVE);
    }
    private static void ProtectiveItem(ItemTooltipEvent event) {
        addTooltip(event, Tooltip.PROTECTIONITEM);
    }
    private static void ProtectiveEnchantment(ItemTooltipEvent event){
        addTooltip(event,Tooltip.PROTECTIVEENCHANT);
    }

    private static void addTooltip(ItemTooltipEvent event, Tooltip tooltip){
        event.getToolTip().add(Component.translatable(tooltip.tooltip).withStyle(Style.EMPTY.withColor(tooltip.color)));
    }

    private enum Tooltip {

        COLD(TextColor.parseColor("#4085f5"), "tooltip.hon.cold"),
        HOT(TextColor.parseColor("#ff2121"), "tooltip.hon.hot"),
        PROTECTIONITEM(TextColor.parseColor("#d4d404"), "tooltip.hon.protectivegear"),
        PROTECTIVEENCHANT(TextColor.parseColor("#d4d404"), "tooltip.hon.protectiveenchant"),
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
