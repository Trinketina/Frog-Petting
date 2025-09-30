package net.trinketina.frogpetting;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;
import net.minecraft.entity.AnimationState;

import java.util.HashMap;

@Environment(EnvType.CLIENT)
public class PettingAnimations {
    public static final HashMap<String, Animation> PETTING_ANIMATIONS = new HashMap<>();
    public static final Animation SNIFFING;

    static {
        SNIFFING = Animation.Builder.create(1.0F)
                .addBoneAnimation("nose",
                        new Transformation(Transformation.Targets.SCALE,
                                new Keyframe[]{
                                        new Keyframe(0.0F, AnimationHelper.createScalingVector((double)1.0F, (double)1.0F, (double)1.0F), Transformation.Interpolations.CUBIC),
                                        new Keyframe(0.0833F, AnimationHelper.createScalingVector((double)1.0F, (double)0.7F, (double)1.0F), Transformation.Interpolations.CUBIC),
                                        new Keyframe(0.125F, AnimationHelper.createScalingVector((double)1.0F, (double)3.0F, (double)1.0F), Transformation.Interpolations.CUBIC),
                                        new Keyframe(0.25F, AnimationHelper.createScalingVector((double)1.0F, (double)3.0F, (double)1.0F), Transformation.Interpolations.CUBIC),
                                        new Keyframe(0.7083F, AnimationHelper.createScalingVector((double)1.0F, (double)4.0F, (double)1.0F), Transformation.Interpolations.CUBIC),
                                        new Keyframe(0.8333F, AnimationHelper.createScalingVector((double)1.0F, (double)1.0F, (double)1.0F), Transformation.Interpolations.CUBIC),
                                        new Keyframe(1.0F, AnimationHelper.createScalingVector((double)1.0F, (double)1.0F, (double)1.0F), Transformation.Interpolations.CUBIC)}))
                .addBoneAnimation("head",
                        new Transformation(Transformation.Targets.ROTATE,
                                new Keyframe[]{
                                        new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                                        new Keyframe(0.125F, AnimationHelper.createRotationalVector(-5.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                                        new Keyframe(0.875F, AnimationHelper.createRotationalVector(-20.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                                        new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)})).build();

        /*final Animation CROAKING = Animation.Builder.create(3.0f)
                .addBoneAnimation("croaking_body",
                        new Transformation(Transformation.Targets.MOVE_ORIGIN,
                                new Keyframe(0.0f, AnimationHelper.createTranslationalVector(0.0f, 0.0f, 0.0f), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.375f, AnimationHelper.createTranslationalVector(0.0f, 0.0f, 0.0f), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.4167f, AnimationHelper.createTranslationalVector(0.0f, 0.0f, 0.0f), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.4583f, AnimationHelper.createTranslationalVector(0.0f, 1.0f, 0.0f), Transformation.Interpolations.LINEAR),
                                new Keyframe(2.9583f, AnimationHelper.createTranslationalVector(0.0f, 1.0f, 0.0f), Transformation.Interpolations.LINEAR),
                                new Keyframe(3.0f, AnimationHelper.createTranslationalVector(0.0f, 0.0f, 0.0f), Transformation.Interpolations.LINEAR)))
                .addBoneAnimation("croaking_body",
                        new Transformation(Transformation.Targets.SCALE,
                                new Keyframe(0.0f, AnimationHelper.createScalingVector(0.0, 0.0, 0.0), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.375f, AnimationHelper.createScalingVector(0.0, 0.0, 0.0), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.4167f, AnimationHelper.createScalingVector(1.0, 1.0, 1.0), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.4583f, AnimationHelper.createScalingVector(1.0, 1.0, 1.0), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.5417f, AnimationHelper.createScalingVector(1.3f, 2.1f, 1.6f), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.625f, AnimationHelper.createScalingVector(1.3f, 2.1f, 1.6f), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.7083f, AnimationHelper.createScalingVector(1.0, 1.0, 1.0), Transformation.Interpolations.LINEAR),
                                new Keyframe(2.25f, AnimationHelper.createScalingVector(1.0, 1.0, 1.0), Transformation.Interpolations.LINEAR),
                                new Keyframe(2.3333f, AnimationHelper.createScalingVector(1.3f, 2.1f, 1.6f), Transformation.Interpolations.LINEAR),
                                new Keyframe(2.4167f, AnimationHelper.createScalingVector(1.3f, 2.1f, 1.6f), Transformation.Interpolations.LINEAR),
                                new Keyframe(2.5f, AnimationHelper.createScalingVector(1.0, 1.0, 1.0), Transformation.Interpolations.LINEAR),
                                new Keyframe(2.5833f, AnimationHelper.createScalingVector(1.0, 1.0, 1.0), Transformation.Interpolations.LINEAR),
                                new Keyframe(2.6667f, AnimationHelper.createScalingVector(1.3f, 2.1f, 1.6f), Transformation.Interpolations.LINEAR),
                                new Keyframe(2.875f, AnimationHelper.createScalingVector(1.3f, 2.1f, 1.6f), Transformation.Interpolations.LINEAR),
                                new Keyframe(2.9583f, AnimationHelper.createScalingVector(1.0, 1.0, 1.0), Transformation.Interpolations.LINEAR),
                                new Keyframe(3.0f, AnimationHelper.createScalingVector(0.0, 0.0, 0.0), Transformation.Interpolations.LINEAR))).build();

        final AnimationState TEST_WITH_SCALE = Animation.Builder.create(0.5)
                .addBoneAnimation("head",
                        new Transformation(Transformation.Targets.MOVE_ORIGIN,
                                new Keyframe(0.0, ( 0.000E+0 -0.000E+0  0.000E+0)), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.25, ( 0.000E+0  5.000E-1  0.000E+0)), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.5, ( 0.000E+0 -0.000E+0  0.000E+0)), Transformation.Interpolations.LINEAR)))
                .addBoneAnimation("eyes",
                        new Transformation(net.minecraft.client.render.entity.animation.Transformation$Targets$$Lambda/0x00000008010b7278@152cabb5,
                                new Keyframe(0.0, ( 0.000E+0  0.000E+0  0.000E+0)), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.25, (-1.000E-2 -1.000E-2 -1.000E-2)), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.5, ( 0.000E+0  0.000E+0  0.000E+0)), Transformation.Interpolations.LINEAR)
                .addBoneAnimation("eyes",
                        new Transformation(net.minecraft.client.render.entity.animation.Transformation$Targets$$Lambda/0x00000008010b7278@152cabb5,
                                new Keyframe(0.0, ( 0.000E+0  0.000E+0  0.000E+0)), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.25, (-1.000E-2 -1.000E-2 -1.000E-2)), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.5, ( 0.000E+0  0.000E+0  0.000E+0)), Transformation.Interpolations.LINEAR)
                .addBoneAnimation("tongue",
                        new Transformation(net.minecraft.client.render.entity.animation.Transformation$Targets$$Lambda/0x00000008010b6e58@19602d72,
                                new Keyframe(0.0, ( 0.000E+0 -0.000E+0  0.000E+0)), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.25, ( 0.000E+0  5.000E-1  0.000E+0)), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.5, ( 0.000E+0 -0.000E+0  0.000E+0)), Transformation.Interpolations.LINEAR)
                .addBoneAnimation("body",
                        new Transformation(net.minecraft.client.render.entity.animation.Transformation$Targets$$Lambda/0x00000008010b6e58@19602d72,
                                new Keyframe(0.0, ( 0.000E+0 -0.000E+0  0.000E+0)), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.25, ( 0.000E+0  5.000E-1  0.000E+0)), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.5, ( 0.000E+0 -0.000E+0  0.000E+0)), Transformation.Interpolations.LINEAR)
                .addBoneAnimation("croaking_body",
                        new Transformation(net.minecraft.client.render.entity.animation.Transformation$Targets$$Lambda/0x00000008010b7278@152cabb5,
                                new Keyframe(0.0, ( 0.000E+0  0.000E+0  0.000E+0)), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.25, ( 4.000E-1  1.000E+0  4.000E-1)), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.5, ( 0.000E+0  0.000E+0  0.000E+0)), Transformation.Interpolations.LINEAR))).build();
        final AnimationState TEST_WITHOUT = Animation.Builder.create(0.5)
                .addBoneAnimation("head",
                        new Transformation(net.minecraft.client.render.entity.animation.Transformation$Targets$$Lambda/0x00000008010adb78@ffc1a60,
                                new Keyframe(0.0, ( 0.000E+0 -0.000E+0  0.000E+0)), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.25, ( 0.000E+0  5.000E-1  0.000E+0)), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.5, ( 0.000E+0 -0.000E+0  0.000E+0)), Transformation.Interpolations.LINEAR)
                .addBoneAnimation("eyes",
                        new Transformation(net.minecraft.client.render.entity.animation.Transformation$Targets$$Lambda/0x00000008010adb78@ffc1a60,
                                new Keyframe(0.0, ( 0.000E+0 -0.000E+0  0.000E+0)), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.25, ( 0.000E+0  1.500E+0  0.000E+0)), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.5, ( 0.000E+0 -0.000E+0  0.000E+0)), Transformation.Interpolations.LINEAR)
                .addBoneAnimation("tongue",
                        new Transformation(net.minecraft.client.render.entity.animation.Transformation$Targets$$Lambda/0x00000008010adb78@ffc1a60,
                                new Keyframe(0.0, ( 0.000E+0 -0.000E+0  0.000E+0)), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.25, ( 0.000E+0  5.000E-1  0.000E+0)), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.5, ( 0.000E+0 -0.000E+0  0.000E+0)), Transformation.Interpolations.LINEAR)
                .addBoneAnimation("body", new Transformation(net.minecraft.client.render.entity.animation.Transformation$Targets$$Lambda/0x00000008010adb78@ffc1a60,
                                new Keyframe(0.0, ( 0.000E+0 -0.000E+0  0.000E+0)), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.25, ( 0.000E+0  5.000E-1  0.000E+0)), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.5, ( 0.000E+0 -0.000E+0  0.000E+0)), Transformation.Interpolations.LINEAR)
                .addBoneAnimation("croaking_body", new Transformation(net.minecraft.client.render.entity.animation.Transformation$Targets$$Lambda/0x00000008010adf98@7b5f2995,
                                new Keyframe(0.0, ( 0.000E+0  0.000E+0  0.000E+0)), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.25, ( 4.000E-1  1.000E+0  4.000E-1)), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.5, ( 0.000E+0  0.000E+0  0.000E+0)), Transformation.Interpolations.LINEAR))).build();*/
    }
}
