package net.trinketina.frogpetting.resourcegen.jsondata;

import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.AnimationChannel;
import net.trinketina.frogpetting.animation.Transformation;

public class BoneTransformationData {
    public Transformation.Target transformation_target;
    public Keyframe[] keyframes;

    public BoneTransformationData(Transformation.Target _transformation_target, Keyframe[] _keyframes) {
        transformation_target = _transformation_target;
        keyframes = _keyframes.clone();
    }
}