package net.trinketina.frogpetting.mixin;

import net.minecraft.client.model.Model;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelPart;
import net.trinketina.frogpetting.IPettingAnimationState;
import net.trinketina.frogpetting.IPettingModel;
import net.trinketina.frogpetting.PettingData;
import net.trinketina.frogpetting.animation.AnimationHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Model.class)
public abstract class PettingModelMixin<S> implements IPettingModel<S> {
    @Unique
    private S pettingState;
    @Override
    public boolean frog_Petting$tryGenerateKeyframeAnimation(String entity_id) {
        return false;
    }

    @Override
    public void frog_Petting$setPettingAnim() {
        if (this.pettingState instanceof IPettingAnimationState pettingAnimationState) {
            if (pettingState == null || pettingAnimationState == null ||  pettingAnimationState.frog_Petting$getEntityRenderState() == null) {
                return;
            }
            String entity_id = pettingAnimationState.frog_Petting$getEntityRenderState().entityType.toString();
            if (pettingAnimationState.frog_Petting$getPettingAnimationState().isStarted()) {
                if (pettingAnimationState.frog_Petting$isBaby() && PettingData.BABY_PETTING_ANIMATIONS.containsKey(entity_id)) {
                    AnimationHandler.animate((Model)(Object)this, PettingData.BABY_PETTING_ANIMATIONS.get(entity_id), pettingAnimationState.frog_Petting$getPettingAnimationState(),  pettingAnimationState.frog_Petting$getPettingAnimationState().getTimeInMillis(pettingAnimationState.frog_Petting$getEntityRenderState().ageInTicks));
                }
                else if (PettingData.PETTING_ANIMATIONS.containsKey(entity_id)) {
                    AnimationHandler.animate((Model)(Object)this, PettingData.PETTING_ANIMATIONS.get(entity_id), pettingAnimationState.frog_Petting$getPettingAnimationState(),  pettingAnimationState.frog_Petting$getPettingAnimationState().getTimeInMillis(pettingAnimationState.frog_Petting$getEntityRenderState().ageInTicks));
                }
            }


            //this.setupAnim(this.pettingState);
            //		animationState.run(state -> AnimationHelper.animate(this, animation, (long)((float)state.getTimeInMilliseconds(age) * speedMultiplier), 1.0F, ANIMATION_VEC));
        }
    }

    @Inject(method = "Lnet/minecraft/client/model/Model;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;III)V", at = @At("HEAD"))
    public final void renderToBuffer(final PoseStack poseStack, final VertexConsumer buffer, final int lightCoords, final int overlayCoords, final int color, CallbackInfo ci) {
        this.frog_Petting$setPettingAnim();
    }
    @Inject(method = "setupAnim", at = @At(value = "RETURN"))
    private void onSetupAnim(S state, CallbackInfo ci) {
        this.pettingState = state;

        //frog_Petting$setPettingAngles();
    }


    @Shadow public abstract void setupAnim(final S state);
    @Shadow protected ModelPart root;
}
