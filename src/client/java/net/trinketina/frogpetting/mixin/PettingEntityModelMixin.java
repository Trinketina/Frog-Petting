package net.trinketina.frogpetting.mixin;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.util.Identifier;
import net.trinketina.frogpetting.IPettingAnimationState;
import net.trinketina.frogpetting.IPettingModel;
import net.trinketina.frogpetting.PettingData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Function;

@Mixin(EntityModel.class)
public abstract class PettingEntityModelMixin<T extends EntityRenderState> extends Model implements IPettingModel {

    @Unique
    private EntityRenderState pettingState;

    public PettingEntityModelMixin(ModelPart root, Function<Identifier, RenderLayer> layerFactory) {
        super(root, layerFactory);
    }

    @Override
    public void frog_Petting$setPettingAngles() {
        if (this.pettingState instanceof IPettingAnimationState pettingRenderState) {
            String entity_id = pettingState.entityType.toString();

            if (PettingData.PETTING_ANIMATIONS.containsKey(entity_id)) {
                this.animate(pettingRenderState.frog_Petting$getPettingAnimationState(), PettingData.PETTING_ANIMATIONS.get(entity_id), pettingState.age);
            }
        }
    }

    @Override
    public void frog_Petting$setPettingAngles(EntityRenderState renderState) {
        if (renderState instanceof IPettingAnimationState pettingRenderState) {
            String entity_id = renderState.entityType.toString();

            if (PettingData.PETTING_ANIMATIONS.containsKey(entity_id)) {
                this.animate(pettingRenderState.frog_Petting$getPettingAnimationState(), PettingData.PETTING_ANIMATIONS.get(entity_id), renderState.age);
            }
        }
    }

    @Inject(method = "setAngles", at = @At(value = "RETURN"))
    private void onSetAngles(T state, CallbackInfo ci) {
        this.pettingState = state;
        /*if (state instanceof IPettingAnimationState) {
            IPettingAnimationState pettingRenderState = (IPettingAnimationState) state;
            String entity_id = state.entityType.toString();

            if (PettingData.PETTING_ANIMATIONS.containsKey(entity_id)) {
                this.animate(pettingRenderState.frog_Petting$getPettingAnimationState(), PettingData.PETTING_ANIMATIONS.get(entity_id), state.age);
            }
        }*/
    }
}
