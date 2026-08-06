package org.Aei.balance_bags.crafting;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapelessRecipe;

public class BeltCuttingRecipe extends ShapelessRecipe {

    // Оставляем стандартный конструктор, который ожидает сериализатор
    public BeltCuttingRecipe(ResourceLocation id, CraftingBookCategory category) {
        super(id, "", category, new ItemStack(org.Aei.balance_bags.BalanceBags.DRAWSTRING.get(), 3),
                NonNullList.of(Ingredient.EMPTY,
                        Ingredient.of(org.Aei.balance_bags.BalanceBags.BELT.get()),
                        Ingredient.of(Items.SHEARS)
                ));
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingContainer container) {
        NonNullList<ItemStack> remaining = NonNullList.withSize(container.getContainerSize(), ItemStack.EMPTY);

        for (int i = 0; i < container.getContainerSize(); ++i) {
            ItemStack stack = container.getItem(i);
            if (stack.is(Items.SHEARS)) {
                ItemStack shears = stack.copy();
                if (shears.hurt(1, RandomSource.create(), null)) {
                    shears.shrink(1);
                }
                remaining.set(i, shears);
            }
        }
        return remaining;
    }
}