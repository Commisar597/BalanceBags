package org.Aei.balance_bags.item;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import org.Aei.balance_bags.BalanceBags;
import org.Aei.balance_bags.inventory.BagInventoryHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio.SoundInfo;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class BagItem extends Item implements ICurioItem {

    public BagItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    public int getBagSize(ItemStack stack) {
        if (stack.is(BalanceBags.BACKPACK.get())) return 18;
        if (stack.is(BalanceBags.REGULAR_BAG.get())) return 12;
        if (stack.is(BalanceBags.DUFFEL_BAG.get())) return 27;
        if (stack.is(BalanceBags.POUCH.get())) return 3;
        return 0;
    }

    @Override
    public SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new SoundInfo(SoundEvents.ARMOR_EQUIP_LEATHER, 1.0F, 1.0F);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return canEquip(slotContext, stack);
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        var foundCurio = CuriosApi.getCuriosHelper().findFirstCurio(slotContext.entity(), stack.getItem());

        if (foundCurio.isPresent()) {
            return foundCurio.get().stack() == stack;
        }

        return true;
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new ICapabilityProvider() {
            private final LazyOptional<IItemHandler> handler =
                    LazyOptional.of(() -> new BagInventoryHandler(stack, getBagSize(stack)));

            @Override
            public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
                if (cap == ForgeCapabilities.ITEM_HANDLER) {
                    return handler.cast();
                }
                return LazyOptional.empty();
            }
        };
    }
}