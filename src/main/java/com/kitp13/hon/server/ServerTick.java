package com.kitp13.hon.server;

import com.kitp13.hon.HotOrNot;
import com.kitp13.hon.config.HonConfig;
import com.kitp13.hon.utils.Temperature;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(modid = HotOrNot.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerTick {
    @SubscribeEvent
    public static void onTick(TickEvent.@NotNull PlayerTickEvent event) {
        Player player = event.player;
        Level level = player.level();
        if (event.phase == TickEvent.Phase.START && !level.isClientSide && (level.getGameTime()%HonConfig.updateSpeed == 0)) {
            if (!player.isOnFire() && !player.isCreative()) {
                LazyOptional<IItemHandler> handler = player.getCapability(ForgeCapabilities.ITEM_HANDLER,null);
                if (handler.isPresent()) {
                    IItemHandler handler1 = handler.resolve().get();
                    for (int i = 0; i < handler1.getSlots(); i++) {
                        ItemStack stack = handler1.extractItem(i,1,true);
                        switch (Temperature.getTemp(stack)){
                            case COLD -> applyColdEffects(player,i);
                            case HOT -> applyHotEffects(player,i);
                        }
                    }
                }
            }
        }
    }

    static void applyHotEffects(Player player, int i) {
        if (!playerUseProtectiveGear(player)){
            player.setSecondsOnFire(5);
        }
    }
    static void applyColdEffects(Player player, int i) {
        if (!playerUseProtectiveGear(player)) {
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 25, 1));
        }
    }

    static boolean playerUseProtectiveGear(@NotNull Player player){
        ItemStack offhand = player.getOffhandItem();
        ItemStack mainhand = player.getMainHandItem();

        if (checkItem(offhand, HotOrNot.MITTS)){
            if (HonConfig.bothHandsMitts){
                if (checkItem(mainhand, HotOrNot.RIGHT_HAND_MITTS)){
                    tryDamageMitt(mainhand, player, EquipmentSlot.MAINHAND);
                } else {
                    return false;
                }
            }
            tryDamageMitt(offhand, player, EquipmentSlot.OFFHAND);
            return true;
        }
        else if (checkItem(offhand, HotOrNot.WOODEN_TONGS)){
            tryDamageWoodTongs(offhand, player, EquipmentSlot.OFFHAND);
            return true;
        } else if (checkItem(offhand, HotOrNot.IRON_TONGS)){
            tryDamageIronTongs(offhand, player, EquipmentSlot.OFFHAND);
            return true;
        }
        return false;
    }

    static boolean checkItem(@NotNull ItemStack itemstack, @NotNull RegistryObject<Item> toCheck){
        return itemstack.getItem().equals(toCheck.get());
    }
    static void damageStack(@NotNull ItemStack stack, Player player, EquipmentSlot slot){
        stack.hurtAndBreak(1,player, (e) -> e.broadcastBreakEvent(slot));
    }
    static void tryDamageMitt(ItemStack stack, Player player, EquipmentSlot slot) {
        if (HonConfig.mittDamageable){
            damageStack(stack,player,slot);
        }
    }
    static void tryDamageWoodTongs(ItemStack stack, Player player, EquipmentSlot slot){
        if (HonConfig.woodenTongsDamageable){
            damageStack(stack,player,slot);
        }
    }
    static void tryDamageIronTongs(ItemStack stack, Player player, EquipmentSlot slot){
        if (HonConfig.ironTongsDamageable){
            damageStack(stack,player,slot);
        }
    }
}
