package net.trinketina.frogpetting;

import net.fabricmc.api.ModInitializer;
import net.trinketina.frogpetting.config.PettingConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FrogPettingMod implements ModInitializer {
	public static final String MOD_ID = "petting-frogs";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		PettingConfig.registerConfigs();
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		//LOGGER.info("Croaking Frogs please wait...");


	}
}