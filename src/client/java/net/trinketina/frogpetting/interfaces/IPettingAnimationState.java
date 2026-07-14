package net.trinketina.frogpetting.interfaces;

import net.minecraft.world.entity.AnimationState;

public interface IPettingAnimationState {

    default boolean frog_Petting$isBaby() {
        return false;
    }

    AnimationState frog_Petting$getPettingAnimationState();
}