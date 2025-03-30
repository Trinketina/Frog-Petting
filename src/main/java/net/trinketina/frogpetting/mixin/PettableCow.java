package net.trinketina.frogpetting.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.Flutterer;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.passive.AbstractCowEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.world.World;
import net.trinketina.frogpetting.PettableInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(AbstractCowEntity.class)
public abstract class PettableCow extends PettingMixin implements PettableInterface {
    @Unique
    protected float vertical_particle_offset = 1.4f;

    @Override public void uniqueInteraction() {
        super.uniqueInteraction();
    }
    @Override public float getOffset() {
        return vertical_particle_offset;
    }

    protected PettableCow(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }
}
