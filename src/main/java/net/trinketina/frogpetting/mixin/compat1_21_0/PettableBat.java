package net.trinketina.frogpetting.mixin.compat1_21_0;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.trinketina.frogpetting.PettableInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BatEntity.class)
public abstract class PettableBat extends PettingMixin implements PettableInterface {
    @Unique
    protected double vertical_particle_offset = .3d;

    @Override public boolean uniqueRequirements(PlayerEntity player, Hand hand) {
        return !player.isSneaking();
    }
    //@Override public void uniqueInteraction(PlayerEntity player, Hand hand) {super.uniqueInteraction(player, hand);}
    @Override public double getVerticalOffset() {
        return vertical_particle_offset;
    }
    //@Override public double getForwardOffset() {return horizontal_particle_offset;}
    protected PettableBat(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }
}
