package net.trinketina.frogpetting.mixin;

import net.trinketina.frogpetting.IPettingAnimationState;
import net.trinketina.frogpetting.PettingClient;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.trinketina.frogpetting.config.PettingConfig;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class NewPettingMixin implements IPettingAnimationState {
    @Unique
    public final AnimationState pettingAnimationState = new AnimationState();

    @Override public void frog_Petting$copyToPettingAnimationState(AnimationState animationState) {
        pettingAnimationState.copyFrom(animationState);
    }

    @Override
    public AnimationState frog_Petting$getPettingAnimationState() {
        return pettingAnimationState;
    }
    //@Shadow public abstract boolean hasStackEquipped(EquipmentSlot slot);

    @Unique
    protected int last_pet_age = -100;


    @Shadow @Nullable protected abstract String getSavedEntityId();
    @Shadow public int age;
    @Shadow public abstract World getWorld();
    @Shadow public abstract Vec3d getRotationVecClient();
    @Shadow public abstract double getX();
    @Shadow public abstract double getY();
    @Shadow public abstract double getZ();

    @Shadow protected abstract boolean couldAcceptPassenger();

    /*@Redirect(method = "interact")
        public ActionResult interact(PlayerEntity player, Hand hand) {
            PettingClient.LOGGER.info("first");
            return super.interact(player, hand);
        }*/

    @Shadow
    @Final
    private EntityType<?> type;

    @Shadow
    public abstract EntityType<?> getType();

    private boolean requireSneaking(String entity_id) {
        if (PettingClient.OFFSETS.containsKey(entity_id)) {
            if (PettingClient.OFFSETS.get(entity_id).require_crouching) {
                PettingClient.LOGGER.info("need to be sneaking");
                //entity data requires sneaking
                //likely client side only?
                //WARN:: might still send petting request to server
                //TODO:: maybe make require crouching a config for specific entities rather than a resource value?
                //TODO:: or make it a tag
                return true;
            }
        }
        if (((Entity)(Object)this) instanceof LivingEntity) {
            LivingEntity living_entity = ((LivingEntity)(Object)this);
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

    @Inject(method = "interact", at = @At("HEAD"), cancellable = true)
    public void onInteract(PlayerEntity player, Hand hand, CallbackInfoReturnable< ActionResult > cir) {
        String entity_id = this.type.toString();
        ItemStack itemStack = player.getStackInHand(hand);

        //PettingClient.LOGGER.info("try pet" + this.type);

        if(itemStack.isEmpty()) {
            if (this.age < last_pet_age + PettingConfig.COOLDOWN) {
                PettingClient.LOGGER.info("cooldown");
                //cooldown not finished
                return;
            }
            if (requireSneaking(entity_id)) {
                if (!player.isSneaking()) {
                    //if sneaking is required, and player is not sneaking, don't pet
                    return;
                }
            }
            else if (player.isSneaking()) {
                //if sneaking is not required, and the player is sneaking, don't pet
                return;
            }
            if (!(this instanceof Leashable) && !PettingClient.OFFSETS.containsKey(entity_id)) {
                //skip if the entity is hostile and not in the offsets
                //WARN:: might be clientside only for the added offsets. might need to rely on tags for that
                return;
            }
            if (PettingConfig.IGNORED_MOBS.contains(entity_id)) {
                PettingClient.LOGGER.info("petting " + entity_id + " is ignored");
                // TODO:: swap to tags for this?
                return;
            }
            if (!getWorld().isClient) {
                this.last_pet_age = this.age;
                cir.setReturnValue(ActionResult.SUCCESS);
                return;
            }
            //PettingClient.LOGGER.info("trying to pet " + entity_id);

            //runs the custom interactions, if any are present
            //TODO:: re-implement unique interactions, through a data-driven means
            //uniqueInteraction(player, hand);
            this.frog_Petting$getPettingAnimationState().start(this.age);

            Vec3d rotation = this.getRotationVecClient();

            /*double forward_offset = default_forward_offset;
            double vertical_offset = default_vertical_offset;*/
            double forward_offset = 0.0;
            double vertical_offset = 0.5;
            if (PettingClient.OFFSETS.containsKey(entity_id)) {
                forward_offset = PettingClient.OFFSETS.get(entity_id).offset[0];
                vertical_offset = PettingClient.OFFSETS.get(entity_id).offset[1];
            }

            this.getWorld().addParticleClient(ParticleTypes.HEART,
                    this.getX()+Math.random()*.1 + (forward_offset * rotation.getX()),
                    this.getY()+Math.random()*.5 + vertical_offset,
                    this.getZ()+Math.random()*.1 + (forward_offset * rotation.getZ()),
                    0.0D, 0.2D, 0.0D);
            last_pet_age = this.age;

            PettingClient.LOGGER.info("success");
            cir.setReturnValue(ActionResult.SUCCESS);
        }
    }
}
