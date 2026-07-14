package net.trinketina.frogpetting.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.world.entity.Entity;
import net.trinketina.frogpetting.interfaces.IPettingModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Model.class)
public class PettingModelMixin<T extends Entity> implements IPettingModel<T>  {


    @Inject(method = "renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;II)V", at = @At("HEAD"))
    public final void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int j, CallbackInfo ci) {
        this.frog_Petting$setPettingAnim();
    }

    @Override
    public void frog_Petting$setPettingAnim() {}
}
