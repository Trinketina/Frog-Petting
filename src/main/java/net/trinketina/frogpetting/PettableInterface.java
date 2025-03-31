package net.trinketina.frogpetting;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;

public interface PettableInterface {
    double default_vertical_offset = .5d;
    double default_forward_offset = 0;

    //add any unique interactions to the mob
    void uniqueInteraction(PlayerEntity player, Hand hand);
    boolean uniqueRequirements(PlayerEntity player, Hand hand);

    double getVerticalOffset();
    double getForwardOffset();
}
