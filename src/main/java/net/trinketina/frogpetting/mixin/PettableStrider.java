package net.trinketina.frogpetting.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemSteerable;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.StriderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.trinketina.frogpetting.PettableInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(StriderEntity.class)
public abstract class PettableStrider extends PettingMixin implements PettableInterface, ItemSteerable {
    @Unique
    protected double vertical_particle_offset = 1.3d;

    @Override public boolean uniqueRequirements(PlayerEntity player, Hand hand) {
        if (this.hasSaddleEquipped()) {
            return player.isSneaking();
        }
        return super.uniqueRequirements(player, hand);
    }
    //@Override public void uniqueInteraction(PlayerEntity player, Hand hand) {super.uniqueInteraction(player, hand);}
    @Override public double getVerticalOffset() {
        return vertical_particle_offset;
    }
    //@Override public double getForwardOffset() {return horizontal_particle_offset;}
    protected PettableStrider(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }
}
