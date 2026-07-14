package net.trinketina.frogpetting.resourcegen;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.AnimationChannel;
import net.trinketina.frogpetting.PettingClient;
import net.trinketina.frogpetting.animation.Animation;
import net.trinketina.frogpetting.animation.Transformation;
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

    public static AnimationData readAnimation(BufferedReader reader, String animation_name, boolean is_necessary) throws IOException {

        JsonElement animation_json = JsonParser.parseReader(reader);

        JsonObject root = animation_json.getAsJsonObject();
        JsonObject animations = root.getAsJsonObject("animations");

        JsonObject petting_animation = animations.getAsJsonObject(animation_name);
        if (petting_animation == null)  {
            if (is_necessary)
                PettingClient.LOGGER.error("Animation '" + animation_name + "' not found");
            return null;
        }
        JsonObject bones = petting_animation.getAsJsonObject("bones");

        AnimationData animation_data = new AnimationData();
        animation_data.animation_length = petting_animation.get("animation_length").getAsFloat();

        //loop through and read the bone data
        animation_data.bone_animations = readBones(bones);

        return animation_data;
    }
    static List<AnimationBoneData> readBones(JsonObject bones) {
        //initialize bones
        List<AnimationBoneData> bone_animations = new ArrayList<>();

        //iterate through the bones
        for (Map.Entry<String, JsonElement> bone_entry : bones.entrySet()) {
            AnimationBoneData bone_animation = new AnimationBoneData();

            bone_animation.bone_name = bone_entry.getKey();
            //PettingClient.LOGGER.info(bone_animation.bone_name);

            //loop through and read the transformation data
            bone_animation.transformation_animations = readTransformations(bone_entry.getValue());
            bone_animations.add(bone_animation);
        }
        return bone_animations;
    }
    static List<BoneTransformationData> readTransformations(JsonElement bone_data) {
        //initialize transformation data
        List<BoneTransformationData> transformations = new ArrayList<>();

        //iterate through the transformation types present
        for (Map.Entry<String, JsonElement> transformationEntry : bone_data.getAsJsonObject().entrySet()) {
            String transformation_target_type = transformationEntry.getKey();
            Transformation.Target transformation_target;
            switch (transformation_target_type) {
                case "position":
                    transformation_target = Transformation.Targets.POSITION;
                    break;
                case "rotation":
                    transformation_target = Transformation.Targets.ROTATION;
                    break;
                case "scale":
                    transformation_target = Transformation.Targets.SCALE;
                    break;
                default:
                    //skip loading the keyframes if invalid target type
                    continue;
            }

            //loop through and read the keyframe data
            List<Keyframe> keyframes_list = readKeyframes(transformationEntry.getValue(), transformation_target_type);
            if (keyframes_list != null) {
                Keyframe[] keyframes = keyframes_list.toArray(new Keyframe[0]);
                BoneTransformationData transformation_data = new BoneTransformationData(transformation_target, keyframes);
                transformations.add(transformation_data);
            }
        }
        return transformations;
    }
    static List<Keyframe> readKeyframes(JsonElement keyframe_data, String transformation_target_type) {
        //initialize keyframes array
        List<Keyframe> keyframes = new ArrayList<>();

        //iterate through each keyframe
        for (Map.Entry<String, JsonElement> keyframeEntry : keyframe_data.getAsJsonObject().entrySet()) {
            //TODO:: parse interpolation of keyframeEntry

            //catch any errors for specific keyframes, don't throw out entire animation
            try {
                float keyframe_position = Float.parseFloat(keyframeEntry.getKey());

                String vector_string = keyframeEntry.getValue().toString();
                String[] vector_values = vector_string.substring(vector_string.indexOf("[") + 1, vector_string.indexOf("]")).split(",");

                float x = Float.parseFloat(vector_values[0]);
                float y = Float.parseFloat(vector_values[1]);
                float z = Float.parseFloat(vector_values[2]);


                //create the keyframe, depending on what type of transformation it is
                Vector3f keyframe_vector = new Vector3f(x, y, z);
                Keyframe keyframe;
                switch (transformation_target_type) {
                    case "position":
                        keyframe_vector = KeyframeAnimations.posVec(x, y, z);
                        keyframe = new Keyframe(keyframe_position, keyframe_vector, AnimationChannel.Interpolations.LINEAR);
                        //PettingClient.LOGGER.info("position: [" + keyframe_vector.x + ", " + keyframe_vector.y + ", " + keyframe_vector.z + "]");
                        break;
                    case "rotation":
                        keyframe_vector = KeyframeAnimations.degreeVec(x, y, z);
                        keyframe = new Keyframe(keyframe_position, keyframe_vector, AnimationChannel.Interpolations.LINEAR);
                        //PettingClient.LOGGER.info("rotation: [" + keyframe_vector.x + ", " + keyframe_vector.y + ", " + keyframe_vector.z + "]");
                        break;
                    case "scale":
                        keyframe_vector = KeyframeAnimations.scaleVec(x, y, z);
                        keyframe = new Keyframe(keyframe_position, keyframe_vector, AnimationChannel.Interpolations.LINEAR);
                        //PettingClient.LOGGER.info("scale: [" + keyframe_vector.x + ", " + keyframe_vector.y + ", " + keyframe_vector.z + "]");
                        break;
                    default:
                        //skip keyframe if invalid target type, should never default but just in case
                        continue;
                }
                keyframes.add(keyframe);
            } catch (Exception e) {
                PettingClient.LOGGER.error("error reading keyframe: " + e.getMessage());
                return null;
            }
            //PettingClient.LOGGER.info("[" + x + ", " + y + ", " + z + "]");
        }

        return keyframes;
    }

    public static Animation buildAnimation(AnimationData animation_data) {
        float animation_length = animation_data.animation_length;

        Animation.Builder animation_builder = Animation.Builder.create(animation_length);

        for (AnimationBoneData bone_animation : animation_data.bone_animations) {
            for (BoneTransformationData animation_element : bone_animation.transformation_animations) {
                animation_builder = animation_builder.addBoneTransformation(
                        bone_animation.bone_name,
                        new Transformation(animation_element.transformation_target, animation_element.keyframes));
                //PettingClient.LOGGER.info("Added animation to " + bone_animation.bone_name + ": " + animation_element.keyframes[1]);
            }
        }

        return  animation_builder.build();
    }
}