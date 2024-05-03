package com.kitp13.hon.network.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class HotPacket {
    private boolean setOnFire;
    private UUID player;

    public HotPacket(boolean fire, UUID player) {
        this.setOnFire = fire;
        this.player = player;
    }
    public static void encode(HotPacket msg, FriendlyByteBuf buf) {
        buf.writeBoolean(msg.setOnFire);
        buf.writeUUID(msg.player);
    }
    public static HotPacket decode(FriendlyByteBuf buf) {
        return new HotPacket(buf.readBoolean(),buf.readUUID());
    }
    public static void handle(HotPacket msg, Supplier<NetworkEvent.Context> ctx) {

        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender().getServer().getPlayerList().getPlayer(msg.player);
            if (player!=null && msg.setOnFire){
                player.setSecondsOnFire(5);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
