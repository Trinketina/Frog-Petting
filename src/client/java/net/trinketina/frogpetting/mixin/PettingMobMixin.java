package net.trinketina.frogpetting.mixin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.trinketina.frogpetting.IPettingSound;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Mob.class)
public abstract class PettingMobMixin extends LivingEntity implements IPettingSound {
    @Shadow
    @Nullable
    protected abstract SoundEvent getAmbientSound();

    protected PettingMobMixin(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    public SoundEvent frog_Petting$getPettingAmbientSound() {
        return this.getAmbientSound();
    }

    @Override
    public float frog_Petting$getPettingSoundPitch(RandomSource random) {
        return this.getVoicePitch();
    }

    @Override
    public float frog_Petting$getPettingSoundVolume() {
        return this.getSoundVolume();
    }
}
