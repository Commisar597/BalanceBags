package org.Aei.balance_bags.inventory;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.Aei.balance_bags.BalanceBags;
import org.Aei.balance_bags.network.PouchData;
import top.theillusivec4.curios.api.CuriosApi;

public class PouchLinkManager {

    // Хранит "спрятанный" реальный предмет хотбара, пока слот сумки активен
    private static final String STASH_TAG = "PouchStash";

    /**
     * Вызывать ПЕРЕД тем как activeSlot поменяется на новый (или на -1).
     * Возвращает в реальный хотбар исходный предмет, финально сохраняет
     * актуальное состояние в сумку.
     */
    public static void deactivate(Player player, int hotbarIndex, int bagSlot) {
        if (hotbarIndex < 0) return;

        CuriosApi.getCuriosHelper().findFirstCurio(player, BalanceBags.POUCH.get()).ifPresent(slotResult -> {
            ItemStack pouchStack = slotResult.stack();
            BagInventoryHandler handler = BagInventoryHandlerCache.get(pouchStack);

            // 1. Финально забираем то, что сейчас реально лежит в хотбаре, обратно в сумку
            ItemStack currentReal = player.getInventory().getItem(hotbarIndex);
            handler.setStackInSlot(bagSlot, currentReal.copy());

            // 2. Возвращаем предмет, который был в хотбаре ДО активации
            CompoundTag pouchTag = pouchStack.getOrCreateTag();
            CompoundTag stashTag = pouchTag.getCompound(STASH_TAG);
            String key = String.valueOf(hotbarIndex);

            ItemStack restored = ItemStack.EMPTY;
            if (stashTag.contains(key)) {
                restored = ItemStack.of(stashTag.getCompound(key));
                stashTag.remove(key);
                pouchTag.put(STASH_TAG, stashTag);
            }
            player.getInventory().setItem(hotbarIndex, restored);

            handler.save();
        });
    }

    /**
     * Вызывать ПОСЛЕ того как activeSlot стал новым значением (не -1).
     * Прячет текущий реальный предмет хотбара и кладёт туда предмет из сумки.
     */
    public static void activate(Player player, int hotbarIndex, int bagSlot) {
        if (hotbarIndex < 0) return;

        CuriosApi.getCuriosHelper().findFirstCurio(player, BalanceBags.POUCH.get()).ifPresent(slotResult -> {
            ItemStack pouchStack = slotResult.stack();
            BagInventoryHandler handler = BagInventoryHandlerCache.get(pouchStack);

            // 1. Прячем текущий реальный предмет хотбара
            ItemStack currentReal = player.getInventory().getItem(hotbarIndex);
            CompoundTag pouchTag = pouchStack.getOrCreateTag();
            CompoundTag stashTag = pouchTag.getCompound(STASH_TAG);
            stashTag.put(String.valueOf(hotbarIndex), currentReal.save(new CompoundTag()));
            pouchTag.put(STASH_TAG, stashTag);

            // 2. Кладём в реальный слот предмет из сумки
            ItemStack fromBag = handler.getStackInSlot(bagSlot).copy();
            player.getInventory().setItem(hotbarIndex, fromBag);
        });
    }

    /**
     * Вызывать КАЖДЫЙ ТИК на сервере для игроков с активным слотом сумки.
     * Синхронизирует то, что реально произошло в хотбаре (shrink, hurtAndBreak,
     * removeItem у трезубца и т.д.), обратно в NBT сумки.
     */
    public static void tickSync(Player player) {
        int activeSlot = PouchData.getActiveSlot(player);
        if (activeSlot == -1) return;

        int hotbarIndex = player.getInventory().selected;

        CuriosApi.getCuriosHelper().findFirstCurio(player, BalanceBags.POUCH.get()).ifPresent(slotResult -> {
            ItemStack pouchStack = slotResult.stack();
            BagInventoryHandler handler = BagInventoryHandlerCache.get(pouchStack);

            ItemStack currentReal = player.getInventory().getItem(hotbarIndex);
            ItemStack currentBag = handler.getStackInSlot(activeSlot);

            if (!ItemStack.matches(currentReal, currentBag)) {
                handler.setStackInSlot(activeSlot, currentReal.copy());
            }
        });
    }
}