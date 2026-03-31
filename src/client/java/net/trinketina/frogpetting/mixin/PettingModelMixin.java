package net.trinketina.frogpetting.mixin;

import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.Model;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.trinketina.frogpetting.IPettingAnimationState;
import net.trinketina.frogpetting.IPettingModel;
import net.trinketina.frogpetting.PettingClient;
import net.trinketina.frogpetting.PettingData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Model.class)
public abstract class PettingModelMixin<S> implements IPettingModel {
    @Unique
    private S pettingState;
    @Override
    public boolean frog_Petting$tryGenerateKeyframeAnimation(String entity_id) {
        try {
            if (!PettingData.PETTING_ANIMATIONS.containsKey(entity_id)) return false;
            if (!PettingData.PETTING_KEYFRAMES.containsKey(entity_id) || PettingData.PETTING_KEYFRAMES.get(entity_id) == null) {
                PettingData.PETTING_KEYFRAMES.put(entity_id, PettingData.PETTING_ANIMATIONS.get(entity_id).bake(this.root));
                return true;
            }
            return false;
        } catch (Exception e){
            PettingClient.LOGGER.warn("Could not generate animation for " + entity_id + ": " + e);
        }
        return false;
    }

    @Override
    public void frog_Petting$setPettingAngles() {
        if (this.pettingState instanceof IPettingAnimationState pettingRenderState && pettingState instanceof EntityRenderState renderstate) {
            this.setupAnim(this.pettingState);
            String entity_id = renderstate.entityType.toString();

            if (PettingData.PETTING_ANIMATIONS.containsKey(entity_id)) {
                frog_Petting$tryGenerateKeyframeAnimation(entity_id);
                if (PettingData.PETTING_KEYFRAMES.containsKey(entity_id)) {
                    PettingData.PETTING_KEYFRAMES.get(entity_id).apply(pettingRenderState.frog_Petting$getPettingAnimationState(), renderstate.ageInTicks);
                }
                //this.animate(pettingRenderState.frog_Petting$getPettingAnimationState(), PettingData.PETTING_ANIMATIONS.get(entity_id), pettingState.age);
            }
        }
    }

    @Inject(method = "Lnet/minecraft/client/model/Model;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;III)V", at = @At("HEAD"))
    public final void renderToBuffer(final PoseStack poseStack, final VertexConsumer buffer, final int lightCoords, final int overlayCoords, final int color, CallbackInfo ci) {
        this.frog_Petting$setPettingAngles();
    }
    @Inject(method = "setupAnim", at = @At(value = "RETURN"))
    private void onSetupAnim(S state, CallbackInfo ci) {
        this.pettingState = state;
        //frog_Petting$setPettingAngles();
    }


    @Shadow public abstract void setupAnim(final S state);
    @Shadow protected ModelPart root;
}
