package net.trinketina.frogpetting.resourcegen.jsondata;

import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

public class PettingTransformationData {
    public Transformation.Target transformation_target;
    public Keyframe[] keyframes;

    public PettingTransformationData(Transformation.Target _transformation_target, Keyframe[] _keyframes) {
        transformation_target = _transformation_target;
        keyframes = _keyframes.clone();
    }
}
