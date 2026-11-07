package org.Aei.balance_bags.mixin;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import org.Aei.balance_bags.BalanceBags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(AbstractContainerScreen.class)
public abstract class AbstractContainerScreenMixin {

    @Inject(
            method = "renderSlot(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/world/inventory/Slot;)V",
            at = @At("TAIL"),
            require = 0
    )
    private void renderDisabledOffhandOverlay(GuiGraphics graphics, Slot slot, CallbackInfo ci) {
        if (slot.container instanceof Inventory inventory) {
            if (slot.getContainerSlot() == Inventory.SLOT_OFFHAND) {
                boolean hasDuffelBag = CuriosApi.getCuriosHelper()
                        .findFirstCurio(inventory.player, BalanceBags.DUFFEL_BAG.get())
                        .isPresent();

                if (hasDuffelBag) {
                    int x = slot.x;
                    int y = slot.y;

                    graphics.fill(x, y, x + 16, y + 16, 0xCC111111);
                }
            }
        }
    }
}