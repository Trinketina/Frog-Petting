package net.trinketina.frogpetting;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.animation.KeyframeAnimations;
import net.trinketina.frogpetting.animation.Animation;
import net.trinketina.frogpetting.resourcegen.jsondata.PettingOffsetData;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class PettingData {
    public static final Map<String, Animation> PETTING_ANIMATIONS = new HashMap<>();
    public static final Map<String, Animation> BABY_PETTING_ANIMATIONS = new HashMap<>();

    public static final Map<String, PettingOffsetData> OFFSETS = new HashMap<>();

    public static KeyMapping pettingKeybind = KeyMappingHelper.registerKeyMapping(
            new KeyMapping(
                    "key."+PettingClient.MOD_ID+".pet",
                    InputConstants.Type.KEYSYM,
                    GLFW.GLFW_KEY_F,
                    PettingClient.PETTING_CATEGORY
            )
    );
}
