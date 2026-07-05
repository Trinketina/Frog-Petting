package net.trinketina.frogpetting.interfaces;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public interface IPettingInteract {

    default void TryInteractPet(Minecraft minecraft) {
        if (minecraft.player != null && minecraft.hitResult != null && minecraft.hitResult.getType() == HitResult.Type.ENTITY) {
            EntityHitResult entityHit = (EntityHitResult)minecraft.hitResult;
            Entity entity = entityHit.getEntity();

            if (minecraft.player.isWithinEntityInteractionRange(entity, 0.0) && entity instanceof IPettingInteract pettable) {
                InteractionResult result = pettable.frog_Petting$pettingInteract(minecraft.player, InteractionHand.MAIN_HAND, true);

                if (result == InteractionResult.SUCCESS) {
                    if (minecraft.player.getMainHandItem() == ItemStack.EMPTY) {
                        minecraft.player.swing(InteractionHand.MAIN_HAND);
                    }
                }
            }
        }
    }

    default InteractionResult frog_Petting$pettingInteract(Player player, InteractionHand hand, boolean fromKeybind) {return InteractionResult.PASS;}
}
