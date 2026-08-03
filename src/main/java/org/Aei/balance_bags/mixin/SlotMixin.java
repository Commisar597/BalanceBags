package org.Aei.balance_bags.mixin;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.Aei.balance_bags.BalanceBags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(Slot.class)
public abstract class SlotMixin {

    @Shadow public abstract int getContainerSlot();

    @Inject(method = "mayPlace", at = @At("HEAD"), cancellable = true)
    private void preventOffhandPlacement(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        Slot self = (Slot) (Object) this;
        // Проверяем, принадлежит ли слот инвентарю игрока и является ли он слотом левой руки (index 40)
        if (self.container instanceof Inventory inventory) {
            if (this.getContainerSlot() == Inventory.SLOT_OFFHAND) {
                boolean hasDuffelBag = CuriosApi.getCuriosHelper()
                        .findFirstCurio(inventory.player, BalanceBags.DUFFEL_BAG.get())
                        .isPresent();

                if (hasDuffelBag) {
                    cir.setReturnValue(false);
                }
            }
        }
    }
}