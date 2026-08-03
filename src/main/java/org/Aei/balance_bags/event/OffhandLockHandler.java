package org.Aei.balance_bags.event;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.Aei.balance_bags.BalanceBags;
import top.theillusivec4.curios.api.CuriosApi;

@Mod.EventBusSubscriber(modid = BalanceBags.MOD_ID)
public class OffhandLockHandler {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && !event.player.level().isClientSide) {
            Player player = event.player;

            boolean hasDuffelBag = CuriosApi.getCuriosHelper()
                    .findFirstCurio(player, BalanceBags.DUFFEL_BAG.get())
                    .isPresent();

            if (hasDuffelBag) {
                ItemStack offhandStack = player.getOffhandItem();
                if (!offhandStack.isEmpty()) {
                    // Переносим предмет из левой руки в основной инвентарь или выбрасываем
                    if (!player.getInventory().add(offhandStack)) {
                        player.drop(offhandStack, false);
                    }
                    player.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
                }
            }
        }
    }
}