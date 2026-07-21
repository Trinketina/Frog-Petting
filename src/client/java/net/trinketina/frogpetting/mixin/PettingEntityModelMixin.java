package net.trinketina.frogpetting.mixin;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.trinketina.frogpetting.PettingData;
import net.trinketina.frogpetting.animation.AnimationHandler;
import net.trinketina.frogpetting.interfaces.IPettingAnimationState;
import net.trinketina.frogpetting.interfaces.IPettingModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Function;

@Mixin(EntityModel.class)
public abstract class PettingEntityModelMixin<T extends Entity> extends Model implements IPettingModel<T> {
    @Unique
    private LivingEntity petting_entity;
    @Unique
    boolean isLayer = true;

    public PettingEntityModelMixin(Function<ResourceLocation, RenderType> function) {
        super(function);
    }

    @Override
    public void frog_Petting$setPettingAnim(Entity entity, float entity_bob, boolean isLayer) {
        /*if (!isLayer) {
            this.isLayer = false;
        }*/
        if (entity instanceof IPettingAnimationState pettingAnimationState) {
            if (entity == null || entity.getType() == null || this.frog_petting$getModelRoot() == null) {
                //PettingClient.LOGGER.warn("Entity is null or entity is null");
                return;
            }
            String entity_id = entity.getType().toString();
            if (!isLayer && PettingData.OFFSETS.containsKey(entity_id) && !PettingData.OFFSETS.get(entity_id).require_alternate_rendering) {
                return;
            }

            if (pettingAnimationState.frog_Petting$getPettingAnimationState().isStarted()) {
                pettingAnimationState.frog_Petting$getPettingAnimationState().updateTime(entity_bob, 1.0F);
                if (pettingAnimationState.frog_Petting$isBaby() && PettingData.BABY_PETTING_ANIMATIONS.containsKey(entity_id)) {
                    AnimationHandler.animate(this, PettingData.BABY_PETTING_ANIMATIONS.get(entity_id), pettingAnimationState.frog_Petting$getPettingAnimationState(), pettingAnimationState.frog_Petting$getPettingAnimationState().getAccumulatedTime());
                }
                else if (PettingData.PETTING_ANIMATIONS.containsKey(entity_id)) {
                    AnimationHandler.animate(this, PettingData.PETTING_ANIMATIONS.get(entity_id), pettingAnimationState.frog_Petting$getPettingAnimationState(), pettingAnimationState.frog_Petting$getPettingAnimationState().getAccumulatedTime());
                }
            }
        }

    }
/*    @Override
    public void prepareAnim(LivingEntity entity, float entity_bob) {
        petting_entity = entity;
        petting_bob = entity_bob;
    }
    @Unique
    private void frog_Petting$setPettingAnim() {
        frog_Petting$setPettingAnim(petting_entity, petting_bob);
    }*/

    /*@Override
    public void frog_petting$resetPose() {
        if (this.frog_petting$getModelRoot() != null) {
            frog_petting$getModelRoot().getAllParts().forEach(ModelPart::resetPose);
        }
    }*/


    @Inject(method = "prepareMobModel", at = @At("HEAD"))
    public void preparePettingMobModel(T entity, float f, float g, float h, CallbackInfo ci) {
        frog_petting$resetPose();
        if (isLayer) {
            frog_Petting$setPettingAnim(entity, (float) entity.tickCount + h, true);
        }
    }
}