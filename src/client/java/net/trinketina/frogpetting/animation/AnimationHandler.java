package net.trinketina.frogpetting.animation;

import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AnimationState;
import net.trinketina.frogpetting.PettingClient;
import net.trinketina.frogpetting.interfaces.IPettingModel;
import org.joml.Vector3f;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class AnimationHandler {
    private static final Vector3f ANIMATION_VECTOR_CACHE = new Vector3f();


    public static void animate(Model model, Animation animation, AnimationState animationState, long current_time) {
        float running_time = getRunningSeconds(animation, current_time);
        //Vector3f scratch_vector = new Vector3f();

        animate(model, animation, running_time, 1.0f, ANIMATION_VECTOR_CACHE);

        if (running_time > animation.lengthS()) {
            animationState.stop();
        }
    }

    public static void animate(Model model, Animation animation, float running_time, float scale, Vector3f position) {
        //float running_seconds = getRunningSeconds(animation, running_time);

        for (Map.Entry<String, List<Transformation>> entry : animation.boneTransformations().entrySet()) {
            String key = entry.getKey();
            List<Transformation> transformations = entry.getValue();

            AtomicReference<ModelPart> part = new AtomicReference<>();
            try {
                if (model instanceof IPettingModel<?> pettingModel) {
                    Optional<ModelPart> optional = pettingModel.frog_petting$getAnyDescendantWithName(key);
                    optional.ifPresent(part::set);
                }
            }
            catch (Exception e) {
                //PettingClient.LOGGER.info(e.getMessage());
            }

            if (part.get() != null) {

                for ( var transformation : transformations ) {
                    Keyframe[] keyframes = transformation.keyframes();
                    int i = Math.max(0, Mth.binarySearch(0, keyframes.length, index -> running_time <= keyframes[index].timestamp()) - 1);

                    int j = Math.min(keyframes.length - 1, i + 1);
                    Keyframe keyframe = keyframes[i];
                    Keyframe keyframe2 = keyframes[j];
                    float h = running_time - keyframe.timestamp();
                    float k;
                    if (j != i) {
                        k = Mth.clamp(h / (keyframe2.timestamp() - keyframe.timestamp()), 0.0F, 1.0F);
                    } else {
                        k = 0.0F;
                    }

                    keyframe2.interpolation().apply(position, k, keyframes, i, j, scale);
                    transformation.target().apply(part.get(), position);
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