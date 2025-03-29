package net.trinketina.frogpetting;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.trinketina.frogpetting.config.PettingConfig;

public interface PettableInterface {
    float default_offset = .5f;

    //add any unique interactions to the mob
    void uniqueInteraction();
    boolean uniqueRequirements(PlayerEntity player, Hand hand);

    float getOffset();
}
