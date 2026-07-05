package net.trinketina.frogpetting.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.trinketina.frogpetting.config.PettingConfig;
import net.trinketina.frogpetting.interfaces.IPettingInteract;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class InteractionManagerMixin {



    @Inject(method = "interactEntityAtLocation", at = @At("HEAD"), cancellable = true)
    public void pettingInteract(final PlayerEntity player, final Entity entity, final EntityHitResult hitResult, final Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (PettingConfig.CONFIG.DISABLE_RIGHT_CLICK_PET) {
            return;
        }
        if (entity instanceof IPettingInteract pettable) {
            if (pettable.frog_Petting$pettingInteract(player, hand, false) != ActionResult.SUCCESS) {
                return;
            }
            else {
                cir.setReturnValue(ActionResult.SUCCESS);
                cir.cancel();
            }
        }
    }
}