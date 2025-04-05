package net.trinketina.frogpetting.mixin.compat1_21_5;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.entity.player.PlayerEntity;
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

@Mixin(ParrotEntity.class)
public abstract class PettableParrot extends PettingMixin implements PettableInterface {
    @Unique
    protected double vertical_particle_offset = .4d;

    @Override public boolean uniqueRequirements(PlayerEntity player, Hand hand) {return player.isSneaking();}
    @Override public void uniqueInteraction(PlayerEntity player, Hand hand) {
        if (PettingConfig.ENABLE_PARROT_UNIQUE) {
            this.flapProgress = 0;
            this.flapSpeed = 1;
            this.maxWingDeviation = 1;
        }
        super.uniqueInteraction(player, hand);
    }
    @Override public double getVerticalOffset() {
        return vertical_particle_offset;
    }
    //@Override public double getForwardOffset() {return horizontal_particle_offset;}

    @Inject(method = "interactMob", at = @At("HEAD"), cancellable = true)
    public void onInteractMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (this.age > last_pet + PettingConfig.COOLDOWN*2 ) {

            ActionResult pet_result = super.interactMob(player, hand);
            if (pet_result != ActionResult.SUCCESS)
                return;
            cir.setReturnValue(ActionResult.SUCCESS);
        }
        return;
    }

    @Shadow public float flapProgress;

    @Shadow private float flapSpeed;

    @Shadow public float maxWingDeviation;

    protected PettableParrot(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }
}
