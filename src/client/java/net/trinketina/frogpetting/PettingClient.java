package net.trinketina.frogpetting;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import net.trinketina.frogpetting.config.PettingConfig;
import net.trinketina.frogpetting.resourcegen.PettingResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PettingClient implements ClientModInitializer {
    public static final String MOD_ID = "frog_petting";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final KeyMapping.Category PETTING_CATEGORY = KeyMapping.Category.register(Identifier.fromNamespaceAndPath(MOD_ID, "petting"));

    @Override
    public void onInitializeClient() {
        LOGGER.info("Croaking Frogs please wait...");
        PettingConfig.registerConfigs();

        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new PettingResourceLoader());



    }
}
