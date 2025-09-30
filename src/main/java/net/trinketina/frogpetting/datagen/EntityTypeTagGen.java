package net.trinketina.frogpetting.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.trinketina.frogpetting.PettingMain;

import java.util.concurrent.CompletableFuture;

public class EntityTypeTagGen extends FabricTagProvider.EntityTypeTagProvider {

    public static final TagKey<EntityType<?>> ALLOW_PETTING = TagKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(PettingMain.MOD_ID, "allow_petting"));
    public static final TagKey<EntityType<?>> BLOCK_PETTING = TagKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(PettingMain.MOD_ID, "block_petting"));



    public EntityTypeTagGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ALLOW_PETTING)
                .add(EntityType.PLAYER)
                .add(EntityType.SLIME);
        getOrCreateTagBuilder(BLOCK_PETTING);
    }
}
