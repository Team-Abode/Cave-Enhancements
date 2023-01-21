package com.teamabode.cave_enhancements.core.registry.misc;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public class ItemProperties {
    public static final Item.Properties DEFAULT = new Item.Properties();

    public static final Item.Properties ONE_STACK = new Item.Properties().stacksTo(1);

    public static Item.Properties tool(int maxDurability) {
        return ONE_STACK.durability(maxDurability).tab(CreativeModeTab.TAB_TOOLS);
    }
}
