package org.Aei.balance_bags.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.Aei.balance_bags.BalanceBags;
import org.Aei.balance_bags.inventory.BagSlotManager;
import top.theillusivec4.curios.api.event.CurioChangeEvent;

@Mod.EventBusSubscriber(modid = BalanceBags.MOD_ID)
public class BagSlotEvents {

    // 1. Обновляем слоты на сервере при экипировке/снятии сумок в Curios
    @SubscribeEvent
    public static void onCurioChange(CurioChangeEvent event) {
        if (event.getEntity() instanceof Player player) {
            BagSlotManager.updateBagSlots(player, player.inventoryMenu);
        }
    }

    // 2. Обновляем слоты при входе игрока и респавне
    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        BagSlotManager.updateBagSlots(event.getEntity(), event.getEntity().inventoryMenu);
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        BagSlotManager.updateBagSlots(event.getEntity(), event.getEntity().inventoryMenu);
    }

    // 3. Клиентская синхронизация слотов при открытии GUI инвентаря
    @Mod.EventBusSubscriber(modid = BalanceBags.MOD_ID, value = Dist.CLIENT)
    public static class ClientEvents {
        @SubscribeEvent
        public static void onOpenInventory(ScreenEvent.Init.Post event) {
            if (event.getScreen() instanceof InventoryScreen screen) {
                Player player = Minecraft.getInstance().player;
                if (player != null) {
                    BagSlotManager.updateBagSlots(player, screen.getMenu());
                }
            }
        }
    }
}