package net.trinketina.frogpetting.mixin;

import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.trinketina.frogpetting.*;
import net.trinketina.frogpetting.config.PettingConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

// TODO(Ravel): can not resolve target class LivingEntity
// TODO(Ravel): can not resolve target class LivingEntity
@Mixin(LivingEntity.class)
public abstract class PettingLivingEntityMixin extends Entity implements IPettitngInteract, IPettingAnimationState, IPettingSound {

    @Unique
    public final AnimationState pettingAnimationState = new AnimationState();


    @Unique
    protected int last_pet_age = -100;

    public PettingLivingEntityMixin(EntityType<?> type, Level level) {
        super(type, level);
    }


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
        if (((Object)this) instanceof LivingEntity living_entity) {
            if (living_entity.hasItemInSlot(EquipmentSlot.SADDLE)) {
                //require sneaking when saddle is equipped
                return true;
            }
        }
        if (((Object)this) instanceof AbstractHorse) {
            return true;
        }

        return false;
    }

    @Override
    public InteractionResult frog_Petting$pettingInteract(Player player, InteractionHand hand) {
        String entity_id = this.getType().toString();
        ItemStack itemStack = player.getItemInHand(hand);
        PettingMain.LOGGER.info("petting" + entity_id);

        if (!itemStack.isEmpty()) {
            return InteractionResult.PASS;
        }
        if (this.tickCount < last_pet_age + PettingConfig.COOLDOWN) {
            //PettingClient.LOGGER.info("cooldown");
            //cooldown not finished
            return InteractionResult.PASS;
        }
        if (PettingConfig.IGNORED_MOBS.contains(entity_id)) {
            //PettingClient.LOGGER.info("petting " + entity_id + " is ignored");
            // TODO:: swap to tags for this?
            return InteractionResult.PASS;
        }
        if ((!((LivingEntity)(Object)this instanceof AgeableMob) || ((LivingEntity)(Object)this instanceof AbstractVillager) ) && !PettingData.OFFSETS.containsKey(entity_id)) {
            //skip if the entity is hostile and not in the offsets
            //also skips if the entity is a villager, TODO:: maybe move to requireSneaking instead?
            //WARN:: might be clientside only for the added offsets. might need to rely on tags for that
            return InteractionResult.PASS;
        }
        if (requireSneaking(entity_id)) {
            if (!player.isCrouching()) {
                //if sneaking is required, and player is not sneaking, don't pet
                return InteractionResult.PASS;
            }
        } else if (player.isCrouching()) {
            //if sneaking is not required, and the player is sneaking, don't pet
            return InteractionResult.PASS;
        }
        if (this instanceof Leashable leashable) {
            if (leashable.isLeashed()) {
                //don't pet if leash is attached
                return InteractionResult.PASS;
            }
        }

        if (!level().isClientSide()) {
            this.last_pet_age = this.tickCount;
            //cir.setReturnValue(ActionResult.SUCCESS);
            return InteractionResult.SUCCESS;
        }
        //PettingClient.LOGGER.info("trying to pet " + entity_id);

        //runs the custom interactions, if any are present
        //TODO:: re-implement sound support

        if (this.frog_Petting$getPettingSound(entity_id) != null) {
            //this.level().playSoundFromEntityClient((AbstractHorse) (Object) this, this.frog_Petting$getPettingSound(entity_id), SoundSource.AMBIENT, this.frog_Petting$getPettingSoundVolume(), this.frog_Petting$getPettingSoundPitch(this.random));
            this.level().playLocalSound(this, this.frog_Petting$getPettingSound(entity_id), SoundSource.AMBIENT, frog_Petting$getPettingSoundVolume(), frog_Petting$getPettingSoundPitch(this.random));
        } else if (this.frog_Petting$getPettingAmbientSound() != null) {
            this.level().playLocalSound(this, this.frog_Petting$getPettingAmbientSound(), SoundSource.AMBIENT, this.frog_Petting$getPettingSoundVolume(), this.frog_Petting$getPettingSoundPitch(this.random));
            //PettingClient.LOGGER.info("sound id: " + this.frog_Petting$getPettingAmbientSound().id());
        }
        this.frog_Petting$getPettingAnimationState().start(this.tickCount);


        Vec3 rotation = this.getForward();

        double forward_offset = 0.0;
        double vertical_offset = 0.5;
        if (PettingData.OFFSETS.containsKey(entity_id)) {
            forward_offset = PettingData.OFFSETS.get(entity_id).offset[0];
            vertical_offset = PettingData.OFFSETS.get(entity_id).offset[1];
        }

        this.level().addParticle(ParticleTypes.HEART,
                this.getX() + Math.random() * .1 + (forward_offset * rotation.x),
                this.getY() + Math.random() * .5 + vertical_offset,
                this.getZ() + Math.random() * .1 + (forward_offset * rotation.z()),
                0.0D, 0.2D, 0.0D);
        last_pet_age = this.tickCount;

        PettingClient.LOGGER.info("petted " + entity_id);
        //cir.setReturnValue(ActionResult.SUCCESS);
        return InteractionResult.SUCCESS;
    }
}
