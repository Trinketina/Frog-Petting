package net.trinketina.frogpetting.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.Tameable;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.trinketina.frogpetting.PettableInterface;
import net.trinketina.frogpetting.config.PettingConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(CatEntity.class)
public abstract class PettableCat
    extends PettingMixin implements PettableInterface, Tameable {


    @Unique
    protected double vertical_particle_offset = .4d;

    @Override public void uniqueInteraction(PlayerEntity player, Hand hand) {
        if (PettingConfig.ENABLE_CAT_UNIQUE)
            headDownAnimation = 1f;
        //tells the cat to purr
        if (Math.random() > .2f)
            this.getWorld().playSoundFromEntity(this, SoundEvents.ENTITY_CAT_PURR, SoundCategory.AMBIENT, this.getSoundVolume(), this.getSoundPitch());
        else
            this.getWorld().playSoundFromEntity(this, SoundEvents.ENTITY_CAT_PURREOW, SoundCategory.AMBIENT, this.getSoundVolume(), this.getSoundPitch());
    }

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

    @Override public double getVerticalOffset() {
        return vertical_particle_offset;
    }
    @Shadow private float headDownAnimation;
    protected PettableCat(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }
}