package org.Aei.balance_bags.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.Aei.balance_bags.BalanceBags;
import org.Aei.balance_bags.network.PacketHandler;
import org.Aei.balance_bags.network.ServerboundSelectPouchSlotPacket;
import top.theillusivec4.curios.api.CuriosApi;

@Mod.EventBusSubscriber(modid = BalanceBags.MOD_ID, value = Dist.CLIENT)
public class ClientInputHandler {

    public static int activePouchSlot = -1;

    /**
     * Устанавливает активный слот подсумка и отправляет пакет синхронизации на сервер.
     */
    public static void setSlot(int newSlot) {
        if (activePouchSlot != newSlot) {
            activePouchSlot = newSlot;
            // Отправляем пакет на сервер, чтобы он знал о смене активного слота
            PacketHandler.sendToServer(new ServerboundSelectPouchSlotPacket(newSlot));
        }
    }

    @SubscribeEvent
    public static void onMouseScroll(InputEvent.MouseScrollingEvent event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;

        boolean hasPouch = CuriosApi.getCuriosHelper()
                .findFirstCurio(player, BalanceBags.POUCH.get())
                .isPresent();

        if (!hasPouch) {
            if (activePouchSlot != -1) {
                setSlot(-1);
            }
            return;
        }

        double scroll = event.getScrollDelta();
        if (scroll == 0) return;

        int currentVanillaSlot = player.getInventory().selected;

        if (scroll < 0) { // Вперед / вниз
            if (activePouchSlot != -1) {
                if (activePouchSlot < 2) {
                    setSlot(activePouchSlot + 1);
                    event.setCanceled(true);
                } else {
                    setSlot(-1);
                    player.getInventory().selected = 0;
                    event.setCanceled(true);
                }
            } else if (currentVanillaSlot == 8) {
                setSlot(0);
                event.setCanceled(true);
            }
        } else { // Назад / вверх
            if (activePouchSlot != -1) {
                if (activePouchSlot > 0) {
                    setSlot(activePouchSlot - 1);
                    event.setCanceled(true);
                } else {
                    setSlot(-1);
                    player.getInventory().selected = 8;
                    event.setCanceled(true);
                }
            } else if (currentVanillaSlot == 0) {
                setSlot(2);
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;

        // Если игрок нажимает стандартные цифры 1-9, снимаем выделение с подсумка
        for (int i = 0; i < 9; i++) {
            if (Minecraft.getInstance().options.keyHotbarSlots[i].consumeClick()) {
                setSlot(-1);
                player.getInventory().selected = i;
                break;
            }
        }

        // Горячие клавиши подсумка
        if (ModKeyMappings.POUCH_SLOT_1.consumeClick()) setSlot(0);
        if (ModKeyMappings.POUCH_SLOT_2.consumeClick()) setSlot(1);
        if (ModKeyMappings.POUCH_SLOT_3.consumeClick()) setSlot(2);
    }
}