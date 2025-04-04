package net.trinketina.frogpetting;
import net.fabricmc.api.ClientModInitializer;
import net.trinketina.frogpetting.config.PettingConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FrogPettingModClient implements ClientModInitializer {
    public static int cooldown;
    public static final String MOD_ID = "petting-frogs";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        PettingConfig.registerConfigs();
        LOGGER.info("Croaking Frogs please wait...");
    }
}
