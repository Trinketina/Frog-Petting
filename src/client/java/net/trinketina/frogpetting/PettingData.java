package net.trinketina.frogpetting;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.animation.KeyframeAnimations;
import net.trinketina.frogpetting.resourcegen.jsondata.PettingOffsetData;

import java.util.HashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class PettingData {
    public static final Map<String, AnimationDefinition> PETTING_ANIMATIONS = new HashMap<>();
    public static final Map<String, KeyframeAnimation> PETTING_KEYFRAMES = new HashMap<>();
    public static final Map<String, PettingOffsetData> OFFSETS = new HashMap<>();
}
