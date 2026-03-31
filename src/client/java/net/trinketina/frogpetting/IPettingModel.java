package net.trinketina.frogpetting;

import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.AnimationState;

public interface IPettingModel {
    KeyframeAnimation frog_Petting$tryGetKeyframeAnimation(String entity_id);
    //void frog_Petting$setPettingAngles(EntityRenderState state);
    void frog_Petting$setPettingAngles();

    ModelPart frog_Petting$getRoot();
}
