package net.trinketina.frogpetting.mixin;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.trinketina.frogpetting.PettingClient;
import net.trinketina.frogpetting.PettingData;
import net.trinketina.frogpetting.config.PettingConfig;
import net.trinketina.frogpetting.interfaces.IPettingAnimationState;
import net.trinketina.frogpetting.interfaces.IPettingInteract;
import net.trinketina.frogpetting.interfaces.IPettingSound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LivingEntity.class)
public abstract class PettingLivingEntityMixin extends Entity implements IPettingInteract, IPettingAnimationState, IPettingSound {

    @Shadow
    public abstract boolean isBaby();

    @Unique
    protected int last_pet_age = -100;

    public PettingLivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Unique
    private boolean requireSneaking(String entity_id) {
        if (PettingData.OFFSETS.containsKey(entity_id)) {
            if (PettingData.OFFSETS.get(entity_id).require_crouching) {
                //if entity data requires sneaking
                return true;
            }
        }
        if (((Object)this) instanceof Saddleable saddleable) {
            if (saddleable.isSaddled()) {
                //require sneaking when saddle is equipped
                return true;
            }
        }
        if (((Object)this) instanceof AbstractHorse) {
            //default to requiring sneaking for horse-likes
            return true;
        }

        return false;
    }

    @Override
    public InteractionResult frog_Petting$pettingInteract(Player player, InteractionHand hand, boolean fromKeybind) {
        String entity_id = this.getType().toString();
        ItemStack itemStack = player.getItemInHand(hand);

        //only run extra checks if the player is interacting with right-click
        if (!fromKeybind) {
            if (!itemStack.isEmpty()) {
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
                //if the player is holding the leash of an entity
                if (leashable.getLeashHolder() == player) {
                    return InteractionResult.PASS;
                }
            }
        }
        if (this.tickCount < last_pet_age + PettingConfig.CONFIG.COOLDOWN) {
            //cooldown not finished
            return InteractionResult.PASS;
        }
        if (PettingConfig.CONFIG.IGNORED_MOBS.contains(entity_id)) {
            //PettingClient.LOGGER.info("petting " + entity_id + " is ignored");
            return InteractionResult.PASS;
        }
        if ((!((LivingEntity)(Object)this instanceof AgeableMob) || ((LivingEntity)(Object)this instanceof AbstractVillager) ) && !PettingData.OFFSETS.containsKey(entity_id)) {
            //skip if the entity is hostile and not in the offsets
            //also skips if the entity is a villager and not in the offset
            return InteractionResult.PASS;
        }
        if (!level().isClientSide()) {
            this.last_pet_age = this.tickCount;
            return InteractionResult.SUCCESS;
        }

        //runs the custom interactions, if any are present

        // plays a sound, if available
        if (this.frog_Petting$getPettingSound(entity_id) != null) {
            this.level().playLocalSound(this, this.frog_Petting$getPettingSound(entity_id), SoundSource.AMBIENT, frog_Petting$getPettingSoundVolume(), frog_Petting$getPettingSoundPitch(this.random));
        } else if (this.frog_Petting$getPettingAmbientSound() != null) {
            this.level().playLocalSound(this, this.frog_Petting$getPettingAmbientSound(), SoundSource.AMBIENT, this.frog_Petting$getPettingSoundVolume(), this.frog_Petting$getPettingSoundPitch(this.random));
        }
        this.frog_Petting$getPettingAnimationState().start(this.tickCount);


        // generates the particle position
        Vec3 rotation = this.getForward();

        double forward_offset = 0.0;
        double vertical_offset = 0.5;
        if (PettingData.OFFSETS.containsKey(entity_id)) {
            if (isBaby() && PettingData.OFFSETS.get(entity_id).baby_offset != null  ) {
                forward_offset = PettingData.OFFSETS.get(entity_id).baby_offset[0];
                vertical_offset = PettingData.OFFSETS.get(entity_id).baby_offset[1];
            }
            else {
                forward_offset = PettingData.OFFSETS.get(entity_id).offset[0];
                vertical_offset = PettingData.OFFSETS.get(entity_id).offset[1];
            }
        }

        //generates the particle
        this.level().addParticle(ParticleTypes.HEART,
                this.getX() + Math.random() * .1 + (forward_offset * rotation.x),
                this.getY() + Math.random() * .5 + vertical_offset,
                this.getZ() + Math.random() * .1 + (forward_offset * rotation.z()),
                0.0D, 0.2D, 0.0D);
        last_pet_age = this.tickCount;

        PettingClient.LOGGER.info("petted " + entity_id);
        return InteractionResult.SUCCESS;
    }

}
