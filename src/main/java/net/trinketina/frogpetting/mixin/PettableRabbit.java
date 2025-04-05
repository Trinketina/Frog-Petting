package net.trinketina.frogpetting.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.RabbitEntity;
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

@Mixin(RabbitEntity.class)
public abstract class PettableRabbit extends PettingMixin implements PettableInterface {
    @Unique
    protected double vertical_particle_offset = .4d;
    @Unique
    protected float jump_pos = 0;

    //@Override public boolean uniqueRequirements(PlayerEntity player, Hand hand) {return super.uniqueRequirements(player, hand);}
    @Override public void uniqueInteraction(PlayerEntity player, Hand hand) {
        if (PettingConfig.ENABLE_RABBIT_UNIQUE)
            this.startJump();
        this.getWorld().playSoundFromEntity(player, this, SoundEvents.ENTITY_RABBIT_JUMP, SoundCategory.AMBIENT, this.getSoundVolume(), this.getSoundPitch());
    }
    @Override public double getVerticalOffset() {
        return vertical_particle_offset;
    }

    @Shadow public abstract void startJump();;

    protected PettableRabbit(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }
}
