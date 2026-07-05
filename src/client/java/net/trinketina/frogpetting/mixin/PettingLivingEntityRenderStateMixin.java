package net.trinketina.frogpetting.mixin;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.trinketina.frogpetting.interfaces.IPettingAnimationState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LivingEntityRenderState.class)
public abstract class PettingLivingEntityRenderStateMixin implements IPettingAnimationState {
    @Shadow
    public boolean isBaby;

    @Override
    public boolean frog_Petting$isBaby() {
        return isBaby;
    }
}
