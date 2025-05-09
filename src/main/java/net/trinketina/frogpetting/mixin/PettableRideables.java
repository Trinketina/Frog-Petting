package net.trinketina.frogpetting.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.trinketina.frogpetting.PettableInterface;
import net.trinketina.frogpetting.config.PettingConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractHorseEntity.class)
public abstract class PettableRideables extends PettingMixin implements PettableInterface {
    @Unique
    protected double vertical_particle_offset = 1.5d;

    @Override public boolean uniqueRequirements(PlayerEntity player, Hand hand) {return player.isSneaking();}
    //@Override public void uniqueInteraction(PlayerEntity player, Hand hand) {super.uniqueInteraction(player, hand);}

    @Inject(method = "interactMob", at = @At("HEAD"), cancellable = true)
    public void onInteractMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (PettingConfig.ENABLE_RIDEABLE_PETTING && this.age > last_pet_age + PettingConfig.COOLDOWN*2 && !PettingConfig.IGNORED_MOBS.contains(this.getType().toString())) {

            ActionResult pet_result = super.interactMob(player, hand);
            if (pet_result != ActionResult.SUCCESS)
                return;
            cir.setReturnValue(ActionResult.SUCCESS);
            return;
        }
        return;
    }

    @Override public double getVerticalOffset() {
        return vertical_particle_offset;
    }

    protected PettableRideables(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }
}
