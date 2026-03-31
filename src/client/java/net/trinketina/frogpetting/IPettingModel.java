package net.trinketina.frogpetting;

import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.geom.ModelPart;

public interface IPettingModel<S> {
    boolean frog_Petting$tryGenerateKeyframeAnimation(String entity_id);
    void frog_Petting$setPettingAngles();
}
