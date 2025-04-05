package net.trinketina.frogpetting.mixin.compat1200minus;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.passive.AllayEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.trinketina.frogpetting.PettableInterface;
import net.trinketina.frogpetting.config.PettingConfig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(AllayEntity.class)
public abstract class PettableAllay
    extends PettingMixin implements PettableInterface {


    @Unique
    protected double vertical_particle_offset = .4d;
    @Unique
    protected boolean pettedDancing = false;

    @Override public void uniqueInteraction(PlayerEntity player, Hand hand) {
        //makes the Allay dance!
        if (PettingConfig.ENABLE_ALLAY_UNIQUE) {
            pettedDancing = true;
            this.dataTracker.set(DANCING, pettedDancing);
        }
        super.uniqueInteraction(player, hand);
    }
    @Override public boolean uniqueRequirements(PlayerEntity player, Hand hand) {
        return !this.isHoldingItem() && super.uniqueRequirements(player, hand);
    }
    @Override public double getVerticalOffset() {
        return vertical_particle_offset;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void onTick(CallbackInfo ci) {
        if(this.getWorld().isClient && this.age > this.last_pet + 10) {
            pettedDancing = false;
            this.dataTracker.set(DANCING, pettedDancing);
        }
    }


    @Shadow @Final private static TrackedData<Boolean> DANCING;

    @Shadow public abstract boolean isHoldingItem();
    @Shadow public abstract void setDancing(boolean dancing);
    protected PettableAllay(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }
}