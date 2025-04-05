package net.trinketina.frogpetting.mixin.compat1_21_0;

import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CamelEntity;
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

@Mixin(CamelEntity.class)
abstract class PettableCamel extends PettingMixin implements PettableInterface {
    @Unique
    protected double vertical_particle_offset = 2.5d;
    @Unique
    protected double vertical_sitting_particle_offset = 1.4d;
    @Unique
    protected double forward_particle_offset = 1.6d;

    //@Override public boolean uniqueRequirements(PlayerEntity player, Hand hand) {return super.uniqueRequirements(player, hand);}
    @Override public void uniqueInteraction(PlayerEntity player, Hand hand) {
        if (PettingConfig.ENABLE_CAMEL_UNIQUE)
            idlingAnimationState.start(this.age - 50);
        super.uniqueInteraction(player, hand);
    }
    @Override public double getVerticalOffset() {
        if (isSitting())
            return vertical_sitting_particle_offset;
        return vertical_particle_offset;
    }
    @Override public double getForwardOffset() {return forward_particle_offset;}

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

    @Shadow public abstract boolean isSitting();
    @Shadow public final AnimationState idlingAnimationState = new AnimationState();
    protected PettableCamel(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }
}
