package net.trinketina.frogpetting.mixin;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.trinketina.frogpetting.IPettitngInteract;
import net.trinketina.frogpetting.PettingClient;
import net.trinketina.frogpetting.PettingData;
import net.trinketina.frogpetting.config.PettingConfig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiPlayerGameMode.class)
public abstract class GameModeMixin {
    @Shadow
    @Final
    private Minecraft minecraft;



    @Inject(method = "interact", at = @At("HEAD"), cancellable = true)
    public void pettingInteract(final Player player, final Entity entity, final EntityHitResult hitResult, final InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        if (PettingConfig.CONFIG.DISABLE_RIGHT_CLICK_PET) {
            return;
        }
        if (entity instanceof IPettitngInteract pettable) {
            if (pettable.frog_Petting$pettingInteract(player, hand, false) != InteractionResult.SUCCESS) {
                return;
            }
            else {
                cir.setReturnValue(InteractionResult.SUCCESS);
                cir.cancel();
            }
        }
    }
}
