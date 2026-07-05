package net.trinketina.frogpetting.mixin;

import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.trinketina.frogpetting.IPettitngInteract;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiPlayerGameMode.class)
public abstract class GameModeMixin {

    @Inject(method = "interact", at = @At("HEAD"), cancellable = true)
    public void pettingInteract(final Player player, final Entity entity, final EntityHitResult hitResult, final InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        if (entity instanceof IPettitngInteract pettable) {
            if (pettable.frog_Petting$pettingInteract(player, hand) != InteractionResult.SUCCESS) {
                return;
            }
            else {
                cir.setReturnValue(InteractionResult.SUCCESS);
                cir.cancel();
            }
        }
    }
}
