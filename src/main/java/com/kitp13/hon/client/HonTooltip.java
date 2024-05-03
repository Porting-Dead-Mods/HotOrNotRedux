package com.kitp13.hon.client;

import com.kitp13.hon.HotOrNot;
import com.kitp13.hon.config.HonConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = HotOrNot.MODID,bus= Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class HonTooltip {
    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (HonConfig.tooltips && !stack.isEmpty() && !HonConfig.blacklistColdItems.contains(stack.getItem()) && !HonConfig.blacklistHotItems.contains(stack.getItem())){
            LazyOptional<IFluidHandlerItem> fluidHandler1 = stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM);
            if (HonConfig.blacklistHotItems.contains(stack.getItem()) || HonConfig.blacklistColdItems.contains(stack.getItem())){
            } else if (stack.getItem().equals(HotOrNot.MITTS.get())||stack.getItem().equals(HotOrNot.IRON_TONGS.get())||stack.getItem().equals(HotOrNot.WOODEN_TONGS.get())){
                ProtectiveItem(event,stack);
            } else if (fluidHandler1.isPresent()) {

                IFluidHandlerItem fluidHandlerItem = fluidHandler1.resolve().get();

                FluidStack fluidStack = fluidHandlerItem.drain(1000, IFluidHandler.FluidAction.SIMULATE);
                if (!fluidStack.isEmpty()) {
                    if (fluidStack.getFluid().getFluidType().getTemperature() > HonConfig.hotThreshold || fluidStack.getFluid().getFluidType().getTemperature() < HonConfig.coldThreshold){
                        if (fluidStack.getFluid().getFluidType().getTemperature() > HonConfig.hotThreshold) {
                            HotItem(event,stack);
                        } else if (fluidStack.getFluid().getFluidType().getTemperature() < HonConfig.coldThreshold) {
                            ColdItem(event, stack);
                        }
                    }
                }
            } else if (HonConfig.customHotItems.contains(stack.getItem())){
                HotItem(event,stack);
            } else if (HonConfig.customColdItems.contains(stack.getItem())) {
                ColdItem(event,stack);
            }
        }
    }
    private static void HotItem(ItemTooltipEvent event, ItemStack stack){
        event.getToolTip().add(Component.translatable(Tooltip.HOT.tooltip).withStyle(Style.EMPTY.withColor(Tooltip.HOT.color)));
    }
    private static void ColdItem(ItemTooltipEvent event, ItemStack stack){
        event.getToolTip().add(Component.translatable(Tooltip.COLD.tooltip).withStyle(Style.EMPTY.withColor(Tooltip.COLD.color)));
    }
    private static void ProtectiveItem(ItemTooltipEvent event, ItemStack stack) {
        event.getToolTip().add(Component.translatable(Tooltip.PROTECTIONITEM.tooltip).withStyle(Style.EMPTY.withColor(Tooltip.PROTECTIONITEM.color)));
    }

    private enum Tooltip {

        COLD(TextColor.parseColor("#4085f5"), "tooltip.hon.cold"),
        HOT(TextColor.parseColor("#ff2121"), "tooltip.hon.hot"),
        PROTECTIONITEM(TextColor.parseColor("#d4d404"), "tooltip.hon.protectivegear");
        public final TextColor color;
        public final String tooltip;
        Tooltip(TextColor color, String tooltip) {
            this.color = color;
            this.tooltip = tooltip;
        }
    }
}
