package net.trinketina.frogpetting;

import net.minecraft.world.entity.AnimationState;

public interface IPettingAnimationState {

    void frog_Petting$copyToPettingAnimationState(net.minecraft.world.entity.AnimationState animationState);

    AnimationState frog_Petting$getPettingAnimationState();
}