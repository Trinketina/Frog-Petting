package net.trinketina.frogpetting.mixin.compat1_21_5;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.trinketina.frogpetting.FrogPettingModClient;
import net.trinketina.frogpetting.PettableInterface;
import net.trinketina.frogpetting.config.PettingConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PettablePlayers extends LivingEntity implements PettableInterface {
    @Unique
    protected int last_pet = -100;
    @Unique
    protected double vertical_particle_offset = 1.6d;

    //@Override public boolean uniqueRequirements(PlayerEntity player, Hand hand) {return ;}
    @Override public void uniqueInteraction(PlayerEntity player, Hand hand) {}
    @Override public double getVerticalOffset() {
        return vertical_particle_offset;
    }
    @Override public double getForwardOffset() {return default_forward_offset;}

    @Inject(method = "interact", at = @At("HEAD"), cancellable = true)
    public void onInteract(Entity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        ItemStack itemStack = this.getStackInHand(hand);
        if (entity.isPlayer() && itemStack.isEmpty() && !this.isSpectator() && !this.isSneaking()) {
            FrogPettingModClient.LOGGER.info("trying to pet " + this.getType().toString());
            if (this.age < last_pet + PettingConfig.COOLDOWN) {
                return;
            }
            /*if (!getWorld().isClient) {
                FrogPettingModClient.LOGGER.info("on server");
                this.last_pet = this.age;
                cir.setReturnValue(ActionResult.SUCCESS);
                return;
            }*/

            Vec3d rotation = entity.getRotationVecClient();
            entity.getWorld().addParticleClient(ParticleTypes.HEART,
                    entity.getX()+Math.random()*.1 + (getForwardOffset() * rotation.getX()),
                    entity.getY()+Math.random()*.5 + getVerticalOffset(),
                    entity.getZ()+Math.random()*.1 + (getForwardOffset() * rotation.getZ()),
                    0.0D, 0.2D, 0.0D);

            this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_BREEDING_PARTICLES);
            last_pet = this.age;

            FrogPettingModClient.LOGGER.info("success");
        }
    }

    @Shadow public abstract boolean isSpectator();
    protected PettablePlayers(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }
}
