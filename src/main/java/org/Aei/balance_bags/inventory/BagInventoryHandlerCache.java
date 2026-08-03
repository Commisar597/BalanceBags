package org.Aei.balance_bags.inventory;

import net.minecraft.world.item.ItemStack;

import java.util.Map;
import java.util.WeakHashMap;

public class BagInventoryHandlerCache {
    private static final Map<ItemStack, BagInventoryHandler> CACHE = new WeakHashMap<>();
    public static final int BAG_SIZE = 3;

    public static BagInventoryHandler get(ItemStack pouchStack) {
        return CACHE.computeIfAbsent(pouchStack, s -> new BagInventoryHandler(s, BAG_SIZE));
    }

    public static void invalidate(ItemStack pouchStack) {
        CACHE.remove(pouchStack);
    }
}