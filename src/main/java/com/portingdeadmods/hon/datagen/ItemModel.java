package com.portingdeadmods.hon.datagen;

import com.portingdeadmods.hon.HotOrNot;
import com.portingdeadmods.hon.item.HonItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ItemModel extends ItemModelProvider{

    public ItemModel(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        this.item(HonItems.WOODEN_TONGS);
        this.item(HonItems.IRON_TONGS);
        this.item(HonItems.MITTS, "mittens_left_hand");
        this.item(HonItems.RIGHT_HAND_MITTS, "mittens_right_hand");
    }

    private ItemModelBuilder item(RegistryObject<Item> item){
        return item(item, item.getId().getPath());
    }
    private ItemModelBuilder item(RegistryObject<Item> item, String textureName){
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(HotOrNot.MODID, "item/" + textureName));
    }
}
