package net.trinketina.frogpetting.mixin.compat1215;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.trinketina.frogpetting.PettableInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(OcelotEntity.class)
public abstract class PettableOcelot extends PettingMixin implements PettableInterface {
    @Unique
    protected double vertical_particle_offset = .4d;

    //@Override public boolean uniqueRequirements(PlayerEntity player, Hand hand) {return super.uniqueRequirements(player, hand);}
    @Override public void uniqueInteraction(PlayerEntity player, Hand hand) {
        //tells the cat to purr
        if (Math.random() > .2f)
            this.getWorld().playSoundFromEntity(this, SoundEvents.ENTITY_CAT_PURR, SoundCategory.AMBIENT, this.getSoundVolume(), this.getSoundPitch());
        else
            this.getWorld().playSoundFromEntity(this, SoundEvents.ENTITY_CAT_PURREOW, SoundCategory.AMBIENT, this.getSoundVolume(), this.getSoundPitch());    }
    @Override public double getVerticalOffset() {
        return vertical_particle_offset;
    }
    //@Override public double getForwardOffset() {return horizontal_particle_offset;}
    protected PettableOcelot(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }
}
