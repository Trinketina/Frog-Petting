package net.trinketina.frogpetting.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.Flutterer;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.passive.PandaEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PandaEntity.class)
public abstract class PettablePanda extends PettingMixin implements PettableInterface {
    @Shadow private float rollOverAnimationProgress;
    @Unique
    protected double vertical_particle_offset = 1.5d;

    @Override public void uniqueInteraction(PlayerEntity player, Hand hand) {
        super.uniqueInteraction(player, hand);
    }
    @Override public boolean uniqueRequirements(PlayerEntity player, Hand hand) {return !player.isSneaking();}
    @Override public double getVerticalOffset() {
        return vertical_particle_offset;
    }

    @Inject(method = "interactMob", at = @At("RETURN"), cancellable = true)
    public void onInteractMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (cir.getReturnValue() == ActionResult.SUCCESS) return;

        super.interactMob(player, hand);
        cir.setReturnValue(ActionResult.SUCCESS);
    }


    protected PettablePanda(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }
}
