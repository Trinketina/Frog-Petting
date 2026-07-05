package net.trinketina.frogpetting.interfaces;

import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.entity.AnimationState;
import net.minecraft.world.entity.AnimationState;

public interface IPettingAnimationState {

    default boolean frog_Petting$isBaby() {
        return false;
    }

    void frog_Petting$copyToPettingAnimationState(AnimationState animationState);

    AnimationState frog_Petting$getPettingAnimationState();

    EntityRenderState frog_Petting$getEntityRenderState();
}