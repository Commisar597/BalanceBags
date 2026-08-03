package org.Aei.balance_bags.mixin;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.Aei.balance_bags.client.ClientInputHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Gui.class)
public class GuiMixin {

    @Redirect(
            method = "renderHotbar",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V"
            )
    )
    private void hideVanillaHotbarSelection(GuiGraphics graphics, ResourceLocation texture, int x, int y, int u, int v, int width, int height) {
        // Проверяем UV выборки рамки хотбара (U=0, V=22)
        if (u == 0 && v == 22 && ClientInputHandler.activePouchSlot != -1) {
            return; // Пропускаем отрисовку ванильной рамки выделения
        }
        graphics.blit(texture, x, y, u, v, width, height);
    }
}