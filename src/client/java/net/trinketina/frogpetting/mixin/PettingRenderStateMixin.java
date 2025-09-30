package net.trinketina.frogpetting.mixin;

import net.minecraft.client.render.entity.state.EntityRenderState;
import net.trinketina.frogpetting.IPettingAnimationState;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityRenderState.class)
public abstract class PettingRenderStateMixin implements IPettingAnimationState {
}
