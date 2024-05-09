package com.portingdeadmods.hon.server;

import com.portingdeadmods.hon.HotOrNot;
import com.portingdeadmods.hon.config.HonConfig;
import com.portingdeadmods.hon.enchant.HonEnchants;
import com.portingdeadmods.hon.item.HonItems;
import com.portingdeadmods.hon.utils.Item;
import com.portingdeadmods.hon.utils.Temperature;
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
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.IItemHandler;
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

        for (int i =0; i<4; i++){
            if (player.getInventory().armor.get(i).getEnchantmentLevel(HonEnchants.TERMAL_PROTECTION.get()) >= 1){
                return true;
            }
        }

        if (Item.checkItem(offhand, HonItems.MITTS)){
            if (HonConfig.bothHandsMitts){
                if (Item.checkItem(mainhand, HonItems.RIGHT_HAND_MITTS)){
                    tryDamageMitt(mainhand, player, EquipmentSlot.MAINHAND);
                } else {
                    return false;
                }
            }
            tryDamageMitt(offhand, player, EquipmentSlot.OFFHAND);
            return true;
        }
        else if (Item.checkItem(offhand, HonItems.WOODEN_TONGS)){
            tryDamageWoodTongs(offhand, player, EquipmentSlot.OFFHAND);
            return true;
        } else if (Item.checkItem(offhand, HonItems.IRON_TONGS)){
            tryDamageIronTongs(offhand, player, EquipmentSlot.OFFHAND);
            return true;
        }
        return false;
    }


    static void tryDamageMitt(ItemStack stack, Player player, EquipmentSlot slot) {
        if (HonConfig.mittDamageable){
            Item.damageStack(stack,player,slot);
        }
    }
    static void tryDamageWoodTongs(ItemStack stack, Player player, EquipmentSlot slot){
        if (HonConfig.woodenTongsDamageable){
            Item.damageStack(stack,player,slot);
        }
    }
    static void tryDamageIronTongs(ItemStack stack, Player player, EquipmentSlot slot){
        if (HonConfig.ironTongsDamageable){
            Item.damageStack(stack,player,slot);
        }
    }
}
