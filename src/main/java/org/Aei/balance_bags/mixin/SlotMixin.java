package org.Aei.balance_bags.mixin;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.Aei.balance_bags.BalanceBags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(Slot.class)
public abstract class SlotMixin {

    @Inject(
            method = "mayPlace(Lnet/minecraft/world/item/ItemStack;)Z",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private void preventOffhandPlacement(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        Slot self = (Slot) (Object) this;
        if (self.getContainerSlot() == Inventory.SLOT_OFFHAND && self.container instanceof Inventory inventory) {
            boolean hasDuffelBag = CuriosApi.getCuriosHelper()
                    .findFirstCurio(inventory.player, BalanceBags.DUFFEL_BAG.get())
                    .isPresent();

            if (hasDuffelBag) {
                cir.setReturnValue(false);
            }
        }
    }
}