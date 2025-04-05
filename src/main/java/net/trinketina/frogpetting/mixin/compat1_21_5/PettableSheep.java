package net.trinketina.frogpetting.mixin.compat1_21_5;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.trinketina.frogpetting.PettableInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(SheepEntity.class)
public abstract class PettableSheep extends PettingMixin implements PettableInterface {
    @Unique
    protected double vertical_particle_offset = 1.2d;
    @Unique
    protected double forward_particle_offset = .2d;

    //@Override public boolean uniqueRequirements(PlayerEntity player, Hand hand) {return super.uniqueRequirements(player, hand);}
    @Override public void uniqueInteraction(PlayerEntity player, Hand hand) {super.uniqueInteraction(player, hand);}
    @Override public double getVerticalOffset() {
        return vertical_particle_offset;
    }
    @Override public double getForwardOffset() {
        return forward_particle_offset;
    }

    protected PettableSheep(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }
}
