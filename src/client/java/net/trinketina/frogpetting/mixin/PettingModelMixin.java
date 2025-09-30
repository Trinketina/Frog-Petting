package net.trinketina.frogpetting.mixin;

import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.trinketina.frogpetting.IPettingAnimationState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityModel.class)
public abstract class PettingModelMixin<T extends EntityRenderState> implements IPettingAnimationState {

    @Inject(method = "setAngles", at = @At("RETURN"))
    private void onSetAngles(T state, CallbackInfo ci) {

    }
}
