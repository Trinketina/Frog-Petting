package net.trinketina.frogpetting.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.trinketina.frogpetting.PettableInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PolarBearEntity.class)
public abstract class PettablePolarBear extends PettingMixin implements PettableInterface {
    @Unique
    protected double vertical_particle_offset = 10d;

    @Override public boolean uniqueRequirements(PlayerEntity player, Hand hand) {return super.uniqueRequirements(player, hand);}
    @Override public void uniqueInteraction() {
        this.playSound(SoundEvents.ENTITY_POLAR_BEAR_AMBIENT_BABY);
        //playAmbientSound();
    }
    @Override public double getOffset() {
        return vertical_particle_offset;
    }

    protected PettablePolarBear(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }
}
