package net.trinketina.frogpetting.mixin;

import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.passive.WolfSoundVariant;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.trinketina.frogpetting.PettableInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(WolfEntity.class)
public abstract class PettableWolf extends PettingMixin implements PettableInterface {
    @Unique
    protected double vertical_particle_offset = .4d;

    //@Override public boolean uniqueRequirements(PlayerEntity player, Hand hand) {return super.uniqueRequirements(player, hand);}
    @Override public void uniqueInteraction(PlayerEntity player, Hand hand) {
        SoundEvent wolf_sound;
        setBegging(true);
        if (Math.random() > .7f)
            wolf_sound = this.getSoundVariant().value().ambientSound().value();
        else
            wolf_sound = this.getSoundVariant().value().pantSound().value();

        if (wolf_sound == null) {
            super.uniqueInteraction(player, hand);
            return;
        }
        this.getWorld().playSoundFromEntityClient(this, wolf_sound, SoundCategory.AMBIENT, this.getSoundVolume(), this.getSoundPitch());
    }
    @Override public double getVerticalOffset() {
        return vertical_particle_offset;
    }
    //@Override public double getForwardOffset() {return horizontal_particle_offset;}

    @Shadow private RegistryEntry<WolfSoundVariant> getSoundVariant() {return null;}
    @Shadow public void setBegging(boolean begging) {}
    /*@Shadow private boolean furWet;

    @Shadow private float shakeProgress;*/
    protected PettableWolf(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }
}
