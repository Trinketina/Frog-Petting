package net.trinketina.frogpetting.mixin;

import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.FrogEntity;
import net.minecraft.entity.passive.FrogVariants;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.trinketina.frogpetting.PettableInterface;
import net.trinketina.frogpetting.config.PettingConfig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;


@Mixin(FrogEntity.class)
public abstract class PettableFrog
    extends PettingMixin
        implements FrogVariants, PettableInterface {
    @Unique
    protected double vertical_particle_offset = .5d;

    @Override public void uniqueInteraction(PlayerEntity player, Hand hand) {
        //plays the croaking animation, age decreased slightly to make the animation start at a later step
        if (PettingConfig.ENABLE_FROG_UNIQUE)
            this.croakingAnimationState.start(this.age-10);
        super.uniqueInteraction(player, hand);
    }
    @Override public double getVerticalOffset() {
        return vertical_particle_offset;
    }

    @Shadow @Final public AnimationState croakingAnimationState;
    protected PettableFrog(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }
}