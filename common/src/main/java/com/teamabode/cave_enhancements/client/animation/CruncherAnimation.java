package com.teamabode.cave_enhancements.client.animation;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

public class CruncherAnimation {

    public static final AnimationDefinition WALKING;
    public static final AnimationDefinition CHOMPING;


    static {
        WALKING = AnimationDefinition.Builder.withLength(0.8F)
                // Body
                .addAnimation(
                        "body",
                        new AnimationChannel(
                                AnimationChannel.Targets.ROTATION,
                                new Keyframe(0.0F, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.2F, KeyframeAnimations.degreeVec(0, 0, -5), AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.4F, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.6F, KeyframeAnimations.degreeVec(0, 0, 5), AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.8F, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.CATMULLROM)
                        )
                ).addAnimation(
                        "body",
                        new AnimationChannel(
                                AnimationChannel.Targets.POSITION,
                                new Keyframe(0.0F, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.4F, KeyframeAnimations.posVec(0, -0.25F, 0), AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.8F, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.CATMULLROM)
                        )
                )
                // Left Front Leg
                .addAnimation(
                        "left_front_leg",
                        new AnimationChannel(
                                AnimationChannel.Targets.ROTATION,
                                new Keyframe(0.0F, KeyframeAnimations.degreeVec(-22.5F, 0, 0), AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.2F, KeyframeAnimations.degreeVec(22.5F, 0, -5F), AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.8F, KeyframeAnimations.degreeVec(-22.5F, 0, 0), AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation(
                        "left_front_leg",
                        new AnimationChannel(
                                AnimationChannel.Targets.POSITION,
                                new Keyframe(0.0F, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.2F, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.44F, KeyframeAnimations.posVec(0, 1, 0), AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.8F, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.CATMULLROM)))
                // Left Hind Leg
                .addAnimation(
                        "left_hind_leg",
                        new AnimationChannel(
                                AnimationChannel.Targets.ROTATION,
                                new Keyframe(0.0F, KeyframeAnimations.degreeVec(22.5F, 0, 0), AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.2F, KeyframeAnimations.degreeVec(-22.5F, 0, -5F), AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.8F, KeyframeAnimations.degreeVec(22.5F, 0, 0), AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation(
                        "left_hind_leg",
                        new AnimationChannel(
                                AnimationChannel.Targets.POSITION,
                                new Keyframe(0.0F, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.2F, KeyframeAnimations.posVec(0, 1, 0), AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.44F, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.8F, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.CATMULLROM)))
                // Right Front Leg
                .addAnimation(
                        "right_front_leg",
                        new AnimationChannel(
                                AnimationChannel.Targets.ROTATION,
                                new Keyframe(0.0F, KeyframeAnimations.degreeVec(22.5F, 0, 0), AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.2F, KeyframeAnimations.degreeVec(-22.5F, 0, 5F), AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.8F, KeyframeAnimations.degreeVec(22.5F, 0, 0), AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation(
                        "right_front_leg",
                        new AnimationChannel(
                                AnimationChannel.Targets.POSITION,
                                new Keyframe(0.0F, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.2F, KeyframeAnimations.posVec(0, 1, 0), AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.44F, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.8F, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.CATMULLROM)))
                // Right Hind Leg
                .addAnimation(
                        "right_hind_leg",
                        new AnimationChannel(
                                AnimationChannel.Targets.ROTATION,
                                new Keyframe(0.0F, KeyframeAnimations.degreeVec(-22.5F, 0, 0), AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.2F, KeyframeAnimations.degreeVec(22.5F, 0, 5F), AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.8F, KeyframeAnimations.degreeVec(-22.5F, 0, 0), AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation(
                        "right_hind_leg",
                        new AnimationChannel(
                                AnimationChannel.Targets.POSITION,
                                new Keyframe(0.0F, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.2F, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.44F, KeyframeAnimations.posVec(0, 1, 0), AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.8F, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.CATMULLROM)))
                .looping().build();
        CHOMPING = AnimationDefinition.Builder.withLength(0.32F)
                .addAnimation("head",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.16F, KeyframeAnimations.posVec(0.0F, 0.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.32F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                        )
                )
                .addAnimation("upper_jaw",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.16F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.32F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                        )
                )
                .addAnimation("upper_jaw",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.16F, KeyframeAnimations.degreeVec(0.0F, 0.25F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.32F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                        )
                )
                .addAnimation("lower_jaw",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.16F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.32F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                        )
                )
                .addAnimation("lower_jaw",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.16F, KeyframeAnimations.degreeVec(0.0F, 0.25F, -0.5F), AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.32F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                        )
                )
                .build();
    }
}
