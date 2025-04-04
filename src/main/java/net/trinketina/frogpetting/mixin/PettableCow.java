package net.trinketina.frogpetting.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AbstractCowEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.trinketina.frogpetting.PettableInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(AbstractCowEntity.class)
public abstract class PettableCow extends PettingMixin implements PettableInterface {
    @Unique
    protected double vertical_particle_offset = 1.5d;
    @Unique
    protected double forward_particle_offset = .3d;

    @Override public void uniqueInteraction(PlayerEntity player, Hand hand) {
        super.uniqueInteraction(player, hand);
    }
    @Override public double getVerticalOffset() {
        return vertical_particle_offset;
    }
    @Override public double getForwardOffset() {
        return forward_particle_offset;
    }

    protected PettableCow(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }
}
