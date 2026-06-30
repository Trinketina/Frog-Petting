package net.trinketina.frogpetting.animation;

import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import org.joml.Vector3f;
import org.joml.Vector3fc;


public record Transformation(Transformation.Target target, Keyframe... keyframes) {

    public interface Interpolation {
        Vector3f apply(Vector3f dest, float delta, Keyframe[] keyframes, int start, int end, float scale);
    }

    public static class Interpolations {
        public static final Transformation.Interpolation LINEAR = (dest, delta, keyframes, start, end, scale) -> {
            Vector3fc vector3f = keyframes[start].postTarget();
            Vector3fc vector3f2 = keyframes[end].postTarget();
            return vector3f.lerp(vector3f2, delta, dest).mul(scale);
        };
        public static final Transformation.Interpolation CUBIC = (dest, delta, keyframes, start, end, scale) -> {
            Vector3fc vector3f = keyframes[Math.max(0, start - 1)].postTarget();
            Vector3fc vector3f2 = keyframes[start].postTarget();
            Vector3fc vector3f3 = keyframes[end].postTarget();
            Vector3fc vector3f4 = keyframes[Math.min(keyframes.length - 1, end + 1)].postTarget();
            dest.set(
                    Mth.catmullrom(delta, vector3f.x(), vector3f2.x(), vector3f3.x(), vector3f4.x()) * scale,
                    Mth.catmullrom(delta, vector3f.y(), vector3f2.y(), vector3f3.y(), vector3f4.y()) * scale,
                    Mth.catmullrom(delta, vector3f.z(), vector3f2.z(), vector3f3.z(), vector3f4.z()) * scale
            );
            return dest;
        };
    }

    public interface Target {
        void apply(ModelPart modelPart, Vector3f vec);
    }

    public static class Targets {
        public static final Transformation.Target POSITION = ModelPart::offsetPos;
        public static final Transformation.Target ROTATION = ModelPart::offsetRotation;
        public static final Transformation.Target SCALE = ModelPart::offsetScale;
    }
}
