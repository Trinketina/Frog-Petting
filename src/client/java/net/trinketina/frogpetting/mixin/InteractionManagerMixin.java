package net.trinketina.frogpetting.mixin;

import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.EntityHitResult;
import net.trinketina.frogpetting.config.PettingConfig;
import net.trinketina.frogpetting.interfaces.IPettingInteract;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiPlayerGameMode.class)
public abstract class InteractionManagerMixin {



    @Inject(method = "interactAt", at = @At("HEAD"), cancellable = true)
    public void pettingInteract(Player player, Entity entity, EntityHitResult entityHitResult, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> cir) {
        if (PettingConfig.CONFIG.DISABLE_RIGHT_CLICK_PET) {
            return;
        }
        if (entity instanceof IPettingInteract pettable) {
            if (pettable.frog_Petting$pettingInteract(player, interactionHand, false) != InteractionResult.SUCCESS) {
                return;
            }
            else {
                cir.setReturnValue(InteractionResult.SUCCESS);
                cir.cancel();
            }
        }
    }
}