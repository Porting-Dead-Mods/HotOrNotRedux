package com.kitp13.hon.network.packets;

import com.kitp13.hon.HotOrNot;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static SimpleChannel INSTANCE;

    public static void registerMessages(){
        INSTANCE = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(HotOrNot.MODID, "hot"), () -> PROTOCOL_VERSION,PROTOCOL_VERSION::equals,PROTOCOL_VERSION::equals
        );
        int index = 0;
        INSTANCE.registerMessage(index++,
                HotPacket.class,HotPacket::encode,HotPacket::decode,HotPacket::handle);
        INSTANCE.registerMessage(index++,
                ColdPacket.class,ColdPacket::encode,ColdPacket::decode,ColdPacket::handle);
    }
}
