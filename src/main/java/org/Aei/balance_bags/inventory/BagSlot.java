package org.Aei.balance_bags.inventory;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.Aei.balance_bags.item.BagItem;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.function.Supplier;

public class BagSlot extends SlotItemHandler {
    private final Player player;
    private final Supplier<Item> bagItemSupplier;

    public BagSlot(Player player, IItemHandler itemHandler, int index, int xPosition, int yPosition, Supplier<Item> bagItemSupplier) {
        super(itemHandler, index, xPosition, yPosition);
        this.player = player;
        this.bagItemSupplier = bagItemSupplier;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        if (stack.getItem() instanceof BagItem) {
            return false;
        }
        return super.mayPlace(stack);
    }

    @Override
    public boolean isActive() {
        // Слот активен (виден и доступен) только если у игрока надет соответствующий Curio
        if (player == null || bagItemSupplier.get() == null) {
            return false;
        }
        return CuriosApi.getCuriosHelper().findFirstCurio(player, bagItemSupplier.get()).isPresent();
    }
}