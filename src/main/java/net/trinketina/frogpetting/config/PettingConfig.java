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
        configs.addLine();
        configs.addKeyValuePair(new Pair<>("rideable-shift-petting", true), "enable/disable rideable mob petting");
        configs.addLine();
        configs.addSeparator();
        configs.addComment("Enable or Disable entity-specific unique interactions");
        configs.addSeparator();
        configs.addKeyValuePair(new Pair<>("allay-unique", true), "allay twirl");
        configs.addKeyValuePair(new Pair<>("bee-unique", true), "bee spin");
        configs.addKeyValuePair(new Pair<>("camel-unique", true), "camel ear wiggle");
        configs.addKeyValuePair(new Pair<>("cat-unique", true), "cat nod");
        configs.addKeyValuePair(new Pair<>("chicken-unique", true), "chicken wing flap");
        configs.addKeyValuePair(new Pair<>("frog-unique", true), "frog croak");
        configs.addKeyValuePair(new Pair<>("goat-unique", true), "goat nod");
        configs.addKeyValuePair(new Pair<>("parrot-unique", true), "parrot wing flap");
        configs.addKeyValuePair(new Pair<>("rabbit-unique", true), "rabbit kick");
        configs.addKeyValuePair(new Pair<>("slime-unique", true), "slime squish");
        configs.addKeyValuePair(new Pair<>("sniffer-unique", true), "sniffer sniff");
        configs.addKeyValuePair(new Pair<>("wolf-unique", true), "wolf shake");

    }

    private static void assignConfigs() {
        COOLDOWN = CONFIG.getOrDefault("cooldown", 5);
        SLIME_SQUISHINESS = CONFIG.getOrDefault("slime-squishiness", -.8f);
        ENABLE_RIDEABLE_PETTING = CONFIG.getOrDefault("rideable-shift-petting", true);
        ENABLE_ALLAY_UNIQUE = CONFIG.getOrDefault("allay-unique", true);
        ENABLE_BEE_UNIQUE = CONFIG.getOrDefault("bee-unique", true);
        ENABLE_CAMEL_UNIQUE = CONFIG.getOrDefault("camel-unique", true);
        ENABLE_CAT_UNIQUE = CONFIG.getOrDefault("cat-unique", true);
        ENABLE_CHICKEN_UNIQUE = CONFIG.getOrDefault("chicken-unique", true);
        ENABLE_FROG_UNIQUE = CONFIG.getOrDefault("frog-unique", true);
        ENABLE_PARROT_UNIQUE = CONFIG.getOrDefault("parrot-unique", true);
        ENABLE_GOAT_UNIQUE = CONFIG.getOrDefault("goat-unique", true);
        ENABLE_RABBIT_UNIQUE = CONFIG.getOrDefault("rabbit-unique", true);
        ENABLE_SLIME_UNIQUE = CONFIG.getOrDefault("slime-unique", true);
        ENABLE_SNIFFER_UNIQUE = CONFIG.getOrDefault("sniffer-unique", true);
        ENABLE_WOLF_UNIQUE = CONFIG.getOrDefault("wolf-unique", true);
        //System.out.println("All " + configs.getConfigsList().size() + " have been set properly");
    }
}
