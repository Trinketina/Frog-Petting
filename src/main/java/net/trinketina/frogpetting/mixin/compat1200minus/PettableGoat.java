package net.trinketina.frogpetting.mixin.compat1200minus;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.Flutterer;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.passive.AnimalEntity;

import net.minecraft.entity.passive.GoatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.trinketina.frogpetting.PettableInterface;
import net.trinketina.frogpetting.config.PettingConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(GoatEntity.class)
public abstract class PettableGoat extends PettingMixin implements Angerable, Flutterer, PettableInterface {
    @Unique
    protected double vertical_particle_offset = 1d;

    @Override public void uniqueInteraction(PlayerEntity player, Hand hand) {
        //preparingRam = true;
        if (PettingConfig.ENABLE_GOAT_UNIQUE)
            headPitch = 10;
        super.uniqueInteraction(player, hand);
    }
    @Override public double getVerticalOffset() {
        return vertical_particle_offset;
    }

    @Shadow private boolean preparingRam;
    @Shadow private int headPitch;

    protected PettableGoat(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }
}
