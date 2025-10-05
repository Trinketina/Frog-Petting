package net.trinketina.frogpetting.config;
import com.mojang.datafixers.util.Pair;
import net.trinketina.frogpetting.PettingMain;

import java.util.Arrays;
import java.util.List;

public class PettingConfig {
    //SimpleConfig CONFIG = SimpleConfig.of( "petting-config" ).provider( this::provider ).request();
    private String provider( String filename ) {
        return "#Pet more Than Frogs Config\n#cooldown time in ticks:";
    }
    public static int COOLDOWN;
    public static float SLIME_SQUISHINESS;

    public static boolean ENABLE_RIDEABLE_PETTING;
    public static boolean ENABLE_ALLAY_UNIQUE;
    public static boolean ENABLE_BEE_UNIQUE;
    public static boolean ENABLE_CAMEL_UNIQUE;
    public static boolean ENABLE_CAT_UNIQUE;
    public static boolean ENABLE_CHICKEN_UNIQUE;
    public static boolean ENABLE_FROG_UNIQUE;
    public static boolean ENABLE_GOAT_UNIQUE;
    public static boolean ENABLE_PARROT_UNIQUE;
    public static boolean ENABLE_RABBIT_UNIQUE;
    public static boolean ENABLE_SLIME_UNIQUE;
    public static boolean ENABLE_SNIFFER_UNIQUE;
    public static boolean ENABLE_WOLF_UNIQUE;
    public static List<String> IGNORED_MOBS;

    public static SimpleConfig CONFIG;
    private static PettingConfigProvider configs;

    public static void registerConfigs() {
        configs = new PettingConfigProvider();
        createConfigs();

        CONFIG = SimpleConfig.of(PettingMain.MOD_ID + "-config").provider(configs).request();

        assignConfigs();
    }

    private static void createConfigs() {
        configs.addKeyValuePair(new Pair<>("cooldown", 10), "petting cooldown in ticks");
        configs.addLine();
        configs.addLine();
        configs.addSeparator();
        //courtesy of erengeez
        configs.addSeparator();
        configs.addComment("Disable interactions for some mobs");
        configs.addSeparator();
        configs.addKeyValuePair(new Pair<>("ignored-mobs", "\"\""), "ignored mobs separated by commas (ex: \"entity.minecraft.wolf, entity.minecraft.parrot\")");
        //--
    }

    private static void assignConfigs() {
        COOLDOWN = CONFIG.getOrDefault("cooldown", 5);

        IGNORED_MOBS = Arrays.asList(CONFIG.getOrDefault("ignored-mobs","\"\"").split("\\s*,\\s*"));
        //System.out.println("All " + configs.getConfigsList().size() + " have been set properly");
    }
}
