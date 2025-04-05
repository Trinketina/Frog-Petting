package net.trinketina.frogpetting.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.jetbrains.annotations.Nullable;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.SemanticVersion;
import net.fabricmc.loader.api.Version;

public class VersionUtils
{
    private static final List<Predicate<String>> VERSION_PREDICATES = new ArrayList<>();

    static
    {
        final int[][] ranges =
                {
                        {1, 20, 1},
                        {1, 21, 0},
                        {1, 21, 5},
                };

        final String prefix = ".compat%s_%s_";

        for (final int[] range : ranges)
        {
            final int major = range[0];
            final int minor = range[1];
            //final int patch = range[2];

            final String predicatePrefix = String.format(prefix, major, minor);
            final String predicateMinor = predicatePrefix + ".";
            final String predicateMinorPlus = predicatePrefix + "plus.";
            final String predicateMinorMinus = predicatePrefix + "minus.";
            final String predicateMinorZero = predicatePrefix + "0.";
            final String predicateMinorZeroMinus = predicatePrefix + "0minus.";

            VERSION_PREDICATES.add(n -> n.contains(predicateMinor) && VersionUtils.MINOR != minor);
            VERSION_PREDICATES.add(n -> n.contains(predicateMinorPlus) && VersionUtils.MINOR < minor);
            VERSION_PREDICATES.add(n -> n.contains(predicateMinorMinus) && VersionUtils.MINOR > minor);
            VERSION_PREDICATES.add(n -> n.contains(predicateMinorZero) && (VersionUtils.MINOR != minor || VersionUtils.PATCH != 0));
            VERSION_PREDICATES.add(n -> n.contains(predicateMinorZeroMinus) && (VersionUtils.MINOR > minor || (VersionUtils.MINOR == minor && VersionUtils.PATCH > 0)));

            final int maxPatch = range[2];

            for (int i = 1; i <= maxPatch; i++)
            {
                final int patch = i;

                final String predicatePatch = predicatePrefix + patch + ".";
                final String predicatePatchPlus = predicatePrefix + patch + "plus.";
                final String predicatePatchMinus = predicatePrefix + patch + "minus.";

                VERSION_PREDICATES.add(n -> n.contains(predicatePatch) && (VersionUtils.MINOR != minor || VersionUtils.PATCH != patch));
                VERSION_PREDICATES.add(n -> n.contains(predicatePatchPlus) && (VersionUtils.MINOR < minor || (VersionUtils.MINOR == minor && VersionUtils.PATCH < patch)));
                VERSION_PREDICATES.add(n -> n.contains(predicatePatchMinus) && (VersionUtils.MINOR > minor || (VersionUtils.MINOR == minor && VersionUtils.PATCH > patch)));
            }
        }
    }

    public static boolean shouldApplyCompatibilityMixin(String mixinClassName)
    {
        if (mixinClassName.contains(".compat"))
        {
            for (final Predicate<String> predicate : VERSION_PREDICATES)
            {
                if (predicate.test(mixinClassName))
                {
                    return false;
                }
            }
        }

        return true;
    }

    @Nullable
    public static final SemanticVersion MINECRAFT_VERSION = lookupMinecraftVersion();
    public static final int MAJOR = getVersionComponent(0);
    public static final int MINOR = getVersionComponent(1);
    public static final int PATCH = getVersionComponent(2);

    private static SemanticVersion lookupMinecraftVersion()
    {
        final Version version = FabricLoader.getInstance().getModContainer("minecraft").get().getMetadata().getVersion();

        return (SemanticVersion) (version instanceof SemanticVersion ? version : null);
    }

    private static int getVersionComponent(int pos)
    {
        return MINECRAFT_VERSION != null ? MINECRAFT_VERSION.getVersionComponent(pos) : -1;
    }
}
