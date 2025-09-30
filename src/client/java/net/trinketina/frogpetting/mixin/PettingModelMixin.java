package net.trinketina.frogpetting.mixin;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.util.Identifier;
import net.trinketina.frogpetting.IPettingAnimationState;
import net.trinketina.frogpetting.PettingAnimations;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Function;

@Mixin(EntityModel.class)
public abstract class PettingModelMixin<T extends EntityRenderState> extends Model {

    public PettingModelMixin(ModelPart root, Function<Identifier, RenderLayer> layerFactory) {
        super(root, layerFactory);
    }

    @Inject(method = "setAngles", at = @At("RETURN"))
    private void onSetAngles(T state, CallbackInfo ci) {
        if (state instanceof IPettingAnimationState) {
            IPettingAnimationState pettingRenderState = (IPettingAnimationState) state;
            String entity_id = state.entityType.toString();

            //PettingClient.LOGGER.info(entity_id);

            if (PettingAnimations.PETTING_ANIMATIONS.containsKey(entity_id)) {
                this.animate(pettingRenderState.frog_Petting$getPettingAnimationState(), PettingAnimations.PETTING_ANIMATIONS.get(entity_id), state.age);
            }
            //state.entityType.toString();
            //this.animate(pettingRenderState.getPettingAnimationState(), TBD, state.age);
        }
    }
}
