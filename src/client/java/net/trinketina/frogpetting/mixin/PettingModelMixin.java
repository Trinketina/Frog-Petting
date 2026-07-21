package net.trinketina.frogpetting.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.trinketina.frogpetting.interfaces.IPettingModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(Model.class)
public class PettingModelMixin<T extends Entity> implements IPettingModel<T>  {
    @Unique
    ModelPart pettingModelRoot = null;


    /*@Inject(method = "renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;II)V", at = @At("HEAD"))
    public final void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int j, CallbackInfo ci) {
        //this.frog_Petting$setPettingAnim();
    }*/

    @Override
    public void frog_Petting$setPettingAnim(LivingEntity entity) {}

    @Override
    public ModelPart frog_petting$getModelRoot() {
        return pettingModelRoot;
    }

    @Override
    public void frog_petting$setModelRoot(ModelPart root) {
        this.pettingModelRoot = root;
    }

    @Override
    public Optional<ModelPart> frog_petting$getAnyDescendantWithName(String string) {
        return string.equals("root") ? Optional.of(this.pettingModelRoot) : this.pettingModelRoot.getAllParts().filter((modelPart) -> modelPart.hasChild(string)).findFirst().map((modelPart) -> modelPart.getChild(string));
    }

    @Override
    public void frog_petting$ResetPose() {
        this.pettingModelRoot.getAllParts().forEach(ModelPart::resetPose);
    }
}
