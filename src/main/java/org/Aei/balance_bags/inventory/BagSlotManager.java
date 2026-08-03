package org.Aei.balance_bags.inventory;

import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import org.Aei.balance_bags.BalanceBags;
import top.theillusivec4.curios.api.CuriosApi;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class BagSlotManager {

    private static final Method ADD_SLOT_METHOD = findMethod(AbstractContainerMenu.class, new String[]{"m_38897_", "addSlot"}, Slot.class);
    private static final Field REMOTE_SLOTS_FIELD = findField(AbstractContainerMenu.class, "f_38845_", "remoteSlots");
    private static final Field LAST_SLOTS_FIELD = findField(AbstractContainerMenu.class, "f_38844_", "lastSlots");

    @SuppressWarnings("unchecked")
    public static void updateBagSlots(Player player, InventoryMenu menu) {
        if (player == null || menu == null) return;

        // 1. Очищаем прошлые слоты сумок
        menu.slots.removeIf(slot -> slot instanceof BagSlot);

        // Синхронизируем размеры приватных списков remoteSlots и lastSlots
        try {
            NonNullList<ItemStack> remoteSlots = (NonNullList<ItemStack>) REMOTE_SLOTS_FIELD.get(menu);
            NonNullList<ItemStack> lastSlots = (NonNullList<ItemStack>) LAST_SLOTS_FIELD.get(menu);

            while (remoteSlots.size() > menu.slots.size()) {
                remoteSlots.remove(remoteSlots.size() - 1);
            }
            while (lastSlots.size() > menu.slots.size()) {
                lastSlots.remove(lastSlots.size() - 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // --- 1. Рюкзак (18 слотов) — Слева ---
        CuriosApi.getCuriosHelper().findFirstCurio(player, BalanceBags.BACKPACK.get()).ifPresent(slotResult -> {
            ItemStack stack = slotResult.stack();
            BagInventoryHandler handler = new BagInventoryHandler(stack, 18);
            for (int row = 0; row < 6; row++) {
                for (int col = 0; col < 3; col++) {
                    int slotIndex = row * 3 + col;
                    int x = -64 + (col * 18);
                    int y = 38 + (row * 18);
                    addBagSlot(menu, new BagSlot(handler, slotIndex, x, y, stack));
                }
            }
        });

        // --- 2. Сумка (12 слотов) — Справа вверху ---
        CuriosApi.getCuriosHelper().findFirstCurio(player, BalanceBags.REGULAR_BAG.get()).ifPresent(slotResult -> {
            ItemStack stack = slotResult.stack();
            BagInventoryHandler handler = new BagInventoryHandler(stack, 12);
            for (int row = 0; row < 4; row++) {
                for (int col = 0; col < 3; col++) {
                    int slotIndex = row * 3 + col;
                    int x = 188 + (col * 18);
                    int y = 23 + (row * 18);
                    addBagSlot(menu, new BagSlot(handler, slotIndex, x, y, stack));
                }
            }
        });

        // --- 3. Подсумок (3 слота) — Справа внизу ---
        CuriosApi.getCuriosHelper().findFirstCurio(player, BalanceBags.POUCH.get()).ifPresent(slotResult -> {
            ItemStack stack = slotResult.stack();
            BagInventoryHandler handler = new BagInventoryHandler(stack, 3);
            for (int col = 0; col < 3; col++) {
                int x = 188 + (col * 18);
                int y = 133;
                addBagSlot(menu, new BagSlot(handler, col, x, y, stack));
            }
        });

        // --- 4. Большая сумка (27 слотов) — Снизу ---
        CuriosApi.getCuriosHelper().findFirstCurio(player, BalanceBags.DUFFEL_BAG.get()).ifPresent(slotResult -> {
            ItemStack stack = slotResult.stack();
            BagInventoryHandler handler = new BagInventoryHandler(stack, 27);
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 9; col++) {
                    int slotIndex = row * 9 + col;
                    int x = 8 + (col * 18);
                    int y = 178 + (row * 18);
                    addBagSlot(menu, new BagSlot(handler, slotIndex, x, y, stack));
                }
            }
        });
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

    private static Field findField(Class<?> clazz, String srgName, String fieldName) {
        try {
            Field f = clazz.getDeclaredField(fieldName);
            f.setAccessible(true);
            return f;
        } catch (Exception ignored) {}
        try {
            Field f = clazz.getDeclaredField(srgName);
            f.setAccessible(true);
            return f;
        } catch (Exception ignored) {}
        return ObfuscationReflectionHelper.findField(clazz, srgName);
    }
}