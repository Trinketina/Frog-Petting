package net.trinketina.frogpetting.mixin;

import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.entity.Entity;
import net.trinketina.frogpetting.IPettingAnimationState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public abstract class PettingRendererMixin<T extends Entity, S extends EntityRenderState> {
    @Inject(method = "updateRenderState", at = @At("HEAD"))
    private void onUpdateRenderState(T livingEntity, S livingEntityRenderState, float f, CallbackInfo ci) {
        if (livingEntityRenderState instanceof IPettingAnimationState && livingEntity instanceof IPettingAnimationState) {
            IPettingAnimationState pettingRenderState = (IPettingAnimationState) livingEntityRenderState;
            IPettingAnimationState pettingEntity = (IPettingAnimationState) livingEntity;

            pettingRenderState.copyToPettingAnimationState(pettingEntity.getPettingAnimationState());
        }
    }
}
