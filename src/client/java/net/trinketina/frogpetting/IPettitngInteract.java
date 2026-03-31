package net.trinketina.frogpetting;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;

public interface IPettitngInteract {
    default InteractionResult frog_Petting$pettingInteract(Player player, InteractionHand hand) {return InteractionResult.PASS;}
}
