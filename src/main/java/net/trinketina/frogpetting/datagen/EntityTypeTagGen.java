package net.trinketina.frogpetting.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.TagKey;
import net.trinketina.frogpetting.PettingMain;

import java.util.concurrent.CompletableFuture;

public class EntityTypeTagGen extends FabricTagsProvider.EntityTypeTagsProvider {

    public static final TagKey<EntityType<?>> ALLOW_PETTING = TagKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(PettingMain.MOD_ID, "allow_petting"));
    public static final TagKey<EntityType<?>> BLOCK_PETTING = TagKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(PettingMain.MOD_ID, "block_petting"));

    public EntityTypeTagGen(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
        super(output, registryLookupFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        valueLookupBuilder(ALLOW_PETTING)
                .add(EntityType.PLAYER)
                .add(EntityType.SLIME);
        valueLookupBuilder(BLOCK_PETTING);
    }
}
