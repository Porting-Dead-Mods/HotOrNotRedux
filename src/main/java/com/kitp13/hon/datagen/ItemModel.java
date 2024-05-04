package com.kitp13.hon.datagen;

import com.kitp13.hon.HotOrNot;
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
        this.item(HotOrNot.WOODEN_TONGS);
        this.item(HotOrNot.IRON_TONGS);
        this.item(HotOrNot.MITTS);
    }

    private ItemModelBuilder item(RegistryObject<Item> item){
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(HotOrNot.MODID, "item/" + item.getId().getPath()));
    }
}
