package net.trinketina.frogpetting;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

public interface Petting {
    int cooldown = 5;
    float default_offset = .5f;

    //add any unique interactions to the mob
    void customInteraction();

    float getOffset();
}
