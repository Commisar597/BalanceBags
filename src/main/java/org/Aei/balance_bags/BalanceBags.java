package org.Aei.balance_bags;

import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.Aei.balance_bags.item.BagItem;
import org.slf4j.Logger;
import top.theillusivec4.curios.api.SlotTypePreset;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.Aei.balance_bags.crafting.BeltCuttingRecipe;

@Mod(BalanceBags.MOD_ID)
public class BalanceBags {

    public static final String MOD_ID = "balance_bags";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    public static final RegistryObject<Item> BACKPACK = ITEMS.register("backpack",
            () -> new BagItem(new Item.Properties()));

    public static final RegistryObject<Item> DUFFEL_BAG = ITEMS.register("duffel_bag",
            () -> new BagItem(new Item.Properties()));

    public static final RegistryObject<Item> REGULAR_BAG = ITEMS.register("regular_bag",
            () -> new BagItem(new Item.Properties()));

    public static final RegistryObject<Item> POUCH = ITEMS.register("pouch",
            () -> new BagItem(new Item.Properties()));

    public static final RegistryObject<Item> DRAWSTRING = ITEMS.register("drawstring",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> POCKET = ITEMS.register("pocket",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SACK = ITEMS.register("sack",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SMALL_STRAP = ITEMS.register("small_strap",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> STRAP = ITEMS.register("strap",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BIG_STRAP = ITEMS.register("big_strap",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BELT = ITEMS.register("belt",
            () -> new Item(new Item.Properties()));

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, "balance_bags");

    public static final RegistryObject<RecipeSerializer<BeltCuttingRecipe>> BELT_CUTTING_SERIALIZER =
            RECIPE_SERIALIZERS.register("belt_cutting", () -> new SimpleCraftingRecipeSerializer<>(BeltCuttingRecipe::new));

    public static final RegistryObject<CreativeModeTab> BALANCE_BAGS_TAB = CREATIVE_MODE_TABS.register("balance_bags_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(BACKPACK.get()))
                    .title(Component.translatable("creativetab.balance_bags_tab"))
                    .displayItems((parameters, output) -> {
                        output.accept(BACKPACK.get());
                        output.accept(DUFFEL_BAG.get());
                        output.accept(REGULAR_BAG.get());
                        output.accept(POUCH.get());
                        output.accept(DRAWSTRING.get());
                        output.accept(POCKET.get());
                        output.accept(SACK.get());
                        output.accept(SMALL_STRAP.get());
                        output.accept(STRAP.get());
                        output.accept(BIG_STRAP.get());
                        output.accept(BELT.get());
                    })
                    .build());

    public BalanceBags() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
        RECIPE_SERIALIZERS.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::enqueueIMC);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Balance Bags common setup initialized.");
    }

    // --- Регистрация слотов Curios через IMC ---
    private void enqueueIMC(final InterModEnqueueEvent event) {
        // Слот Спины (Back)
        InterModComms.sendTo("curios", "register_type",
                () -> SlotTypePreset.BACK.getMessageBuilder().build());

        // Слот Пояса (Belt)
        InterModComms.sendTo("curios", "register_type",
                () -> SlotTypePreset.BELT.getMessageBuilder().build());

        // 2 слота Аксессуара (Curio) под подсумок
        InterModComms.sendTo("curios", "register_type",
                () -> SlotTypePreset.CURIO.getMessageBuilder().size(2).build());
    }
}