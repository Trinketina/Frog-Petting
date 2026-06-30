package net.trinketina.frogpetting.animation;


import java.util.List;
import java.util.Map;

public record Animation(float lengthS, boolean looping, Map<String, List<Transformation>> boneTransformations) {
    public static class Builder {
        private float lengthS;
        private boolean looping;
        private Map<String, List<Transformation>> boneTransformations;

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

        public Animation.Builder boneTransformations(Map<String, List<Transformation>> boneTransformations) {
            this.boneTransformations = boneTransformations;
            return this;
        }

        public Animation build() {
            return new Animation(lengthS, looping, boneTransformations);
        }
    }
}
