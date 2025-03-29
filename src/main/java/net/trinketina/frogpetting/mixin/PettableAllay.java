package net.trinketina.frogpetting.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.AllayEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.trinketina.frogpetting.PettableInterface;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;


@Mixin(AllayEntity.class)
public abstract class PettableAllay
    extends PettingMixin implements PettableInterface {


    @Unique
    protected float vertical_particle_offset = .4f;

    @Override public void uniqueInteraction() {
        //makes the Allay dance!
        this.setDancing(true);
        super.uniqueInteraction();
    }
    @Override public boolean uniqueRequirements(PlayerEntity player, Hand hand) {
        return !this.isHoldingItem() && super.uniqueRequirements(player, hand);
    }
    @Override public float getOffset() {
        return vertical_particle_offset;
    }


    @Shadow @Final private static TrackedData<Boolean> DANCING;

    @Shadow public boolean isHoldingItem() {
        return !this.getStackInHand(Hand.MAIN_HAND).isEmpty();
    }
    @Shadow public void setDancing(boolean dancing) {}
    protected PettableAllay(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }
}