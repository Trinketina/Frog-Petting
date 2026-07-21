package net.trinketina.frogpetting.interfaces;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;

import java.util.Optional;

public interface IPettingModel<S> {
    void frog_Petting$setPettingAnim(LivingEntity entity, float entity_bob);

    ModelPart frog_petting$getModelRoot();

    void frog_petting$setModelRoot(ModelPart root);

    Optional<ModelPart> frog_petting$getAnyDescendantWithName(String string);

    void frog_petting$resetPose();

}