package net.trinketina.frogpetting.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.Shearable;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.trinketina.frogpetting.PettableInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SnowGolemEntity.class)
public abstract class PettableSnowGolem extends PettingMixin implements PettableInterface, Shearable {
    @Unique
    protected double vertical_particle_offset = 1.9d;
    @Unique
    protected double vertical_sheared_particle_offset = 1.65d;

    @Override public boolean uniqueRequirements(PlayerEntity player, Hand hand) {return !player.isSneaking();}
    //@Override public void uniqueInteraction(PlayerEntity player, Hand hand) {super.uniqueInteraction(player, hand);}
    @Override public double getVerticalOffset() {
        if (isShearable())
            return vertical_particle_offset;
        return vertical_sheared_particle_offset;
    }
    //@Override public double getForwardOffset() {return horizontal_particle_offset;}

    @Inject(method = "interactMob", at = @At("RETURN"), cancellable = true)
    public void onInteractMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (cir.getReturnValue() != ActionResult.PASS) return;

        super.interactMob(player, hand);
        cir.setReturnValue(ActionResult.SUCCESS);
        return;
    }

    protected PettableSnowGolem(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }
}
