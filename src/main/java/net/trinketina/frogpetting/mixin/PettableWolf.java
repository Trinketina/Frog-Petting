package net.trinketina.frogpetting.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.Tameable;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.WolfEntity;
//import net.minecraft.entity.passive.WolfSoundVariant;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WolfEntity.class)
public abstract class PettableWolf extends PettingMixin implements PettableInterface, Tameable {
    @Unique
    protected double vertical_particle_offset = .4d;
    @Unique
    protected boolean pet_shaking;

    //@Override public boolean uniqueRequirements(PlayerEntity player, Hand hand) {return super.uniqueRequirements(player, hand);}
    @Override public void uniqueInteraction(PlayerEntity player, Hand hand) {
        FrogPettingModClient.LOGGER.info("interact wolf");

        if (PettingConfig.ENABLE_WOLF_UNIQUE && !pet_shaking) {
            pet_shaking = true;
            shakeProgress = .1f;
        }
        SoundEvent wolf_sound;
        if (Math.random() > .7f)
            wolf_sound = SoundEvents.ENTITY_WOLF_AMBIENT; // this.getSoundVariant().value().ambientSound().value();
        else
            wolf_sound = SoundEvents.ENTITY_WOLF_PANT; // this.getSoundVariant().value().pantSound().value();

        if (wolf_sound == null) {
            super.uniqueInteraction(player, hand);
            return;
        }
        this.getWorld().playSoundFromEntity(player, this, wolf_sound, SoundCategory.AMBIENT, this.getSoundVolume(), this.getSoundPitch());
    }
    @Override public double getVerticalOffset() {
        return vertical_particle_offset;
    }
    //@Override public double getForwardOffset() {return horizontal_particle_offset;}
    @Inject(method = "interactMob", at = @At("HEAD"), cancellable = true)
    public void onInteractMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (this.age > last_pet_age + PettingConfig.COOLDOWN*2 && !PettingConfig.IGNORED_MOBS.contains(this.getType().toString())) {

            ActionResult pet_result = super.interactMob(player, hand);
            if (pet_result != ActionResult.SUCCESS)
                return;
            cir.setReturnValue(ActionResult.SUCCESS);
        }
        return;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void onTick(CallbackInfo ci) {
        if (this.pet_shaking) {
            if (this.shakeProgress == 0.0F) {
                this.playSound(SoundEvents.ENTITY_WOLF_SHAKE, this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                //this.emitGameEvent(GameEvent.ENTITY_ACTION);
            }

            this.lastShakeProgress = this.shakeProgress;
            this.shakeProgress += 0.05F;
            if (this.lastShakeProgress >= 2.0F) {
                this.pet_shaking = false;
                this.lastShakeProgress = 0.0F;
                this.shakeProgress = 0.0F;
            }

            if (this.shakeProgress > 0.4F) {
                float f = (float) this.getY();
                int i = (int) (MathHelper.sin((this.shakeProgress - 0.4F) * (float) Math.PI) * 7.0F);
                Vec3d vec3d = this.getVelocity();

                for (int j = 0; j < i; j++) {
                    float g = (this.random.nextFloat() * 2.0F - 1.0F) * this.getWidth() * 0.5F;
                    float h = (this.random.nextFloat() * 2.0F - 1.0F) * this.getWidth() * 0.5F;
                    //this.getWorld().addParticleClient(ParticleTypes.SPLASH, this.getX() + g, f + 0.8F, this.getZ() + h, vec3d.x, vec3d.y, vec3d.z);
                }
            }
        }
    }

    @Shadow private float shakeProgress;

    @Shadow private float lastShakeProgress;
    protected PettableWolf(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }
}
