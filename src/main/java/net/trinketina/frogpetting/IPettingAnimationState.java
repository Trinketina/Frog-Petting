package net.trinketina.frogpetting;

import net.minecraft.entity.AnimationState;

public interface IPettingAnimationState {

    void frog_Petting$copyToPettingAnimationState(AnimationState animationState);

    AnimationState frog_Petting$getPettingAnimationState();
}