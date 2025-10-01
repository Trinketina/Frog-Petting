package net.trinketina.frogpetting;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.registry.Registries;
import net.minecraft.resource.ResourceType;
import net.minecraft.sound.SoundEvent;
import net.trinketina.frogpetting.config.PettingConfig;
import net.trinketina.frogpetting.resourcegen.jsondata.PettingOffsetData;
import net.trinketina.frogpetting.resourcegen.PettingResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class PettingClient implements ClientModInitializer {
    public static int cooldown;
    public static final String MOD_ID = "frog_petting";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        LOGGER.info("Croaking Frogs please wait...");
        PettingConfig.registerConfigs();

        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new PettingResourceLoader());


    }
}
