package net.trinketina.frogpetting.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.AllayEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.trinketina.frogpetting.FrogPettingModClient;
import net.trinketina.frogpetting.Petting;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(AllayEntity.class)
public abstract class AllayEntityPetting
    extends PathAwareEntity implements Petting {
    protected AllayEntityPetting(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Unique
    protected float vertical_offset = .4f;
    @Unique
    protected int last_pet = -5;


    /*@Inject(method = "interactMob", at = @At("HEAD"), cancellable = true)
    public ActionResult interactMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        FrogPettingModClient.LOGGER.info("Trying to Pet Allay");
        ItemStack itemStack = player.getStackInHand(hand);
        //check whether the hand is empty

        if(itemStack.isEmpty() && !this.isHoldingItem() && !player.isSneaking() && this.age > last_pet + cooldown) {
            playAmbientSound();
            customInteraction();

            getWorld().addParticleClient(ParticleTypes.HEART,this.getX()+Math.random()*.1,this.getY()+Math.random()*.5+.3,this.getZ()+Math.random()*.1,0.0D, 0.2D, 0.0D);

            last_pet = this.age;
            FrogPettingModClient.LOGGER.info("Success");
            cir.setReturnValue(ActionResult.SUCCESS);
            return ActionResult.SUCCESS;
        }
        //runs through the other interactions if petting fails
        return super.interactMob(player, hand);
    }
*/
    @Override public void customInteraction() {
        //makes the Allay dance!
        this.setDancing(true);
    }
    @Override public float getOffset() {
        return vertical_offset;
    }

    @Shadow @Final private static TrackedData<Boolean> DANCING;

    @Shadow public boolean isHoldingItem() {
        return !this.getStackInHand(Hand.MAIN_HAND).isEmpty();
    }
    @Shadow public void setDancing(boolean dancing) {}
}