package net.trinketina.frogpetting.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.World;
import net.trinketina.frogpetting.PettableInterface;
import org.spongepowered.asm.mixin.Unique;

/*@Mixin(.class)*/
public abstract class BasicPettable extends PettingMixin implements PettableInterface {
    @Unique
    protected double vertical_particle_offset = .4d;

    //@Override public boolean uniqueRequirements(PlayerEntity player, Hand hand) {return super.uniqueRequirements(player, hand);}
    //@Override public void uniqueInteraction(PlayerEntity player, Hand hand) {super.uniqueInteraction(player, hand);}

    protected BasicPettable(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }
}
