package net.trinketina.frogpetting.mixin;

import net.minecraft.entity.*;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.trinketina.frogpetting.*;
import net.trinketina.frogpetting.config.PettingConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LivingEntity.class)
public abstract class PettingLivingEntityMixin extends Entity implements IPettitngInteract, IPettingAnimationState, IPettingSound {
    public PettingLivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }
    @Unique
    public final AnimationState pettingAnimationState = new AnimationState();


    @Unique
    protected int last_pet_age = -100;


    @Override public void frog_Petting$copyToPettingAnimationState(AnimationState animationState) {
        pettingAnimationState.copyFrom(animationState);
    }

    @Override
    public AnimationState frog_Petting$getPettingAnimationState() {
        return pettingAnimationState;
    }

    @Unique
    private boolean requireSneaking(String entity_id) {
        if (PettingData.OFFSETS.containsKey(entity_id)) {
            if (PettingData.OFFSETS.get(entity_id).require_crouching) {
                PettingClient.LOGGER.info("need to be sneaking");
                //entity data requires sneaking
                //likely client side only?
                //WARN:: might still send petting request to server
                //TODO:: maybe make require crouching a config for specific entities rather than a resource value?
                //TODO:: or make it a tag
                return true;
            }
        }
        if (((Entity)(Object)this) instanceof LivingEntity living_entity) {
            if (living_entity.hasStackEquipped(EquipmentSlot.SADDLE)) {
                //require sneaking when saddle is equipped
                return true;
            }
        }
        if (((Entity)(Object)this) instanceof AbstractHorseEntity) {
            return true;
        }

        return false;
    }

    @Override
    public ActionResult frog_Petting$pettingInteract(PlayerEntity player, Hand hand) {
        String entity_id = this.getType().toString();
        ItemStack itemStack = player.getStackInHand(hand);

        if (!itemStack.isEmpty()) {
            return ActionResult.PASS;
        }
        if (this.age < last_pet_age + PettingConfig.COOLDOWN) {
            //PettingClient.LOGGER.info("cooldown");
            //cooldown not finished
            return ActionResult.PASS;
        }
        if (PettingConfig.IGNORED_MOBS.contains(entity_id)) {
            //PettingClient.LOGGER.info("petting " + entity_id + " is ignored");
            // TODO:: swap to tags for this?
            return ActionResult.PASS;
        }
        if ((!((LivingEntity)(Object)this instanceof PassiveEntity) || (this instanceof InteractionObserver) ) && !PettingData.OFFSETS.containsKey(entity_id)) {
            //skip if the entity is hostile and not in the offsets
            //also skips if the entity is a villager, TODO:: maybe move to requireSneaking instead?
            //WARN:: might be clientside only for the added offsets. might need to rely on tags for that
            return ActionResult.PASS;
        }
        if (requireSneaking(entity_id)) {
            if (!player.isSneaking()) {
                //if sneaking is required, and player is not sneaking, don't pet
                return ActionResult.PASS;
            }
        } else if (player.isSneaking()) {
            //if sneaking is not required, and the player is sneaking, don't pet
            return ActionResult.PASS;
        }
        if (this instanceof Leashable leashable) {
            if (leashable.isLeashed()) {
                //don't pet if leash is attached
                return ActionResult.PASS;
            }
        }

        if (!getWorld().isClient) {
            this.last_pet_age = this.age;
            //cir.setReturnValue(ActionResult.SUCCESS);
            return ActionResult.SUCCESS;
        }
        //PettingClient.LOGGER.info("trying to pet " + entity_id);

        //runs the custom interactions, if any are present
        //TODO:: re-implement sound support

        if (this.frog_Petting$getPettingSound(entity_id) != null) {
            this.getWorld().playSoundFromEntityClient((Entity) (Object) this, this.frog_Petting$getPettingSound(entity_id), SoundCategory.AMBIENT, this.frog_Petting$getPettingSoundVolume(), this.frog_Petting$getPettingSoundPitch(this.random));
        } else if (this.frog_Petting$getPettingAmbientSound() != null) {
            this.getWorld().playSoundFromEntityClient((Entity) (Object) this, this.frog_Petting$getPettingAmbientSound(), SoundCategory.AMBIENT, this.frog_Petting$getPettingSoundVolume(), this.frog_Petting$getPettingSoundPitch(this.random));
            //PettingClient.LOGGER.info("sound id: " + this.frog_Petting$getPettingAmbientSound().id());

        }
        this.frog_Petting$getPettingAnimationState().start(this.age);


        Vec3d rotation = this.getRotationVecClient();

        double forward_offset = 0.0;
        double vertical_offset = 0.5;
        if (PettingData.OFFSETS.containsKey(entity_id)) {
            forward_offset = PettingData.OFFSETS.get(entity_id).offset[0];
            vertical_offset = PettingData.OFFSETS.get(entity_id).offset[1];
        }

        this.getWorld().addParticleClient(ParticleTypes.HEART,
                this.getX() + Math.random() * .1 + (forward_offset * rotation.getX()),
                this.getY() + Math.random() * .5 + vertical_offset,
                this.getZ() + Math.random() * .1 + (forward_offset * rotation.getZ()),
                0.0D, 0.2D, 0.0D);
        last_pet_age = this.age;

        PettingClient.LOGGER.info("petted " + entity_id);
        //cir.setReturnValue(ActionResult.SUCCESS);
        return ActionResult.SUCCESS;
    }
}
