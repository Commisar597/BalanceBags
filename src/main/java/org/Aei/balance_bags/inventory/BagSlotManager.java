package org.Aei.balance_bags.inventory;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import org.Aei.balance_bags.BalanceBags;

import java.lang.reflect.Method;

public class BagSlotManager {

    private static final Method ADD_SLOT_METHOD = findMethod(AbstractContainerMenu.class, new String[]{"m_38897_", "addSlot"}, Slot.class);

    /**
     * Вызывается один раз при инициализации меню инвентаря игрока.
     * Создает все потенциальные слоты сумок, которые будут скрыты/показаны через isActive().
     */
    public static void initBagSlots(Player player, InventoryMenu menu) {
        if (player == null || menu == null) return;

        // --- 1. Рюкзак (18 слотов) — Слева ---
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 3; col++) {
                int slotIndex = row * 3 + col;
                int x = -64 + (col * 18);
                int y = 38 + (row * 18);
                addBagSlot(menu, new BagSlot(player, new BagInventoryHandler(player, BalanceBags.BACKPACK, 18), slotIndex, x, y, BalanceBags.BACKPACK));
            }
        }

        // --- 2. Сумка (12 слотов) — Справа вверху ---
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 3; col++) {
                int slotIndex = row * 3 + col;
                int x = 188 + (col * 18);
                int y = 23 + (row * 18);
                addBagSlot(menu, new BagSlot(player, new BagInventoryHandler(player, BalanceBags.REGULAR_BAG, 12), slotIndex, x, y, BalanceBags.REGULAR_BAG));
            }
        }

        // --- 3. Подсумок (3 слота) — Справа внизу ---
        for (int col = 0; col < 3; col++) {
            int x = 188 + (col * 18);
            int y = 133;
            addBagSlot(menu, new BagSlot(player, new BagInventoryHandler(player, BalanceBags.POUCH, 3), col, x, y, BalanceBags.POUCH));
        }

        // --- 4. Большая сумка (27 слотов) — Снизу ---
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                int slotIndex = row * 9 + col;
                int x = 8 + (col * 18);
                int y = 178 + (row * 18);
                addBagSlot(menu, new BagSlot(player, new BagInventoryHandler(player, BalanceBags.DUFFEL_BAG, 27), slotIndex, x, y, BalanceBags.DUFFEL_BAG));
            }
        }
    }

    private static void addBagSlot(InventoryMenu menu, BagSlot slot) {
        try {
            ADD_SLOT_METHOD.invoke(menu, slot);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Method findMethod(Class<?> clazz, String[] names, Class<?>... parameterTypes) {
        for (String name : names) {
            try {
                Method m = clazz.getDeclaredMethod(name, parameterTypes);
                m.setAccessible(true);
                return m;
            } catch (Exception ignored) {}
        }
        try {
            return ObfuscationReflectionHelper.findMethod(clazz, names[0], parameterTypes);
        } catch (Exception e) {
            throw new RuntimeException("Could not find method " + names[0], e);
        }
    }
}