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
    protected float vertical_particle_offset = .4f;

    @Override public void uniqueInteraction() {
        super.uniqueInteraction();
    }
    @Override public boolean uniqueRequirements(PlayerEntity player, Hand hand) {return !player.isSneaking();}
    @Override public float getOffset() {
        return vertical_particle_offset;
    }

    protected PettableTurtle(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }
}
