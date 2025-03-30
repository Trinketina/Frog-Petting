package net.trinketina.frogpetting.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.Flutterer;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import net.trinketina.frogpetting.PettableInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BeeEntity.class)
public abstract class PettableBee extends PettingMixin implements Angerable, Flutterer, PettableInterface {
    @Unique
    protected float vertical_particle_offset = .4f;

    @Override public void uniqueInteraction() {
        this.playSound(SoundEvents.ENTITY_BEE_POLLINATE, 1.0F, 1.0F);
    }
    @Override public float getOffset() {
        return vertical_particle_offset;
    }

    protected PettableBee(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }
}
