package com.teamabode.cave_enhancements.core.integration.quark;

import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public enum VerticalSlabType implements StringRepresentable {
    NORTH(Direction.NORTH),
    SOUTH(Direction.SOUTH),
    WEST(Direction.WEST),
    EAST(Direction.EAST),
    DOUBLE(null);

    private final String name;
    @Nullable
    public final Direction direction;
    public final VoxelShape voxelShape;

    VerticalSlabType(@Nullable Direction direction) {
        this.name = direction == null ? "double" : direction.getSerializedName();
        this.direction = direction;
        if (direction == null) {
            voxelShape = Shapes.block();
        }
        else {
            boolean negAxis = direction.getAxisDirection() == Direction.AxisDirection.NEGATIVE;
            double min = negAxis ? 8 : 0;
            double max = negAxis ? 16 : 8;
            voxelShape = direction.getAxis() == Direction.Axis.X ? Block.box(min, 0, 0, max, 16, 16) : Block.box(0, 0, min, 16, 16, max);
        }
    }

    public static VerticalSlabType fromDirection(Direction direction) {
        for (VerticalSlabType type : VerticalSlabType.values()) {
            if (type.direction != null && direction == type.direction) {
                return type;
            }
        }
        return null;
    }

    public String toString() {
        return name;
    }

    public String getSerializedName() {
        return name;
    }
}
