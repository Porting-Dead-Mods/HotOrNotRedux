package com.kitp13.hon.datagen;

import com.kitp13.hon.HotOrNot;
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
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, HotOrNot.MITTS.get())
                .pattern("LLL")
                .pattern(" LL")
                .pattern("LLL")
                .define('L', Items.LEATHER)
                .unlockedBy(getHasName(Items.LEATHER),has(Items.LEATHER)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, HotOrNot.RIGHT_HAND_MITTS.get())
                .pattern("LLL")
                .pattern("LL ")
                .pattern("LLL")
                .define('L', Items.LEATHER)
                .unlockedBy(getHasName(Items.LEATHER),has(Items.LEATHER)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, HotOrNot.WOODEN_TONGS.get())
                .pattern("S S").pattern("S S").pattern(" S ")
                .define('S', Items.STICK)
                .unlockedBy(getHasName(Items.STICK),has(Items.STICK)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, HotOrNot.IRON_TONGS.get())
                .pattern("I I").pattern("I I").pattern(" I ")
                .define('I', Items.IRON_INGOT)
                .unlockedBy(getHasName(Items.IRON_INGOT),has(Items.IRON_INGOT)).save(consumer);
    }
}
