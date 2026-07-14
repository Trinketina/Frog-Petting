package net.trinketina.frogpetting.mixin;

import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.trinketina.frogpetting.interfaces.IPettingAnimationState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class PettingEntityMixin implements IPettingAnimationState {
    @Shadow
    public int tickCount;
    @Unique
    public AnimationState pettingAnimationState = new AnimationState();

    @Override
    public AnimationState frog_Petting$getPettingAnimationState() {
        return pettingAnimationState;
    }

}
