package com.kitp13.hon;

import com.kitp13.hon.config.HonConfig;
import com.kitp13.hon.item.HonItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod(HotOrNot.MODID)
public class HotOrNot {
    public static final String MODID = "hon";
    public static final String MODNAME = "Hot Or Not Redux";
    public static final String VERSION = "1.0.0";
    public static Logger LOGGER = LogManager.getLogger(MODID);

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<CreativeModeTab> TABREGISTER = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static RegistryObject<Item> MITTS = ITEMS.register("mitttens", () -> new HonItem(1023));
    public static final RegistryObject<Item> WOODEN_TONGS = ITEMS.register("wooden_tongs", () -> new HonItem(127));
    public static final RegistryObject<Item> IRON_TONGS = ITEMS.register("iron_tongs", () -> new HonItem(511));

    public static final RegistryObject<CreativeModeTab> HOT_OR_NOT_TAB = TABREGISTER.register("hon", () ->
            CreativeModeTab.builder()
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .title(Component.translatable("item_group."+MODID+".hon"))
                    .icon(()->new ItemStack(Items.LAVA_BUCKET))
                    .displayItems((p,out) -> {
                            out.accept(Items.LAVA_BUCKET);
                            out.accept(Items.WATER_BUCKET);
                            out.accept(MITTS.get());
                            out.accept(WOODEN_TONGS.get());
                            out.accept(IRON_TONGS.get());
                    })
                    .build());

    public HotOrNot() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(modEventBus);
        TABREGISTER.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::setup);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, HonConfig.SPEC);

    }
    private void setup(final FMLCommonSetupEvent event) {
        //PacketHandler.registerMessages();
    }
}