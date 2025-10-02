package net.trinketina.frogpetting.mixin;

import net.trinketina.frogpetting.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class PettingEntityMixin implements IPettitngInteract {
    @Inject(method = "interact", at = @At("HEAD"), cancellable = true)
    public void onInteract(PlayerEntity player, Hand hand, CallbackInfoReturnable< ActionResult > cir) {

        ActionResult pettingResult = this.frog_Petting$pettingInteract(player, hand);
        if (pettingResult != ActionResult.SUCCESS) {
            return;
        }
        else {
            cir.setReturnValue(pettingResult);
        }
    }
}
