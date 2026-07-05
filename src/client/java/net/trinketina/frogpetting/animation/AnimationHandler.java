package net.trinketina.frogpetting.animation;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.entity.AnimationState;
import net.minecraft.util.math.MathHelper;
import org.joml.Vector3f;

import java.util.List;
import java.util.Map;

public class AnimationHandler {
    public static void animate(Model model, Animation animation, AnimationState animationState, long current_time) {
        float running_time = getRunningSeconds(animation, current_time);
        Vector3f scratch_vector = new Vector3f();

        animate(model, animation, running_time, 1.0f, scratch_vector);
    }

    public static void animate(Model model, Animation animation, float running_time, float scale, Vector3f position) {
        //float running_seconds = getRunningSeconds(animation, running_time);

        for (Map.Entry<String, List<Transformation>> entry : animation.boneTransformations().entrySet()) {
            String key = entry.getKey();
            List<Transformation> transformations = entry.getValue();

            ModelPart part = null;
            try {
                if (model instanceof SinglePartEntityModel<?> animatable) {
                    part = animatable.getPart().getChild(key);
                }
            }
            catch (Exception e) {}

            if (part != null) {
                for ( var transformation : transformations ) {
                    Keyframe[] keyframes = transformation.keyframes();
                    int i = Math.max(0, MathHelper.binarySearch(0, keyframes.length, index -> running_time <= keyframes[index].timestamp()) - 1);

                    int j = Math.min(keyframes.length - 1, i + 1);
                    Keyframe keyframe = keyframes[i];
                    Keyframe keyframe2 = keyframes[j];
                    float h = running_time - keyframe.timestamp();
                    float k;
                    if (j != i) {
                        k = MathHelper.clamp(h / (keyframe2.timestamp() - keyframe.timestamp()), 0.0F, 1.0F);
                    } else {
                        k = 0.0F;
                    }

                    keyframe2.interpolation().apply(position, k, keyframes, i, j, scale);
                    transformation.target().apply(part, position);
                }
            }
        }
    }

    private static float getRunningSeconds(Animation animation, long current_time) {
        float f = (float) current_time / 1000.0F;
        return animation.looping() ? f % animation.lengthS() : f;
    }

    public static Vector3f createTranslationalVector(float x, float y, float z) {
        return new Vector3f(x, -y, z);
    }

    public static Vector3f createRotationalVector(float x, float y, float z) {
        return new Vector3f(x * (float) (Math.PI / 180.0), y * (float) (Math.PI / 180.0), z * (float) (Math.PI / 180.0));
    }

    public static Vector3f createScalingVector(double x, double y, double z) {
        return new Vector3f((float)(x - 1.0), (float)(y - 1.0), (float)(z - 1.0));
    }
}