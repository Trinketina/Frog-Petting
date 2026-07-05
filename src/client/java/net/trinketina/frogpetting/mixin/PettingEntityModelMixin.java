package net.trinketina.frogpetting.mixin;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.trinketina.frogpetting.PettingData;
import net.trinketina.frogpetting.interfaces.IPettingAnimationState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.Function;

@Mixin(EntityModel.class)
public abstract class PettingEntityModelMixin<T extends Entity> extends Model {
    @Unique
    private T pettingState;

    public PettingEntityModelMixin(ModelPart root, Function<Identifier, RenderLayer> layerFactory) {
        super(root, layerFactory);
    }

    @Override
    public void frog_Petting$setPettingAngles() {
        if (this.pettingState instanceof IPettingAnimationState pettingRenderState) {
            this.setAngles(this.pettingState);
            //String entity_id = pettingState.entityType.toString();

            /*if (PettingData.PETTING_ANIMATIONS.containsKey(entity_id)) {
                this.animate(pettingRenderState.frog_Petting$getPettingAnimationState(), PettingData.PETTING_ANIMATIONS.get(entity_id), pettingState.age);
            }*/
        }
    }

    @Inject(method = "render", at = @At("HEAD"))
    public final void renderToBuffer(final PoseStack poseStack, final VertexConsumer buffer, final int lightCoords, final int overlayCoords, final int color, CallbackInfo ci) {
        this.frog_Petting$setPettingAnim();
    }

    private void onSetAngles(T par1, float par2, float par3, float par4, float par5, float par6, CallbackInfo ci) {
        this.pettingState = state;
    }
}