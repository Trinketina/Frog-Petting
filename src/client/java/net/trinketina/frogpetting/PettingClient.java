package net.trinketina.frogpetting;
import com.mojang.blaze3d.platform.InputConstants;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import net.trinketina.frogpetting.config.PettingConfig;
import net.trinketina.frogpetting.config.PettingConfigData;
import net.trinketina.frogpetting.mixin.GameModeMixin;
import net.trinketina.frogpetting.resourcegen.PettingResourceLoader;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PettingClient implements ClientModInitializer {
    public static final String MOD_ID = "frog_petting";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static KeyMapping.Category PETTING_CATEGORY;

    @Override
    public void onInitializeClient() {
        LOGGER.info("Croaking Frogs please wait...");
        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new PettingResourceLoader());

        //Config Setup
        AutoConfig.register(PettingConfigData.class, GsonConfigSerializer::new);
        PettingConfig.CONFIG = AutoConfig.getConfigHolder(PettingConfigData.class).getConfig();

        //Keybind setup
        PETTING_CATEGORY = KeyMapping.Category.register(Identifier.fromNamespaceAndPath(MOD_ID, "petting"));
        PettingData.PET_KEYBIND = KeyMappingHelper.registerKeyMapping(
                new KeyMapping(
                        "key."+PettingClient.MOD_ID+".pet",
                        InputConstants.Type.KEYSYM,
                        GLFW.GLFW_KEY_F,
                        PettingClient.PETTING_CATEGORY
                )
        );

        //Keybind initialization
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (PettingData.PET_KEYBIND.consumeClick()) {
                PettingClient.LOGGER.info("PRESSED");
                if (client.player instanceof IPettitngInteract pettable) {
                    pettable.TryInteractPet(client);
                }
            }
        });
    }
}
