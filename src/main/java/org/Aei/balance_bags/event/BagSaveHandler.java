package org.Aei.balance_bags.events;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.Aei.balance_bags.BalanceBags;
import org.Aei.balance_bags.inventory.BagInventoryHandlerCache;
import org.Aei.balance_bags.inventory.PouchLinkManager;
import org.Aei.balance_bags.network.PouchData;
import top.theillusivec4.curios.api.CuriosApi;

@Mod.EventBusSubscriber(modid = BalanceBags.MOD_ID)
public class BagSaveHandler {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        // Выполняем только на конце тика и на сервере
        if (event.phase != TickEvent.Phase.END) return;
        Player player = event.player;
        if (player.level().isClientSide) return;

        // 1. Синхронизация состояния по сети
        PouchLinkManager.tickSync(player);

        // 2. Периодическое / гарантированное сохранение инвентаря активного подсумка
        int activeSlot = PouchData.getActiveSlot(player);
        if (activeSlot != -1) {
            CuriosApi.getCuriosHelper().findFirstCurio(player, BalanceBags.POUCH.get())
                    .ifPresent(slotResult -> BagInventoryHandlerCache.get(slotResult.stack()).save());
        }
    }
}