package net.trinketina.frogpetting.resourcegen;

import com.nimbusds.jose.shaded.gson.JsonElement;
import com.nimbusds.jose.shaded.gson.JsonObject;
import com.nimbusds.jose.shaded.gson.JsonParser;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;
import net.trinketina.frogpetting.PettingClient;
import net.trinketina.frogpetting.resourcegen.jsondata.AnimationData;
import net.trinketina.frogpetting.resourcegen.jsondata.AnimationBoneData;
import net.trinketina.frogpetting.resourcegen.jsondata.BoneTransformationData;
import org.joml.Vector3f;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AnimationDataReader {

    public static Animation buildAnimation(AnimationData animation_data) {
        float animation_length = animation_data.animation_length;

        Animation.Builder animation_builder = Animation.Builder.create(animation_length);

        for (AnimationBoneData bone_animation : animation_data.bone_animations) {
            for (BoneTransformationData animation_element : bone_animation.transformation_animations) {
                animation_builder = animation_builder.addBoneAnimation(
                        bone_animation.bone_name,
                        new Transformation(animation_element.transformation_target, animation_element.keyframes));
                //PettingClient.LOGGER.info("Added animation to " + bone_animation.bone_name + ": " + animation_element.keyframes[1]);
            }
        }

        return  animation_builder.build();
    }

    public static AnimationData readAnimation(BufferedReader reader) throws IOException {

        JsonElement animation_json = JsonParser.parseReader(reader);

        JsonObject root = animation_json.getAsJsonObject();
        JsonObject animations = root.getAsJsonObject("animations");
        JsonObject petting_animation = animations.getAsJsonObject("animation.petting");
        JsonObject bones = petting_animation.getAsJsonObject("bones");

        AnimationData animation_data = new AnimationData();
        animation_data.animation_length = petting_animation.get("animation_length").getAsFloat();

        animation_data.bone_animations = readBones(bones);

        return animation_data;
    }

    public static List<AnimationBoneData> readBones(JsonObject bones) {
        //initialize bones
        List<AnimationBoneData> bone_animations = new ArrayList<>();

        //iterate through the bones
        for (Map.Entry<String, JsonElement> boneEntry : bones.entrySet()) {
            AnimationBoneData bone_animation = new AnimationBoneData();

            //load bone_name
            bone_animation.bone_name = boneEntry.getKey();
            PettingClient.LOGGER.info(bone_animation.bone_name);

            //initialize keyframeEntry hashmap
            //iterate through keyframes
                    /*List<Keyframe> scale_keyframes = new ArrayList<>();
                    List<Keyframe> rotate_keyframes = new ArrayList<>();
                    List<Keyframe> translate_keyframes = new ArrayList<>();*/
            //List<PettingTransformationsData> animation_elements = new ArrayList<>();




            bone_animation.transformation_animations = readTransformations(boneEntry.getValue());
            bone_animations.add(bone_animation);
        }
        return bone_animations;
    }
    public static List<BoneTransformationData> readTransformations(JsonElement bone_data) {
        List<BoneTransformationData> transformations = new ArrayList<>();

        for (Map.Entry<String, JsonElement> transformationEntry : bone_data.getAsJsonObject().entrySet()) {
            String transformation_target_type = transformationEntry.getKey();
            Transformation.Target transformation_target;
            switch (transformation_target_type) {
                case "position":
                    transformation_target = Transformation.Targets.MOVE_ORIGIN;
                    break;
                case "rotation":
                    transformation_target = Transformation.Targets.ROTATE;
                    break;
                case "scale":
                    transformation_target = Transformation.Targets.SCALE;
                    break;
                default:
                    //skip loading the keyframes if invalid target type
                    continue;
            }
            Keyframe[] keyframes = readKeyframes(transformationEntry.getValue(), transformation_target_type).toArray(new Keyframe[0]);
            BoneTransformationData transformation_data = new BoneTransformationData(transformation_target, keyframes);

            transformations.add(transformation_data);
        }
        return transformations;
    }
    public static List<Keyframe> readKeyframes(JsonElement keyframe_data, String transformation_target_type) {
        List<Keyframe> keyframes = new ArrayList<>();

        for (Map.Entry<String, JsonElement> keyframeEntry : keyframe_data.getAsJsonObject().entrySet()) {
            float keyframe_position = Float.parseFloat(keyframeEntry.getKey());
            //PettingClient.LOGGER.info(keyframe_position);

            String vector_string = keyframeEntry.getValue().toString();
            String[] vector_values = vector_string.substring(vector_string.indexOf("[") + 1, vector_string.indexOf("]")).split(",");


            float x = Float.parseFloat(vector_values[0]);
            float y = Float.parseFloat(vector_values[1]);
            float z = Float.parseFloat(vector_values[2]);


            Vector3f keyframe_vector = new Vector3f(x, y, z);
            Keyframe keyframe;
            switch (transformation_target_type) {
                case "position":
                    keyframe_vector = AnimationHelper.createTranslationalVector(x, y, z);
                    keyframe = new Keyframe(keyframe_position, keyframe_vector, Transformation.Interpolations.LINEAR);
                    //PettingClient.LOGGER.info("position: [" + keyframe_vector.x + ", " + keyframe_vector.y + ", " + keyframe_vector.z + "]");
                    break;
                case "rotation":
                    keyframe_vector = AnimationHelper.createRotationalVector(x, y, z);
                    keyframe = new Keyframe(keyframe_position, keyframe_vector, Transformation.Interpolations.LINEAR);
                    //PettingClient.LOGGER.info("rotation: [" + keyframe_vector.x + ", " + keyframe_vector.y + ", " + keyframe_vector.z + "]");
                    break;
                case "scale":
                    keyframe_vector = AnimationHelper.createScalingVector(x, y, z);
                    keyframe = new Keyframe(keyframe_position, keyframe_vector, Transformation.Interpolations.LINEAR);
                    //PettingClient.LOGGER.info("scale: [" + keyframe_vector.x + ", " + keyframe_vector.y + ", " + keyframe_vector.z + "]");
                    break;
                default:
                    //skip keyframe if invalid target type, should never default but just in case
                    continue;
            }
            keyframes.add(keyframe);

            //PettingClient.LOGGER.info("[" + x + ", " + y + ", " + z + "]");
            //TODO:: parse interpolation of keyframeEntry
        }

        return keyframes;
    }
}
