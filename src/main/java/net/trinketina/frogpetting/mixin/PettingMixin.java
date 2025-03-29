package net.trinketina.frogpetting.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.trinketina.frogpetting.FrogPettingModClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(AnimalEntity.class)
public abstract class PettingMixin
    extends PassiveEntity {
    protected PettingMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Unique
    protected float vertical_offset = .3f;

    @Inject(method = "interactMob", at = @At("HEAD"), cancellable = true)
    public ActionResult onInteractMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        FrogPettingModClient.LOGGER.info("Trying to Pet");
        ItemStack itemStack = player.getStackInHand(hand);
        //check whether the hand is empty
        if(itemStack == ItemStack.EMPTY) {
            //plays the croaking animation, age decreased slightly to make the animation start at a later step
            //this.croakingAnimationState.start(this.age-10);
            playAmbientSound();

            getWorld().addParticleClient(ParticleTypes.HEART,this.getX()+Math.random()*.1,this.getY()+Math.random()*.5+vertical_offset,this.getZ()+Math.random()*.1,0.0D, 0.2D, 0.0D);

            FrogPettingModClient.LOGGER.info("success!");
            cir.setReturnValue(ActionResult.SUCCESS);
            return ActionResult.SUCCESS;
        }
        //runs through the other interactions if petting the frog fails
        return super.interactMob(player, hand);
    }
}