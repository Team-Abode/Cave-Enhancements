package com.teamabode.cave_enhancements.common.entity.cruncher.goals;

import com.teamabode.cave_enhancements.common.entity.cruncher.Cruncher;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;

import java.util.EnumSet;
import java.util.List;

public class CruncherMoveToItemGoal extends Goal {

    private final Cruncher cruncher;

    public CruncherMoveToItemGoal(Cruncher cruncher) {
        this.cruncher = cruncher;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    public boolean canUse() {
        if (!cruncher.getMainHandItem().isEmpty()) return false;
        if (cruncher.getEatingState() != 0) return false;
        if (cruncher.getSearchCooldownTime() > 0) return false;
        return cruncher.getRandom().nextFloat() > 0.2F;
    }

    public void tick() {
        List<ItemEntity> itemEntities = cruncher.level.getEntitiesOfClass(ItemEntity.class, cruncher.getBoundingBox().inflate(8.0F), Cruncher.GLOW_BERRIES_ONLY);

        if (cruncher.getMainHandItem().isEmpty() && !itemEntities.isEmpty()) {
            ItemEntity target = itemEntities.get(0);
            cruncher.getLookControl().setLookAt(target);
            cruncher.getNavigation().moveTo(target.getX(), target.getY(), target.getZ(), 1.25F);
        }
    }

    public void start() {
        List<ItemEntity> itemEntities = cruncher.level.getEntitiesOfClass(ItemEntity.class, cruncher.getBoundingBox().inflate(8.0F), Cruncher.GLOW_BERRIES_ONLY);

        if (!itemEntities.isEmpty()) {
            ItemEntity target = itemEntities.get(0);
            cruncher.getNavigation().moveTo(target.getX(), target.getY(), target.getZ(), 0.5F);
        }
    }
}
