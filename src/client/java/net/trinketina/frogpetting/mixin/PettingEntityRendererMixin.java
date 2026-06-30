package net.trinketina.frogpetting.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.world.entity.LivingEntity;
import net.trinketina.frogpetting.IPettingAnimationState;
import net.trinketina.frogpetting.IPettingModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public abstract class PettingEntityRendererMixin<T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<? super S>> {
    @Shadow
    protected M model;

    @Inject(method = "submit", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/EntityModel;setupAnim(Ljava/lang/Object;)V", shift =  At.Shift.AFTER, ordinal = 0))
    private void render(final S state, final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final CameraRenderState camera, CallbackInfo ci) {
        if (this.model instanceof IPettingModel pettingModel) {
            pettingModel.frog_Petting$setPettingAnim();
        }
    }
    
    @Inject(method = "extractRenderState*", at = @At("RETURN"))
    private void onExtractRenderState(T livingEntity, S livingEntityRenderState, float partialTicks, CallbackInfo ci) {
        if (livingEntityRenderState instanceof IPettingAnimationState pettingRenderState && livingEntity instanceof IPettingAnimationState pettingEntity) {

            pettingRenderState.frog_Petting$copyToPettingAnimationState(pettingEntity.frog_Petting$getPettingAnimationState());
        }
    }
}
