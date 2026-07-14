package net.trinketina.frogpetting.mixin;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.trinketina.frogpetting.interfaces.IPettingSound;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Random;

@Mixin(Mob.class)
public abstract class PettingMobMixin extends LivingEntity implements IPettingSound {

    @Shadow
    @Nullable
    protected abstract SoundEvent getAmbientSound();

    protected PettingMobMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public SoundEvent frog_Petting$getPettingSound(String entity_id) {
        return IPettingSound.super.frog_Petting$getPettingSound(entity_id);
    }

    @Override
    public SoundEvent frog_Petting$getPettingAmbientSound() {
        return this.getAmbientSound();
    }

    @Override
    public float frog_Petting$getPettingSoundVolume() {
        return this.getSoundVolume();
    }

    @Override
    public float frog_Petting$getPettingSoundPitch(RandomSource random) {
        return getVoicePitch();
    }
}
