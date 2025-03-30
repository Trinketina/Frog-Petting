package net.trinketina.frogpetting.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.Flutterer;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.passive.AnimalEntity;

import net.minecraft.entity.passive.GoatEntity;
import net.minecraft.world.World;
import net.trinketina.frogpetting.PettableInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(GoatEntity.class)
public abstract class PettableGoat extends PettingMixin implements Angerable, Flutterer, PettableInterface {
    @Unique
    protected double vertical_particle_offset = 1d;

    @Override public void uniqueInteraction() {
        super.uniqueInteraction();
    }
    @Override public double getOffset() {
        return vertical_particle_offset;
    }

    protected PettableGoat(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }
}
