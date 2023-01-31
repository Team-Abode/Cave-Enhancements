package com.teamabode.cave_enhancements.common.advancement;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.teamabode.cave_enhancements.CaveEnhancements;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class CruncherFoundBlockTrigger extends SimpleCriterionTrigger<CruncherFoundBlockTrigger.TriggerInstance> {
    private static final ResourceLocation ID = new ResourceLocation(CaveEnhancements.MODID, "cruncher_found_block");

    public CruncherFoundBlockTrigger() {}

    public ResourceLocation getId() {
        return ID;
    }

    protected TriggerInstance createInstance(JsonObject jsonObject, EntityPredicate.Composite composite, DeserializationContext deserializationContext) {
        Block block = deserializeBlock(jsonObject);
        StatePropertiesPredicate statePropertiesPredicate = StatePropertiesPredicate.fromJson(jsonObject.get("state"));
        if (block != null) {
            statePropertiesPredicate.checkState(block.getStateDefinition(), string -> {
                throw new JsonSyntaxException("Block " + block + " has no property " + string + ":");
            });
        }
        return new TriggerInstance(composite, block, statePropertiesPredicate);
    }

    @Nullable
    private static Block deserializeBlock(JsonObject jsonObject) {
        if (jsonObject.has("block")) {
            ResourceLocation resourceLocation = new ResourceLocation(GsonHelper.getAsString(jsonObject, "block"));
            return Registry.BLOCK.getOptional(resourceLocation).orElseThrow(() -> new JsonSyntaxException("Unknown block type '" + resourceLocation + "'"));
        } else {
            return null;
        }
    }

    public void trigger(ServerPlayer serverPlayer, BlockPos blockPos) {
        BlockState blockState = serverPlayer.getLevel().getBlockState(blockPos);
        this.trigger(serverPlayer, (triggerInstance) -> triggerInstance.matches(blockState));
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        @Nullable
        private final Block block;
        private final StatePropertiesPredicate state;

        public TriggerInstance(EntityPredicate.Composite composite, @Nullable Block block, StatePropertiesPredicate state) {
            super(CruncherFoundBlockTrigger.ID, composite);
            this.block = block;
            this.state = state;
        }

        public static TriggerInstance cruncherFoundBlock(Block block) {
            return new TriggerInstance(EntityPredicate.Composite.ANY, block, StatePropertiesPredicate.ANY);
        }

        public boolean matches(BlockState blockState) {
            if (this.block != null && !blockState.is(this.block)) {
                return false;
            } else {
                return this.state.matches(blockState);
            }
        }

        public JsonObject serializeToJson(SerializationContext serializationContext) {
            JsonObject jsonObject = super.serializeToJson(serializationContext);

            if (this.block != null) {
                jsonObject.addProperty("block", Registry.BLOCK.getKey(this.block).toString());
            }
            jsonObject.add("state", this.state.serializeToJson());
            return jsonObject;
        }
    }
}
