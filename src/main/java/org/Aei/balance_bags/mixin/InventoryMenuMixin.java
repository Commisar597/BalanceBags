package org.Aei.balance_bags.mixin;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.Aei.balance_bags.inventory.BagSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(InventoryMenu.class)
public abstract class InventoryMenuMixin extends RecipeBookMenu {

    public InventoryMenuMixin() {
        super(null, 0);
    }

    @Inject(method = "quickMoveStack", at = @At("HEAD"), cancellable = true)
    private void handleBagQuickMove(Player player, int index, CallbackInfoReturnable<ItemStack> cir) {
        InventoryMenu menu = (InventoryMenu) (Object) this;
        if (index < 0 || index >= menu.slots.size()) return;

        Slot slot = menu.slots.get(index);
        if (slot == null || !slot.hasItem()) return;

        ItemStack stackInSlot = slot.getItem();
        ItemStack originalStack = stackInSlot.copy();

        if (slot instanceof BagSlot) {
            if (!this.moveItemStackTo(stackInSlot, 9, 36, false)) { // Главный инвентарь
                if (!this.moveItemStackTo(stackInSlot, 0, 9, false)) { // Хотбар
                    cir.setReturnValue(ItemStack.EMPTY);
                    return;
                }
            }
            slot.onQuickCraft(stackInSlot, originalStack);
            if (stackInSlot.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
            cir.setReturnValue(originalStack);
            return;
        }

        if (index >= 0 && index < 36) {
            int firstBagSlotIndex = -1;
            for (int i = 46; i < menu.slots.size(); i++) {
                if (menu.slots.get(i) instanceof BagSlot) {
                    firstBagSlotIndex = i;
                    break;
                }
            }

            if (firstBagSlotIndex != -1) {
                if (this.moveItemStackTo(stackInSlot, firstBagSlotIndex, menu.slots.size(), false)) {
                    if (stackInSlot.isEmpty()) {
                        slot.setByPlayer(ItemStack.EMPTY);
                    } else {
                        slot.setChanged();
                    }
                    cir.setReturnValue(originalStack);
                }
            }
        }
    }
}