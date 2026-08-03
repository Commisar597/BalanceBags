package org.Aei.balance_bags.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class ModKeyMappings {
    public static final String CATEGORY = "key.categories.balance_bags";

    public static final KeyMapping POUCH_SLOT_1 = new KeyMapping("key.balance_bags.pouch_1", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_Z, CATEGORY);
    public static final KeyMapping POUCH_SLOT_2 = new KeyMapping("key.balance_bags.pouch_2", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_X, CATEGORY);
    public static final KeyMapping POUCH_SLOT_3 = new KeyMapping("key.balance_bags.pouch_3", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_C, CATEGORY);
    public static final KeyMapping USE_POUCH_ITEM = new KeyMapping("key.balance_bags.use_pouch", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_V, CATEGORY);
}