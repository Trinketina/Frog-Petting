package net.trinketina.frogpetting.mixin.compat1212;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.trinketina.frogpetting.PettableInterface;
import net.trinketina.frogpetting.config.PettingConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(SlimeEntity.class)
public abstract class PettableSlime extends PettingMixin implements PettableInterface, Monster {
    @Unique
    protected double vertical_particle_offset = .4d;

    @Override public boolean uniqueRequirements(PlayerEntity player, Hand hand) {
        return !player.isSneaking() && this.isSmall();
    }
    @Override public void uniqueInteraction(PlayerEntity player, Hand hand) {
        if (PettingConfig.ENABLE_SLIME_UNIQUE) {
            this.targetStretch = PettingConfig.SLIME_SQUISHINESS;
        }
        this.getWorld().playSoundFromEntity(this, SoundEvents.ENTITY_SLIME_SQUISH, SoundCategory.AMBIENT, this.getSoundVolume(), this.getSoundPitch());
    }
    @Override public double getVerticalOffset() {
        return vertical_particle_offset;
    }

    @Shadow public abstract boolean isSmall();
    @Shadow public float targetStretch;

    protected PettableSlime(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }
}
