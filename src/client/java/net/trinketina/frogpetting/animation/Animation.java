package net.trinketina.frogpetting.animation;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record Animation(float lengthS, boolean looping, Map<String, List<Transformation>> boneTransformations) {
    public static class Builder {
        private final float lengthS;
        private boolean looping;
        private final Map<String, List<Transformation>> boneTransformations = new HashMap<>();

        public static Animation.Builder create(float lengthS) {
            return new Animation.Builder(lengthS);
        }

        private Builder(float lengthS) {
            this.lengthS = lengthS;
        }

        public Animation.Builder looping(boolean looping) {
            this.looping = looping;
            return this;
        }

        public Animation.Builder addBoneTransformation(String name, Transformation transformation) {
            ((List<Transformation>)this.boneTransformations.computeIfAbsent(name, namex -> new ArrayList<Transformation>())).add(transformation);
            return this;
        }

        public Animation build() {
            return new Animation(lengthS, looping, boneTransformations);
        }
    }
}
