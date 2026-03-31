package net.trinketina.frogpetting.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.trinketina.frogpetting.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class PettingEntityMixin implements IPettitngInteract {
    @Inject(method = "interact", at = @At("HEAD"), cancellable = true)
    public void onInteract(final Player player, final InteractionHand hand, final Vec3 location, CallbackInfoReturnable<InteractionResult> cir) {

        InteractionResult pettingResult = this.frog_Petting$pettingInteract(player, hand);
        if (pettingResult != InteractionResult.SUCCESS) {
            return;
        }
        else {
            cir.setReturnValue(pettingResult);
        }
    }
}
