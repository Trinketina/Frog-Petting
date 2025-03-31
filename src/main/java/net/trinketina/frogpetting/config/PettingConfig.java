package net.trinketina.frogpetting.config;
import com.mojang.datafixers.util.Pair;
import net.trinketina.frogpetting.FrogPettingMod;

public class PettingConfig {
    //SimpleConfig CONFIG = SimpleConfig.of( "petting-config" ).provider( this::provider ).request();
    private String provider( String filename ) {
        return "#Pet more Than Frogs Config\n#cooldown time in ticks:";
    }
    public static int COOLDOWN;
    public static float SLIME_SQUISHINESS;

    public static SimpleConfig CONFIG;
    private static PettingConfigProvider configs;

    public static void registerConfigs() {
        configs = new PettingConfigProvider();
        createConfigs();

        CONFIG = SimpleConfig.of(FrogPettingMod.MOD_ID + "-config").provider(configs).request();

        assignConfigs();
    }

    private static void createConfigs() {
        configs.addKeyValuePair(new Pair<>("cooldown", 5), "petting cooldown in ticks");
        configs.addLine();
        configs.addComment("how squishy is the slime when pet");
        configs.addKeyValuePair(new Pair<>("slime-squishiness", -.8f), "recommended range: -2 to 2");
    }

    private static void assignConfigs() {
        COOLDOWN = CONFIG.getOrDefault("cooldown", 5);
        SLIME_SQUISHINESS = CONFIG.getOrDefault("slime-squishiness", -.8f);
        //System.out.println("All " + configs.getConfigsList().size() + " have been set properly");
    }
}
