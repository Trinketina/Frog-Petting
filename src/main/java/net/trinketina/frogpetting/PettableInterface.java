package net.trinketina.frogpetting;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.trinketina.frogpetting.config.PettingConfig;

public interface PettableInterface {
    double default_offset = .5d;

    //add any unique interactions to the mob
    void uniqueInteraction();
    boolean uniqueRequirements(PlayerEntity player, Hand hand);

    double getOffset();
}
