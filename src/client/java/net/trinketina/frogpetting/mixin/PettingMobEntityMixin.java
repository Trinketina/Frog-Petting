package net.trinketina.frogpetting.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.trinketina.frogpetting.IPettingSound;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(MobEntity.class)
public abstract class PettingMobEntityMixin extends LivingEntity implements IPettingSound {
    @Shadow
    @Nullable
    protected abstract SoundEvent getAmbientSound();

    protected PettingMobEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public SoundEvent frog_Petting$getPettingAmbientSound() {
        return this.getAmbientSound();
    }

    @Override
    public float frog_Petting$getPettingSoundPitch(Random random) {
        return this.getSoundPitch();
    }

    @Override
    public float frog_Petting$getPettingSoundVolume() {
        return this.getSoundVolume();
    }
}
