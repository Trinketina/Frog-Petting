package net.trinketina.frogpetting.mixin.compat1_21_0;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.trinketina.frogpetting.PettableInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PolarBearEntity.class)
public abstract class PettablePolarBear extends PettingMixin implements PettableInterface {
    @Unique
    protected double vertical_particle_offset = 1.5d;
    @Unique
    protected double forward_particle_offset = 1d;

    @Override public boolean uniqueRequirements(PlayerEntity player, Hand hand) {return super.uniqueRequirements(player, hand);}
    @Override public void uniqueInteraction(PlayerEntity player, Hand hand) {
        this.getWorld().playSoundFromEntity(this, SoundEvents.ENTITY_POLAR_BEAR_AMBIENT_BABY, SoundCategory.AMBIENT, this.getSoundVolume(), this.getSoundPitch());
    }
    @Override public double getVerticalOffset() {
        return vertical_particle_offset;
    }
    @Override public double getForwardOffset() {
        return forward_particle_offset;
    }

    protected PettablePolarBear(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }
}
