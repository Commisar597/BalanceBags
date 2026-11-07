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

@Mod.EventBusSubscriber(modid = BalanceBags.MOD_ID)
public class BagSlotEvents {

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = (Player) event.getEntity();
        BagSlotManager.initBagSlots(player, player.inventoryMenu);
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        Player player = (Player) event.getEntity();
        BagSlotManager.initBagSlots(player, player.inventoryMenu);
    }
    
    @Mod.EventBusSubscriber(modid = BalanceBags.MOD_ID, value = Dist.CLIENT)
    public static class ClientEvents {
        @SubscribeEvent
        public static void onOpenInventory(ScreenEvent.Init.Post event) {
            if (event.getScreen() instanceof InventoryScreen screen) {
                Player player = Minecraft.getInstance().player;
                if (player != null) {
                    BagSlotManager.initBagSlots(player, screen.getMenu());
                }
            }
        }
    }
}
