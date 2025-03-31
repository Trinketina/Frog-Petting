package net.trinketina.frogpetting.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.trinketina.frogpetting.PettableInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ChickenEntity.class)
public abstract class PettableChicken extends PettingMixin implements PettableInterface {
    @Unique
    protected double vertical_particle_offset = .4d;

    //@Override public boolean uniqueRequirements(PlayerEntity player, Hand hand) {return super.uniqueRequirements(player, hand);}
    @Override public void uniqueInteraction(PlayerEntity player, Hand hand) {
        maxWingDeviation = 1f;

        super.uniqueInteraction(player, hand);
    }
    @Override public double getVerticalOffset() {
        return vertical_particle_offset;
    }
    //@Override public double getForwardOffset() {return horizontal_particle_offset;}
    @Shadow public float maxWingDeviation;
    protected PettableChicken(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }
}
