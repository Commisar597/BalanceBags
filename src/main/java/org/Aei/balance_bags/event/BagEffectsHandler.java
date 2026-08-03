package org.Aei.balance_bags.event;

import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.Aei.balance_bags.BalanceBags;
import org.Aei.balance_bags.inventory.BagInventoryHandler;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = BalanceBags.MOD_ID)
public class BagEffectsHandler {

    // Генерируем валидный и уникальный UUID для модификатора скорости
    private static final UUID SLOWDOWN_UUID = UUID.nameUUIDFromBytes("balance_bags:slowdown".getBytes());

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        Player player = event.player;

        // Выполняем просчет только на стороне сервера
        if (player.level().isClientSide()) return;

        AttributeInstance speedAttr = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speedAttr == null) return;

        float totalSlowdown = 0.0F;

        // 1. Проверка Большой сумки (27 слотов) — если полностью заполнена (-25%)
        var duffelOpt = CuriosApi.getCuriosHelper().findFirstCurio(player, BalanceBags.DUFFEL_BAG.get());
        if (duffelOpt.isPresent()) {
            BagInventoryHandler handler = new BagInventoryHandler(duffelOpt.get().stack(), 27);
            if (handler.isFull()) {
                totalSlowdown += -0.25F;
            }
        }

        // 2. Проверка Рюкзака (18 слотов) — если полностью заполнен при беге (-15%)
        if (player.isSprinting()) {
            var backpackOpt = CuriosApi.getCuriosHelper().findFirstCurio(player, BalanceBags.BACKPACK.get());
            if (backpackOpt.isPresent()) {
                BagInventoryHandler handler = new BagInventoryHandler(backpackOpt.get().stack(), 18);
                if (handler.isFull()) {
                    totalSlowdown += -0.15F;
                }
            }
        }

        // 3. Управление модификатором скорости
        AttributeModifier currentModifier = speedAttr.getModifier(SLOWDOWN_UUID);

        if (totalSlowdown < 0.0F) {
            // Если штраф изменился (например, суммировались две сумки), пересоздаем модификатор
            if (currentModifier != null && currentModifier.getAmount() != totalSlowdown) {
                speedAttr.removeModifier(SLOWDOWN_UUID);
                currentModifier = null;
            }

            if (currentModifier == null) {
                speedAttr.addTransientModifier(new AttributeModifier(
                        SLOWDOWN_UUID,
                        "Bag Slowdown",
                        totalSlowdown,
                        AttributeModifier.Operation.MULTIPLY_BASE
                ));
            }
        } else {
            // Если штрафов нет — удаляем модификатор
            if (currentModifier != null) {
                speedAttr.removeModifier(SLOWDOWN_UUID);
            }
        }
    }

    // 4. Блокировка Левой руки при надетой Большой сумке (Duffel Bag)
    @SubscribeEvent
    public static void onRightClickOffhand(PlayerInteractEvent.RightClickItem event) {
        if (event.getHand() == InteractionHand.OFF_HAND) {
            boolean hasDuffel = CuriosApi.getCuriosHelper().findFirstCurio(event.getEntity(), BalanceBags.DUFFEL_BAG.get()).isPresent();
            if (hasDuffel) {
                event.setCanceled(true); // Запрещаем использовать предмет в левой руке
            }
        }
    }
}