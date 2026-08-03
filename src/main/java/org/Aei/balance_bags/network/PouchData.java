package org.Aei.balance_bags.network;

import net.minecraft.world.entity.player.Player;
import org.Aei.balance_bags.client.ClientInputHandler;
import org.Aei.balance_bags.inventory.PouchLinkManager;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PouchData {
    private static final Map<UUID, Integer> SERVER_SLOTS = new ConcurrentHashMap<>();

    public static void setActiveSlot(Player player, int newBagSlot, int newHotbarIndex) {
        int oldSlot = getActiveSlot(player);
        int oldHotbarIndex = player.getInventory().selected;

        if (oldSlot != -1) {
            PouchLinkManager.deactivate(player, oldHotbarIndex, oldSlot);
        }

        // Сохраняем новый активный слот на сервере
        if (newBagSlot == -1) {
            SERVER_SLOTS.remove(player.getUUID());
        } else {
            SERVER_SLOTS.put(player.getUUID(), newBagSlot);
        }

        if (newBagSlot != -1) {
            PouchLinkManager.activate(player, newHotbarIndex, newBagSlot);
        }
    }

    public static int getActiveSlot(Player player) {
        if (player.level().isClientSide) {
            return ClientInputHandler.activePouchSlot;
        }
        return SERVER_SLOTS.getOrDefault(player.getUUID(), -1);
    }
}