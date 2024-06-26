package com.portingdeadmods.hon.datagen;

import com.portingdeadmods.hon.item.HonItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class Recipes extends RecipeProvider implements IConditionBuilder {
    public Recipes(PackOutput p_248933_) {
        super(p_248933_);
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, HonItems.MITTS.get())
                .pattern("LLL")
                .pattern(" LL")
                .pattern("LLL")
                .define('L', Items.LEATHER)
                .unlockedBy(getHasName(Items.LEATHER),has(Items.LEATHER)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, HonItems.RIGHT_HAND_MITTS.get())
                .pattern("LLL")
                .pattern("LL ")
                .pattern("LLL")
                .define('L', Items.LEATHER)
                .unlockedBy(getHasName(Items.LEATHER),has(Items.LEATHER)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, HonItems.WOODEN_TONGS.get())
                .pattern("S S").pattern("S S").pattern(" S ")
                .define('S', Items.STICK)
                .unlockedBy(getHasName(Items.STICK),has(Items.STICK)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, HonItems.IRON_TONGS.get())
                .pattern("I I").pattern("I I").pattern(" I ")
                .define('I', Items.IRON_INGOT)
                .unlockedBy(getHasName(Items.IRON_INGOT),has(Items.IRON_INGOT)).save(consumer);
    }
}
