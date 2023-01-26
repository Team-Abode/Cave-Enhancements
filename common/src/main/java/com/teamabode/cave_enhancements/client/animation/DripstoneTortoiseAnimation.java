package com.teamabode.cave_enhancements.client.animation;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationChannel.Interpolations;
import net.minecraft.client.animation.AnimationChannel.Targets;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.AnimationDefinition.Builder;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

@Environment(EnvType.CLIENT)
public class DripstoneTortoiseAnimation {

    public static final AnimationDefinition STOMPING;

    static {
        STOMPING = Builder.withLength(0.52F)
                .addAnimation("head",
                        new AnimationChannel(Targets.ROTATION,
                                new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                                new Keyframe(0.16F, KeyframeAnimations.degreeVec(-45.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                                new Keyframe(0.28F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
                        )
                )
                .addAnimation("left_front_leg",
                        new AnimationChannel(Targets.ROTATION,
                                new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                                new Keyframe(0.16F, KeyframeAnimations.degreeVec(-50.0F, -30.0F, 30.0F), Interpolations.CATMULLROM),
                                new Keyframe(0.28F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
                        )
                )
                .addAnimation("right_front_leg",
                        new AnimationChannel(Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                            new Keyframe(0.16F, KeyframeAnimations.degreeVec(-50.0F, 30.0F, -30.0F), Interpolations.CATMULLROM),
                            new Keyframe(0.28F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
                        )
                )
                .addAnimation("left_hind_leg",
                        new AnimationChannel(Targets.ROTATION,
                                new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                                new Keyframe(0.16F, KeyframeAnimations.degreeVec(50.0F, -30.0F, -30.0F), Interpolations.CATMULLROM),
                                new Keyframe(0.28F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
                        )
                )
                .addAnimation("right_hind_leg",
                        new AnimationChannel(Targets.ROTATION,
                                new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                                new Keyframe(0.16F, KeyframeAnimations.degreeVec(50.0F, 30.0F, 30.0F), Interpolations.CATMULLROM),
                                new Keyframe(0.28F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
                        )
                )
                .build();
    }
}
