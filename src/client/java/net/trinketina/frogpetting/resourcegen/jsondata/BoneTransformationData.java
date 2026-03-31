package net.trinketina.frogpetting.resourcegen.jsondata;

import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.AnimationChannel;

public class BoneTransformationData {
    public AnimationChannel.Target transformation_target;
    public Keyframe[] keyframes;

    public BoneTransformationData(AnimationChannel.Target _transformation_target, Keyframe[] _keyframes) {
        transformation_target = _transformation_target;
        keyframes = _keyframes.clone();
    }
}
