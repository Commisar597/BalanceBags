package org.Aei.balance_bags.init;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.Aei.balance_bags.BalanceBags;
import org.Aei.balance_bags.item.BagItem;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, BalanceBags.MOD_ID);

    public static final RegistryObject<Item> POUCH = ITEMS.register("pouch",
            () -> new BagItem(new Item.Properties()));

    public static final RegistryObject<Item> REGULAR_BAG = ITEMS.register("regular_bag",
            () -> new BagItem(new Item.Properties()));

    public static final RegistryObject<Item> DUFFEL_BAG = ITEMS.register("duffel_bag",
            () -> new BagItem(new Item.Properties()));

    public static final RegistryObject<Item> BACKPACK = ITEMS.register("backpack",
            () -> new BagItem(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}