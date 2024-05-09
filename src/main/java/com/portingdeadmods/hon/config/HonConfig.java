package com.portingdeadmods.hon.config;

import com.portingdeadmods.hon.HotOrNot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = HotOrNot.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class HonConfig {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.IntValue COLD_THRESHOLD = BUILDER
            .comment("All fluids with temperature below this are classed as 'Cold' fluids")
            .defineInRange("coldThreshold", 500,0,Integer.MAX_VALUE);
    private static final ForgeConfigSpec.IntValue HOT_THRESHOLD = BUILDER
            .comment("All fluids with temperature above this are classed as 'Hot' fluids")
            .defineInRange("hotThreshold", 499,0,Integer.MAX_VALUE);
    private static final ForgeConfigSpec.IntValue UPDATE_SPEED = BUILDER
            .comment("The Interval ,in ticks, in which the players inventory is checked")
            .defineInRange("updateSpeed", 20,1,Integer.MAX_VALUE);
    private static final ForgeConfigSpec.BooleanValue MITTS_TAKE_DAMAGE = BUILDER
            .comment("Whether Mitts take damage")
            .define("mittDamageable", true);
    private static final ForgeConfigSpec.BooleanValue BOTH_HANDS_MITTS = BUILDER
            .comment("Whether the player needs to have Mitts in both hands in order to not take damage")
            .define("bothHandsMitts", false);
    private static final ForgeConfigSpec.BooleanValue WOODEN_TAKE_DAMAGE = BUILDER
            .comment("Whether Wooden Tongs take damage")
            .define("woodenTongsDamageable", true);
    private static final ForgeConfigSpec.BooleanValue IRON_TAKE_DAMAGE = BUILDER
            .comment("Whether Iron Tongs take damage")
            .define("ironTongsDamageable", true);
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> HOT_STRINGS = BUILDER
            .comment("A list of items to register as 'Hot' Items.")
            .defineListAllowEmpty("customHotItems", List.of("minecraft:nether_star"), HonConfig::validateItemName);
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> HOT_BLACKLIST_STRINGS = BUILDER
            .comment("A list of items to blacklist as 'Hot' Items.")
            .defineListAllowEmpty("blacklistHotItems", List.of("minecraft:blaze_powder"), HonConfig::validateItemName);
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> COLD_STRINGS = BUILDER
            .comment("A list of items to register as 'Cold' Items.")
            .defineListAllowEmpty("customColdItems", List.of("minecraft:ghast_tear"), HonConfig::validateItemName);
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> COLD_BLACKLIST_STRINGS = BUILDER
            .comment("A list of items to blacklist as 'Cold' Items.")
            .defineListAllowEmpty("blacklistColdItems", List.of("minecraft:ice"), HonConfig::validateItemName);
    private static final ForgeConfigSpec.BooleanValue TOOLTIPS = BUILDER
            .comment("Whether to add Tooltips showing if it is a 'hot' or 'cold' item")
            .define("tooltips", true);
    public static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean mittDamageable;
    public static boolean woodenTongsDamageable;
    public static boolean ironTongsDamageable;
    public static boolean tooltips;
    public static boolean bothHandsMitts;

    public static Set<Item> customHotItems;
    public static Set<Item> blacklistHotItems;
    public static Set<Item> customColdItems;
    public static Set<Item> blacklistColdItems;
    public static int coldThreshold;
    public static int hotThreshold;

    public static int updateSpeed;


    private static boolean validateItemName(final Object obj)
    {
        return obj instanceof final String itemName && ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemName));
    }
    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        bothHandsMitts = BOTH_HANDS_MITTS.get();
        tooltips = TOOLTIPS.get();
        coldThreshold = COLD_THRESHOLD.get();
        hotThreshold = HOT_THRESHOLD.get();
        mittDamageable = MITTS_TAKE_DAMAGE.get();
        woodenTongsDamageable = WOODEN_TAKE_DAMAGE.get();
        ironTongsDamageable = IRON_TAKE_DAMAGE.get();
        customHotItems = HOT_STRINGS.get().stream()
                .map(itemName -> ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName)))
                .collect(Collectors.toSet());
        blacklistHotItems = HOT_BLACKLIST_STRINGS.get().stream().map(itemName -> ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName)))
                .collect(Collectors.toSet());
        customColdItems = COLD_STRINGS.get().stream()
                .map(itemName -> ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName)))
                .collect(Collectors.toSet());
        blacklistColdItems = COLD_BLACKLIST_STRINGS.get().stream().map(itemName -> ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName)))
                .collect(Collectors.toSet());
        updateSpeed = UPDATE_SPEED.get();
    }
}
