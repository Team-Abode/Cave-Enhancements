package com.teamabode.cave_enhancements.core.registry.misc;

import com.teamabode.cave_enhancements.common.advancement.CruncherFoundBlockTrigger;
import com.teamabode.cave_enhancements.core.platform.RegistryHelper;

public class ModCriteriaTriggers {

    public static final CruncherFoundBlockTrigger CRUNCHER_FOUND_BLOCK_TRIGGER = RegistryHelper.registerCriteriaTrigger(new CruncherFoundBlockTrigger());

    public static void init() {

    }
}
