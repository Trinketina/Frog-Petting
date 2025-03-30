package net.trinketina.frogpetting.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.Flutterer;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.trinketina.frogpetting.PettableInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

/*@Mixin(.class)*/
public abstract class BasicPettable extends PettingMixin implements PettableInterface {
    @Unique
    protected float vertical_particle_offset = .4f;

    @Override public boolean uniqueRequirements(PlayerEntity player, Hand hand) {return super.uniqueRequirements(player, hand);}
    @Override public void uniqueInteraction() {
        super.uniqueInteraction();
    }
    @Override public float getOffset() {
        return vertical_particle_offset;
    }

    protected BasicPettable(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }
}
