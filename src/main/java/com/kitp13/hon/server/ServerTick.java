package com.kitp13.hon.server;

import com.kitp13.hon.HotOrNot;
import com.kitp13.hon.config.HonConfig;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.IItemHandler;

@Mod.EventBusSubscriber(modid = HotOrNot.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerTick {
    @SubscribeEvent
    public static void onTick(TickEvent.PlayerTickEvent event) {
        Level level = event.player.level();
        Player player = event.player;
        if (event.phase == TickEvent.Phase.START && !level.isClientSide && (level.getGameTime()%HonConfig.updateSpeed == 0)) {
            //HotOrNot.LOGGER.info("go!");
            if (!player.isOnFire() && !player.isCreative()) {
                LazyOptional<IItemHandler> handler = player.getCapability(ForgeCapabilities.ITEM_HANDLER,null);
                if (handler.isPresent()) {


                    IItemHandler handler1 = handler.resolve().get();
                    for (int i = 0; i < handler1.getSlots(); i++) {

                        ItemStack stack = handler1.extractItem(i,1,true);
                        if (!stack.isEmpty()) {
                            LazyOptional<IFluidHandlerItem> fluidHandler1 = stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM);
                            if (HonConfig.blacklistHotItems.contains(stack.getItem()) || HonConfig.blacklistColdItems.contains(stack.getItem())){
                                continue;
                            }
                            else if (fluidHandler1.isPresent()) {

                                IFluidHandlerItem fluidHandlerItem = fluidHandler1.resolve().get();

                                FluidStack fluidStack = fluidHandlerItem.drain(1000, IFluidHandler.FluidAction.SIMULATE);
                                if (!fluidStack.isEmpty()) {
                                    if (fluidStack.getFluid().getFluidType().getTemperature() > HonConfig.hotThreshold || fluidStack.getFluid().getFluidType().getTemperature() < HonConfig.coldThreshold){
                                        if (!playerUseProtectiveGear(player)) {
                                            // HotOrNot.LOGGER.info(fluidStack.getFluid().getFluidType().getTemperature());
                                            if (fluidStack.getFluid().getFluidType().getTemperature() > HonConfig.hotThreshold) {
                                                applyHotEffects(player, i);
                                            } else if (fluidStack.getFluid().getFluidType().getTemperature() < HonConfig.coldThreshold) {
                                                applyColdEffects(player, i);
                                            }
                                        }
                                    }
                                }
                            } else if (HonConfig.customHotItems.contains(stack.getItem())){
                                if (!playerUseProtectiveGear(player)){
                                    applyHotEffects(player,i);
                                }
                            } else if (HonConfig.customColdItems.contains(stack.getItem())) {
                                if (!playerUseProtectiveGear(player)){
                                    applyColdEffects(player,i);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    static void applyHotEffects(Player player, int i) {
        //PacketHandler.INSTANCE.sendToServer(new HotPacket(true,event.player.getUUID()));
        player.setSecondsOnFire(5);
    }
    static void applyColdEffects(Player player, int i) {
        //PacketHandler.INSTANCE.sendToServer(new ColdPacket(true, event.player.getUUID()));
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,25,1));
    }
    static boolean playerUseProtectiveGear(Player player){
        ItemStack offhand = player.getOffhandItem();
        ItemStack mainhand = player.getMainHandItem();

        if (HonConfig.bothHandsMitts) {
            if (offhand.getItem().equals(HotOrNot.MITTS.get()) && mainhand.getItem().equals(HotOrNot.RIGHT_HAND_MITTS.get())) {
                if(HonConfig.mittDamageable) {
                    mainhand.hurtAndBreak(1, player, (e) -> e.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                    offhand.hurtAndBreak(1, player, (e) -> e.broadcastBreakEvent(EquipmentSlot.OFFHAND));
                }
                return true;
            }
        } else {
            if (offhand.getItem().equals(HotOrNot.MITTS.get())){
                if(HonConfig.mittDamageable) offhand.hurtAndBreak(1,player, (e) -> e.broadcastBreakEvent(EquipmentSlot.OFFHAND));
                return true;
            }
        }
        if (offhand.getItem().equals(HotOrNot.WOODEN_TONGS.get())){
            if(HonConfig.woodenTongsDamageable) offhand.hurtAndBreak(1,player, (e) -> e.broadcastBreakEvent(EquipmentSlot.OFFHAND));
            return true;
        } else if (offhand.getItem().equals(HotOrNot.IRON_TONGS.get())){
            if(HonConfig.ironTongsDamageable) offhand.hurtAndBreak(1,player, (e) -> e.broadcastBreakEvent(EquipmentSlot.OFFHAND));
            return true;
        }
        return false;
    }
}
