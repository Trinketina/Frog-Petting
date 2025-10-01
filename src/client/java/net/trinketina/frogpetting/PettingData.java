package net.trinketina.frogpetting;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.sound.SoundEvent;
import net.trinketina.frogpetting.resourcegen.jsondata.PettingOffsetData;

import java.util.HashMap;

@Environment(EnvType.CLIENT)
public class PettingData {
    public static final HashMap<String, Animation> PETTING_ANIMATIONS = new HashMap<>();
    public static final HashMap<String, PettingOffsetData> OFFSETS = new HashMap<>();
}
