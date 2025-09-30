package net.trinketina.frogpetting.mixin;

import net.minecraft.entity.AnimationState;

public interface IPettingAnimationState {
    final AnimationState pettingAnimationState = new AnimationState();

    default void copyToPettingAnimationState(AnimationState animationState) {
        pettingAnimationState.copyFrom(animationState);
    }
    default AnimationState getPettingAnimationState() { return pettingAnimationState; }
}
