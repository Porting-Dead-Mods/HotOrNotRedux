package com.kitp13.hon.utils;

import com.kitp13.hon.config.HonConfig;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import org.jetbrains.annotations.NotNull;

public class Temperature {
    public static int getFluidTemperature(@NotNull FluidStack fluidStack){
        return fluidStack.getFluid().getFluidType().getTemperature();
    }
    public static Temp getTemp(@NotNull LazyOptional<IFluidHandlerItem> handler) {
        if (!handler.isPresent()) return Temp.NONE;
        FluidStack fluidStack = handler.resolve().get().drain(1000, IFluidHandler.FluidAction.SIMULATE);
        if (Temperature.getFluidTemperature(fluidStack) > HonConfig.hotThreshold) return Temp.HOT;
        else if (Temperature.getFluidTemperature(fluidStack) < HonConfig.coldThreshold) return Temp.COLD;
        return Temp.NONE;
    }
    public static Temp getTemp(ItemStack stack){
        if (!stack.isEmpty()) {
            LazyOptional<IFluidHandlerItem> fluidHandler1 = stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM);
            if (HonConfig.blacklistHotItems.contains(stack.getItem()) || HonConfig.blacklistColdItems.contains(stack.getItem())){
                return Temp.NONE;
            } else if (fluidHandler1.isPresent()) {
                return Temperature.getTemp(fluidHandler1);
            } else if (HonConfig.customHotItems.contains(stack.getItem())){
                return Temp.HOT;
            } else if (HonConfig.customColdItems.contains(stack.getItem())) {
                return Temp.COLD;
            }
        }
        return Temp.NONE;
    }

    public enum Temp{
        COLD,
        HOT,
        NONE
    }
}
