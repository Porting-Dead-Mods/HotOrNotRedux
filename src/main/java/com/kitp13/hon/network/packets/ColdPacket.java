package com.kitp13.hon.network.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class ColdPacket {
    private boolean appltEffects;
    private UUID player;

    public ColdPacket(boolean fire, UUID player) {
        this.appltEffects = fire;
        this.player = player;
    }
    public static void encode(ColdPacket msg, FriendlyByteBuf buf) {
        buf.writeBoolean(msg.appltEffects);
        buf.writeUUID(msg.player);
    }
    public static ColdPacket decode(FriendlyByteBuf buf) {
        return new ColdPacket(buf.readBoolean(),buf.readUUID());
    }
    public static void handle(ColdPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender().getServer().getPlayerList().getPlayer(msg.player);
            if (player!=null && msg.appltEffects){
                //                                                  Effect, Duration, Amplifier
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,21,1));
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
