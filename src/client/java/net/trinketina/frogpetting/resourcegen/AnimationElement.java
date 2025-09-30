package net.trinketina.frogpetting.resourcegen;

import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;
import net.trinketina.frogpetting.PettingClient;

public class AnimationElement {
    public Transformation.Target transformation_target;
    public Keyframe[] keyframes;

    public AnimationElement(Transformation.Target _transformation_target, Keyframe[] _keyframes) {
        transformation_target = _transformation_target;
        keyframes = _keyframes.clone();

        //PettingClient.LOGGER.info("animation element: " + transformation_target + " " + keyframes[1]);
    }
}
