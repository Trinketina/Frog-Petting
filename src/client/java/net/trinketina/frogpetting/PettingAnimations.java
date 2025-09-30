package net.trinketina.frogpetting;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

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

    }
}
