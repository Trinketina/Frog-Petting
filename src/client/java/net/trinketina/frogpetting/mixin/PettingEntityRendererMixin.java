package net.trinketina.frogpetting.mixin;

import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.trinketina.frogpetting.IPettingAnimationState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public abstract class PettingEntityRendererMixin<T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<? super S>> {
    @Inject(method = "updateRenderState*", at = @At("RETURN"))
    private void onUpdateRenderState(T livingEntity, S livingEntityRenderState, float f, CallbackInfo ci) {
        if (livingEntityRenderState instanceof IPettingAnimationState && livingEntity instanceof IPettingAnimationState) {
            IPettingAnimationState pettingRenderState = (IPettingAnimationState) livingEntityRenderState;
            IPettingAnimationState pettingEntity = (IPettingAnimationState) livingEntity;

            pettingRenderState.frog_Petting$copyToPettingAnimationState(pettingEntity.frog_Petting$getPettingAnimationState());
        }
    }
}
