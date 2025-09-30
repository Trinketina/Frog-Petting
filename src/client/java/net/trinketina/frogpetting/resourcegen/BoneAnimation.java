package net.trinketina.frogpetting.resourcegen;

import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

import java.util.HashMap;

public class BoneAnimation {
    public String bone_name;
    public HashMap<Transformation.Target, Keyframe[]> animated_elements = new HashMap<>();
    /*public Transformation.Target transformation_target;
    public Keyframe[] keyframes;*/
    //public Transformation.Interpolation interpolation;
}
