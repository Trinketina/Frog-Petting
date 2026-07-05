package net.trinketina.frogpetting;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.KeyMapping;
import net.trinketina.frogpetting.animation.Animation;
import net.trinketina.frogpetting.resourcegen.jsondata.PettingOffsetData;

import java.util.HashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class PettingData {
    public static final Map<String, Animation> PETTING_ANIMATIONS = new HashMap<>();
    public static final Map<String, Animation> BABY_PETTING_ANIMATIONS = new HashMap<>();

    public static final Map<String, PettingOffsetData> OFFSETS = new HashMap<>();

    public static KeyMapping PET_KEYBIND;
}
