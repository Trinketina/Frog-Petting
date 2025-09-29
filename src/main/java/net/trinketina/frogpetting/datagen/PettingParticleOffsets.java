package net.trinketina.frogpetting.datagen;

import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec2f;
import net.trinketina.frogpetting.PettingClient;

public class PettingParticleOffsets {
    public static final ComponentType<Vec2f> PETTING_PARTICLE_OFFSET_COMPONENT = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(PettingClient.MOD_ID, "petting_particle_offset"),
            ComponentType.<Vec2f>builder().codec(Vec2f.CODEC).build()
    );
}
