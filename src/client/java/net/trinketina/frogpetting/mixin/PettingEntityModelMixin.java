package net.trinketina.frogpetting.mixin;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.trinketina.frogpetting.PettingClient;
import net.trinketina.frogpetting.PettingData;
import net.trinketina.frogpetting.animation.AnimationHandler;
import net.trinketina.frogpetting.interfaces.IPettingAnimationState;
import net.trinketina.frogpetting.interfaces.IPettingModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import traben.entity_model_features.mixin.mixins.MixinModel;
import traben.entity_model_features.models.IEMFModel;

import java.util.function.Function;

@Mixin(EntityModel.class)
public abstract class PettingEntityModelMixin<T extends Entity> extends Model implements IPettingModel<T> {
    @Unique
    private T petting_entity;

    public PettingEntityModelMixin(Function<ResourceLocation, RenderType> function) {
        super(function);
    }


    @Override
    public void frog_Petting$setPettingAnim() {
        if (this.petting_entity instanceof IPettingAnimationState pettingAnimationState && this instanceof IEMFModel emfModel) {
            if (petting_entity == null || petting_entity.getType() == null) {
                return;
            }
            String entity_id = petting_entity.getType().toString();

            if (pettingAnimationState.frog_Petting$getPettingAnimationState().isStarted()) {
                pettingAnimationState.frog_Petting$getPettingAnimationState().updateTime(petting_entity.tickCount, 1.0F);
                if (pettingAnimationState.frog_Petting$isBaby() && PettingData.BABY_PETTING_ANIMATIONS.containsKey(entity_id)) {
                    AnimationHandler.animate(emfModel, PettingData.BABY_PETTING_ANIMATIONS.get(entity_id), pettingAnimationState.frog_Petting$getPettingAnimationState(), pettingAnimationState.frog_Petting$getPettingAnimationState().getAccumulatedTime());
                }
                else if (PettingData.PETTING_ANIMATIONS.containsKey(entity_id)) {
                    AnimationHandler.animate(emfModel, PettingData.PETTING_ANIMATIONS.get(entity_id), pettingAnimationState.frog_Petting$getPettingAnimationState(), pettingAnimationState.frog_Petting$getPettingAnimationState().getAccumulatedTime());
                }
            }
        }

    }



    @Inject(method = "prepareMobModel", at = @At("HEAD"))
    public void preparePettingMobModel(T entity, float f, float g, float h, CallbackInfo ci) {
        petting_entity = entity;
    }
}