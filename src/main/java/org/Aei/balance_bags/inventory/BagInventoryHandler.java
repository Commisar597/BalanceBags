package org.Aei.balance_bags.inventory;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.function.Supplier;

public class BagInventoryHandler extends ItemStackHandler {
    private final Player player;
    private final Supplier<Item> bagItemSupplier;

    public BagInventoryHandler(Player player, Supplier<Item> bagItemSupplier, int size) {
        super(size);
        this.player = player;
        this.bagItemSupplier = bagItemSupplier;
    }

    public BagInventoryHandler(ItemStack staticStack, int size) {
        super(size);
        this.player = null;
        this.bagItemSupplier = () -> staticStack.getItem();
        if (!staticStack.isEmpty()) {
            CompoundTag tag = staticStack.getOrCreateTag();
            if (tag.contains("Inventory")) {
                deserializeNBT(tag.getCompound("Inventory"));
            }
        }
    }

    private ItemStack getBagStack() {
        if (player == null || bagItemSupplier == null) return ItemStack.EMPTY;
        return CuriosApi.getCuriosHelper()
                .findFirstCurio(player, bagItemSupplier.get())
                .map(slotResult -> slotResult.stack())
                .orElse(ItemStack.EMPTY);
    }

    @Override
    public int getSlots() {
        return super.getSlots();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        loadFromBag();
        return super.getStackInSlot(slot);
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        loadFromBag();
        ItemStack result = super.insertItem(slot, stack, simulate);
        if (!simulate) {
            saveToBag();
        }
        return result;
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        loadFromBag();
        ItemStack result = super.extractItem(slot, amount, simulate);
        if (!simulate) {
            saveToBag();
        }
        return result;
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        loadFromBag();
        super.setStackInSlot(slot, stack);
        saveToBag();
    }

    @Override
    public int getSlotLimit(int slot) {
        ItemStack stack = getStackInSlot(slot);
        return stack.isEmpty() ? 64 : Math.min(64, stack.getMaxStackSize());
    }

    public boolean isFull() {
        loadFromBag();
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
        saveToBag();
    }

    private void loadFromBag() {
        ItemStack bag = getBagStack();
        if (!bag.isEmpty()) {
            CompoundTag tag = bag.getTag();
            if (tag != null && tag.contains("Inventory")) {
                deserializeNBT(tag.getCompound("Inventory"));
            } else {
                // Если тега инвентаря еще нет, очищаем локальный буфер хендлера
                for (int i = 0; i < stacks.size(); i++) {
                    stacks.set(i, ItemStack.EMPTY);
                }
            }
        }
    }

    public void saveToBag() {
        ItemStack bag = getBagStack();
        if (!bag.isEmpty()) {
            CompoundTag tag = bag.getOrCreateTag();
            tag.put("Inventory", serializeNBT());
        }
    }
}
