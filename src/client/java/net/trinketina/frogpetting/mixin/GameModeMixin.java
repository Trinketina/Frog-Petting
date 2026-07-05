package net.trinketina.frogpetting.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.EntityHitResult;
import net.trinketina.frogpetting.interfaces.IPettingInteract;
import net.trinketina.frogpetting.config.PettingConfig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiPlayerGameMode.class)
public abstract class GameModeMixin {

    @Inject(method = "interact", at = @At("HEAD"), cancellable = true)
    public void pettingInteract(final Player player, final Entity entity, final EntityHitResult hitResult, final InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        if (PettingConfig.CONFIG.DISABLE_RIGHT_CLICK_PET) {
            //skip petting interaction if right click is disabled
            return;
        }
        if (entity instanceof IPettingInteract pettable &&
                pettable.frog_Petting$pettingInteract(player, hand, false) == InteractionResult.SUCCESS) {

            cir.setReturnValue(InteractionResult.SUCCESS);
            cir.cancel();
        }
    }
}
