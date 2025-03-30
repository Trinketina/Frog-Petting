package net.trinketina.frogpetting.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.trinketina.frogpetting.FrogPettingModClient;
import net.trinketina.frogpetting.PettableInterface;
import net.trinketina.frogpetting.config.PettingConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(MobEntity.class)
public abstract class PettingMixin
    extends LivingEntity implements PettableInterface {
    protected int last_pet = -100;

    public abstract boolean uniqueRequirements();

    @Override public double getOffset() {
        return default_offset;
    }
    @Override public void uniqueInteraction(PlayerEntity player, Hand hand) { this.getWorld().playSoundFromEntityClient(this, this.getAmbientSound(), SoundCategory.AMBIENT, this.getSoundVolume(), this.getSoundPitch()); }
    @Override public boolean uniqueRequirements(PlayerEntity player, Hand hand) {return !player.isSneaking() && this.canBeLeashed();}

    @Inject(method = "interactMob", at = @At("HEAD"), cancellable = true)
    public void onInteractMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (!getWorld().isClient) return;

        FrogPettingModClient.LOGGER.info("trying to pet");
        ItemStack itemStack = player.getStackInHand(hand);
        //check whether the hand is empty
        if(itemStack.isEmpty() && uniqueRequirements(player, hand)) {
            if (this.age < last_pet + PettingConfig.COOLDOWN) {
                cir.setReturnValue(ActionResult.SUCCESS);
                return;
            }
            //runs the custom interactions, if any are present
            uniqueInteraction(player, hand);
            //player.playSound(getAmbientSound());
            getWorld().addParticleClient(ParticleTypes.HEART,this.getX()+Math.random()*.1,this.getY()+Math.random()*.5+getOffset(),this.getZ()+Math.random()*.1,0.0D, 0.2D, 0.0D);
            last_pet = this.age;

            FrogPettingModClient.LOGGER.info("success");
            cir.setReturnValue(ActionResult.SUCCESS);
        }
        //runs through the other interactions if petting fails
    }
    @Shadow public SoundEvent getAmbientSound() {return null;}
    @Shadow public boolean canBeLeashed() {
        return !(this instanceof Monster);
    }
    protected PettingMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }
}