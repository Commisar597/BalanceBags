package org.Aei.balance_bags.inventory;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.Aei.balance_bags.item.BagItem;

public class BagSlot extends SlotItemHandler {
    private final ItemStack bagStack;

    public BagSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, ItemStack bagStack) {
        super(itemHandler, index, xPosition, yPosition);
        this.bagStack = bagStack;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        // Запрещаем класть любые сумки внутрь слотов сумок (защита от дюпа/исчезновения)
        if (stack.getItem() instanceof BagItem) {
            return false;
        }
        return super.mayPlace(stack);
    }

    public ItemStack getBagStack() {
        return bagStack;
    }
}