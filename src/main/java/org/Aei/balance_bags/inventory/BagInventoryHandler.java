package org.Aei.balance_bags.inventory;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class BagInventoryHandler extends ItemStackHandler {
    private final ItemStack pouchStack;

    public BagInventoryHandler(ItemStack pouchStack, int size) {
        super(size);
        this.pouchStack = pouchStack;

        CompoundTag tag = pouchStack.getOrCreateTag();
        if (tag.contains("Inventory")) {
            deserializeNBT(tag.getCompound("Inventory"));
        }
    }

    @Override
    public int getSlotLimit(int slot) {
        ItemStack stack = getStackInSlot(slot);
        return stack.isEmpty() ? 64 : Math.min(64, stack.getMaxStackSize());
    }

    public boolean isFull() {
        for (int i = 0; i < getSlots(); i++) {
            ItemStack stack = getStackInSlot(i);
            if (stack.isEmpty() || stack.getCount() < stack.getMaxStackSize()) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onContentsChanged(int slot) {
        super.onContentsChanged(slot);
        save();
    }

    public void save() {
        if (pouchStack != null && !pouchStack.isEmpty()) {
            CompoundTag tag = pouchStack.getOrCreateTag();
            tag.put("Inventory", serializeNBT());
        }
    }
}