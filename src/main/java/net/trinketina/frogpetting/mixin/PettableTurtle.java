package net.trinketina.frogpetting.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.trinketina.frogpetting.PettableInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(TurtleEntity.class)
public abstract class PettableTurtle extends PettingMixin implements PettableInterface {
    @Unique
    protected double vertical_particle_offset = .4d;

    @Override public void uniqueInteraction(PlayerEntity player, Hand hand) {
        super.uniqueInteraction(player, hand);
    }
    @Override public boolean uniqueRequirements(PlayerEntity player, Hand hand) {return !player.isSneaking();}
    @Override public double getOffset() {
        return vertical_particle_offset;
    }

    protected PettableTurtle(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }
}
