package net.trinketina.frogpetting.mixin;

import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.entity.AnimationState;
import net.trinketina.frogpetting.IPettingAnimationState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(EntityRenderState.class)
public abstract class PettingEntityRenderStateMixin implements IPettingAnimationState {
    @Unique
    public final AnimationState pettingAnimationState = new AnimationState();

    @Override public void frog_Petting$copyToPettingAnimationState(AnimationState animationState) {
        pettingAnimationState.copyFrom(animationState);
    }

    @Override
    public AnimationState frog_Petting$getPettingAnimationState() {
        return pettingAnimationState;
    }
}
