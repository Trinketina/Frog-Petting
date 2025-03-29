package net.trinketina.frogpetting.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import net.trinketina.frogpetting.PettableInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;


@Mixin(CatEntity.class)
public abstract class PettableCat
    extends PettingMixin implements PettableInterface {


    @Unique
    protected float vertical_particle_offset = .4f;

    @Override public void uniqueInteraction() {
        //tells the cat to purr
        if (Math.random() > .2f)
            this.playSound(SoundEvents.ENTITY_CAT_PURR);
        else
            this.playSound(SoundEvents.ENTITY_CAT_PURREOW);
    }
    @Override public float getOffset() {
        return vertical_particle_offset;
    }

    protected PettableCat(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }
}