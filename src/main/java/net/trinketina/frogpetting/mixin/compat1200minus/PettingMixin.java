package net.trinketina.frogpetting.mixin.compat1200minus;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.control.JumpControl;
import net.minecraft.entity.ai.control.LookControl;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.trinketina.frogpetting.FrogPettingModClient;
import net.trinketina.frogpetting.PettableInterface;
import net.trinketina.frogpetting.config.PettingConfig;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEntity.class)
public abstract class PettingMixin
    extends LivingEntity implements PettableInterface {
    @Unique
    protected int last_pet = -100;

    public abstract boolean uniqueRequirements();

    @Override public double getVerticalOffset() {
        return default_vertical_offset;
    }
    @Override public double getForwardOffset() {return default_forward_offset;}
    @Override public void uniqueInteraction(PlayerEntity player, Hand hand) {
        if (this.getAmbientSound() == null) return;
        this.getWorld().playSoundFromEntity(player,this, this.getAmbientSound(), SoundCategory.AMBIENT, this.getSoundVolume(), this.getSoundPitch());
    }
    @Override public boolean uniqueRequirements(PlayerEntity player, Hand hand) {return !player.isSneaking() && this.canBeLeashedBy(player);}

    @Inject(method = "interactMob", at = @At("HEAD"), cancellable = true)
    public void onInteractMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {

        FrogPettingModClient.LOGGER.info("trying to pet " + this.getType().toString());
        ItemStack itemStack = player.getStackInHand(hand);
        //check whether the hand is empty
        if(itemStack.isEmpty() && uniqueRequirements(player, hand)) {
            if (this.age < last_pet + PettingConfig.COOLDOWN) {
                return;
            }
            if (!getWorld().isClient) {
                this.last_pet = this.age;
                cir.setReturnValue(ActionResult.SUCCESS);
                return;
            }
            //runs the custom interactions, if any are present
            uniqueInteraction(player, hand);
            Vec3d rotation = this.getRotationVecClient();
            this.getWorld().addParticle(ParticleTypes.HEART,
                    this.getX()+Math.random()*.1 + (getForwardOffset() * rotation.getX()),
                    this.getY()+Math.random()*.5 + getVerticalOffset(),
                    this.getZ()+Math.random()*.1 + (getForwardOffset() * rotation.getZ()),
                    0.0D, 0.2D, 0.0D);
            last_pet = this.age;

            FrogPettingModClient.LOGGER.info("success");
            cir.setReturnValue(ActionResult.SUCCESS);
        }
        //runs through the other interactions if petting fails
        return;
    }
    @Shadow public ActionResult interactMob(PlayerEntity player, Hand hand) {return ActionResult.PASS;}
    //@Shadow public abstract boolean hasSaddleEquipped();
    @Shadow public abstract SoundEvent getAmbientSound();
    @Dynamic
    @Shadow public abstract boolean canBeLeashedBy(PlayerEntity player);

    @Shadow protected JumpControl jumpControl;

    @Shadow public abstract LookControl getLookControl();
    
    protected PettingMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }
}