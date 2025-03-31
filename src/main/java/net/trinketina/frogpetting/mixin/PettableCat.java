package net.trinketina.frogpetting.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.trinketina.frogpetting.PettableInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;


@Mixin(CatEntity.class)
public abstract class PettableCat
    extends PettingMixin implements PettableInterface {


    @Unique
    protected double vertical_particle_offset = .4d;

    @Override public void uniqueInteraction(PlayerEntity player, Hand hand) {
        //tells the cat to purr
        if (Math.random() > .2f)
            this.getWorld().playSoundFromEntityClient(this, SoundEvents.ENTITY_CAT_PURR, SoundCategory.AMBIENT, this.getSoundVolume(), this.getSoundPitch());
        else
            this.getWorld().playSoundFromEntityClient(this, SoundEvents.ENTITY_CAT_PURREOW, SoundCategory.AMBIENT, this.getSoundVolume(), this.getSoundPitch());
    }
    @Override public double getVerticalOffset() {
        return vertical_particle_offset;
    }

    protected PettableCat(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }
}