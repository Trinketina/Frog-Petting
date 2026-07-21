package net.trinketina.frogpetting.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.LivingEntity;
import net.trinketina.frogpetting.PettingClient;
import net.trinketina.frogpetting.interfaces.IContextModelRootProvider;
import net.trinketina.frogpetting.interfaces.IPettingModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public abstract class PettingLivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> extends EntityRenderer<T> implements RenderLayerParent<T, M> {

    @Inject(method = "<init>", at = @At("RETURN"))
    public void onInit(EntityRendererProvider.Context context, EntityModel entityModel, float f, CallbackInfo ci) {
        if (context instanceof IContextModelRootProvider rootProvider && entityModel instanceof IPettingModel pettingModel) {
            pettingModel.frog_petting$setModelRoot(rootProvider.getRoot());
            PettingClient.LOGGER.info("Root Established");
        }
    }


    @Shadow
    protected M model;

    @Shadow
    protected abstract float getBob(T livingEntity, float f);

    @Inject(method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/EntityModel;setupAnim(Lnet/minecraft/world/entity/Entity;FFFFF)V", shift = At.Shift.AFTER, ordinal = 0))
    private void pettingRenderAnim(T livingEntity, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, CallbackInfo ci) {
        if (this.model instanceof IPettingModel pettingModel) {
            pettingModel.frog_Petting$setPettingAnim(livingEntity, this.getBob(livingEntity, g));
        }
    }

    @Inject(method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/EntityModel;setupAnim(Lnet/minecraft/world/entity/Entity;FFFFF)V", ordinal = 0))
    private void pettingRenderResetPose(T livingEntity, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, CallbackInfo ci) {
        if (this.model instanceof IPettingModel pettingModel) {
            pettingModel.frog_petting$ResetPose();
        }
    }
   /* @Inject(method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/EntityModel;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;III)V", shift = At.Shift.AFTER, ordinal = 0))
    private void pettingRenderToBuffer(T livingEntity, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, CallbackInfo ci) {
        if (this.model instanceof IPettingModel pettingModel) {
            pettingModel.frog_Petting$setPettingAnim();
        }
    }*/

    protected PettingLivingEntityRendererMixin(EntityRendererProvider.Context context) {
        super(context);
    }
}
