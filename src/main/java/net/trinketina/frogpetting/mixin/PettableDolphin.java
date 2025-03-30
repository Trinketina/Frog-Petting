package net.trinketina.frogpetting.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.Flutterer;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.passive.DolphinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.trinketina.frogpetting.PettableInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(DolphinEntity.class)
public abstract class PettableDolphin extends PettingMixin implements Angerable, Flutterer, PettableInterface {
    @Unique
    protected double vertical_particle_offset = .5d;

    @Override public void uniqueInteraction(PlayerEntity player, Hand hand) {
        this.getWorld().playSoundFromEntityClient(this, SoundEvents.ENTITY_DOLPHIN_PLAY, SoundCategory.AMBIENT, this.getSoundVolume(), this.getSoundPitch());
    }
    @Override public double getOffset() {
        return vertical_particle_offset;
    }

    protected PettableDolphin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }
}
