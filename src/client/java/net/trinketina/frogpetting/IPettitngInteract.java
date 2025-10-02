package net.trinketina.frogpetting;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public interface IPettitngInteract {
    default ActionResult frog_Petting$pettingInteract(PlayerEntity player, Hand hand) {return ActionResult.PASS;}
}
