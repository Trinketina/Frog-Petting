package net.trinketina.frogpetting.mixin;

import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.FrogEntity;
import net.minecraft.entity.passive.FrogVariants;

import net.minecraft.world.World;
import net.trinketina.frogpetting.Petting;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;


@Mixin(FrogEntity.class)
public abstract class FrogEntityPetting
    extends PettingMixin
        implements FrogVariants, Petting {
    @Shadow @Final public AnimationState croakingAnimationState;

    protected FrogEntityPetting(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override public void customInteraction() {
        //plays the croaking animation, age decreased slightly to make the animation start at a later step
        this.croakingAnimationState.start(this.age-10);
    }
}