package net.trinketina.frogpetting.resourcegen;

import com.nimbusds.jose.shaded.gson.JsonElement;
import com.nimbusds.jose.shaded.gson.JsonObject;
import com.nimbusds.jose.shaded.gson.JsonParser;
import net.trinketina.frogpetting.resourcegen.jsondata.PettingAnimationData;
import net.trinketina.frogpetting.resourcegen.jsondata.PettingBoneData;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AnimationDataReader {
    public static void ReadAnimation(String entity_id, BufferedReader reader) throws IOException {

        JsonElement animation_json = JsonParser.parseReader(reader);

        JsonObject root = animation_json.getAsJsonObject();
        JsonObject animations = root.getAsJsonObject("animations");
        JsonObject petting_animation = animations.getAsJsonObject("animation.petting");
        JsonObject bones = petting_animation.getAsJsonObject("bones");

        PettingAnimationData animation_data = new PettingAnimationData();
        animation_data.animation_length = petting_animation.get("animation_length").getAsFloat();

        ReadBones(bones, reader);
    }

    public static void ReadBones(JsonObject bones, BufferedReader reader) throws IOException {
        //initialize bones
        List<PettingBoneData> bone_animations = new ArrayList<>();


    }
}
