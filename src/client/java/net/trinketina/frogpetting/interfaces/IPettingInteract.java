package net.trinketina.frogpetting.interfaces;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

public interface IPettingInteract {

    default void TryInteractPet(MinecraftClient minecraft) {
        if (minecraft.crosshairTarget != null && minecraft.crosshairTarget.getType() == HitResult.Type.ENTITY) {
            EntityHitResult entityHit = (EntityHitResult)minecraft.crosshairTarget;
            Entity entity = entityHit.getEntity();

            assert minecraft.player != null;
            if (minecraft.player.canInteractWithEntity(entity, 0.0) && entity instanceof IPettingInteract pettable) {
                ActionResult result = pettable.frog_Petting$pettingInteract(minecraft.player, Hand.MAIN_HAND, true);

                if (result == ActionResult.SUCCESS) {
                    if (minecraft.player.getActiveItem() == ItemStack.EMPTY) {
                        minecraft.player.swingHand(Hand.MAIN_HAND);
                    }
                    //minecraft.player.swing(InteractionHand.MAIN_HAND);
                }
            }
        }
    }

    default ActionResult frog_Petting$pettingInteract(PlayerEntity player, Hand hand, boolean fromKeybind) {return ActionResult.PASS;}
}