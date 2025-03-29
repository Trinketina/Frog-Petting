package net.trinketina.frogpetting.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.trinketina.frogpetting.FrogPettingModClient;
import net.trinketina.frogpetting.Petting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(MobEntity.class)
public abstract class PettingMixin
    extends LivingEntity implements Petting {
    protected PettingMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    protected int last_pet = -100;
    @Inject(method = "interactMob", at = @At("HEAD"), cancellable = true)
    public void onInteractMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        FrogPettingModClient.LOGGER.info("trying to pet");
        ItemStack itemStack = player.getStackInHand(hand);
        //check whether the hand is empty
        if(itemStack == ItemStack.EMPTY && !player.isSneaking() && this.canBeLeashed() && this.age > last_pet + cooldown) {

            //runs the custom interactions, if any are present
            customInteraction();
            playAmbientSound();

            getWorld().addParticleClient(ParticleTypes.HEART,this.getX()+Math.random()*.1,this.getY()+Math.random()*.5+getOffset(),this.getZ()+Math.random()*.1,0.0D, 0.2D, 0.0D);
            last_pet = this.age;

            FrogPettingModClient.LOGGER.info("success");
            cir.setReturnValue(ActionResult.SUCCESS);
            return;
        }
        //runs through the other interactions if petting fails
        return;
    }
    @Shadow public void playAmbientSound() {}
    @Shadow public boolean canBeLeashed() {
        return !(this instanceof Monster);
    }

    @Override public float getOffset() {
        return default_offset;
    }
    @Override public void customInteraction() {}
}