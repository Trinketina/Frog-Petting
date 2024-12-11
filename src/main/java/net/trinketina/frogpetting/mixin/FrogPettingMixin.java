package net.trinketina.frogpetting.mixin;

import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.VariantHolder;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.FrogEntity;
import net.minecraft.entity.passive.FrogVariant;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.trinketina.frogpetting.FrogPettingModClient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;


@Mixin(FrogEntity.class)
public abstract class FrogPettingMixin
    extends AnimalEntity
        implements VariantHolder<FrogVariant> {
    @Shadow @Final public AnimationState croakingAnimationState;

    protected FrogPettingMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        FrogPettingModClient.LOGGER.info(" Trying to Croak Frog");
        ItemStack itemStack = player.getStackInHand(hand);
        //check whether the hand is empty
        if(itemStack == ItemStack.EMPTY) {
            //plays the croaking animation, age decreased slightly to make the animation start at a later step
            this.croakingAnimationState.start(this.age-10);
            playAmbientSound();

            getWorld().addParticle(ParticleTypes.HEART,this.getX()+Math.random()*.1,this.getY()+Math.random()*.5+.3,this.getZ()+Math.random()*.1,0.0D, 0.2D, 0.0D);

            FrogPettingModClient.LOGGER.info(" Croaked Frog");
            return ActionResult.SUCCESS;
        }
        //runs through the other interactions if petting the frog fails
        return super.interactMob(player, hand);
    }
}