package org.Aei.balance_bags.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.Aei.balance_bags.BalanceBags;
import org.Aei.balance_bags.client.ClientInputHandler;
import org.Aei.balance_bags.inventory.BagInventoryHandler;
import top.theillusivec4.curios.api.CuriosApi;

@Mod.EventBusSubscriber(modid = BalanceBags.MOD_ID, value = Dist.CLIENT)
public class PouchHudOverlay {

    private static final ResourceLocation POUCH_HUD_TEXTURE = new ResourceLocation(BalanceBags.MOD_ID, "textures/gui/pouch_hud.png");
    private static final ResourceLocation WIDGETS_TEXTURE = new ResourceLocation("textures/gui/widgets.png");

    @SubscribeEvent
    public static void onRenderHud(RenderGuiOverlayEvent.Post event) {
        if (!event.getOverlay().id().equals(VanillaGuiOverlay.HOTBAR.id())) return;

        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null || player.isSpectator()) return;

        CuriosApi.getCuriosHelper().findFirstCurio(player, BalanceBags.POUCH.get()).ifPresent(slotResult -> {
            GuiGraphics graphics = event.getGuiGraphics();
            int screenWidth = mc.getWindow().getGuiScaledWidth();
            int screenHeight = mc.getWindow().getGuiScaledHeight();

            int x = (screenWidth / 2) + 98;
            int y = screenHeight - 22;

            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();

            // Фон панели подсумка
            graphics.blit(POUCH_HUD_TEXTURE, x, y, 0, 0, 60, 22, 60, 22);

            BagInventoryHandler handler = new BagInventoryHandler(slotResult.stack(), 3);
            for (int i = 0; i < 3; i++) {
                // Всегда берем предмет из подсумка!
                ItemStack stack = handler.getStackInSlot(i);

                int itemX = x + 3 + (i * 19);
                int itemY = y + 3;

                if (!stack.isEmpty()) {
                    graphics.renderItem(stack, itemX, itemY);
                    graphics.renderItemDecorations(mc.font, stack, itemX, itemY);
                }

                // Отрисовка ВАНИЛЬНОЙ рамки выделения поверх выбранного слота
                if (i == ClientInputHandler.activePouchSlot) {
                    graphics.blit(WIDGETS_TEXTURE, itemX - 4, itemY - 4, 0, 22, 24, 24, 256, 256);
                }
            }

            RenderSystem.disableBlend();
        });
    }
}