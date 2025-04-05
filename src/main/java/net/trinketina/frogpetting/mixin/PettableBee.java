package net.trinketina.frogpetting.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.Flutterer;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.BeeEntity;
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

@Mixin(BeeEntity.class)
public abstract class PettableBee extends PettingMixin implements Angerable, Flutterer, PettableInterface {
    @Unique
    protected double vertical_particle_offset = .4d;

    @Override public void uniqueInteraction(PlayerEntity player, Hand hand) {
        if (PettingConfig.ENABLE_BEE_UNIQUE)
            currentPitch = 2f;
        this.getWorld().playSoundFromEntity(this, SoundEvents.ENTITY_BEE_POLLINATE, SoundCategory.AMBIENT, this.getSoundVolume(), this.getSoundPitch());
    }
    @Override public double getVerticalOffset() {
        return vertical_particle_offset;
    }

    @Shadow private float currentPitch;

    protected PettableBee(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }
}
