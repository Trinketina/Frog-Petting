package net.trinketina.frogpetting.mixin.compat1215;

import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.SnifferEntity;
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

@Mixin(SnifferEntity.class)
public abstract class PettableSniffer extends PettingMixin implements PettableInterface {
    @Unique
    protected double vertical_particle_offset = 1.4d;
    @Unique
    protected double forward_particle_offset = 1.8d;

    //@Override public boolean uniqueRequirements(PlayerEntity player, Hand hand) {return super.uniqueRequirements(player, hand);}
    @Override public void uniqueInteraction(PlayerEntity player, Hand hand) {
        if (PettingConfig.ENABLE_SNIFFER_UNIQUE)
            this.sniffingAnimationState.start(this.age);
        this.getWorld().playSoundFromEntity(this, SoundEvents.ENTITY_SNIFFER_HAPPY, SoundCategory.AMBIENT, this.getSoundVolume(), this.getSoundPitch());
    }
    @Override public double getVerticalOffset() {
        return vertical_particle_offset;
    }
    @Override public double getForwardOffset() {return forward_particle_offset;}

    @Shadow public final AnimationState sniffingAnimationState = new AnimationState();

    protected PettableSniffer(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }
}
