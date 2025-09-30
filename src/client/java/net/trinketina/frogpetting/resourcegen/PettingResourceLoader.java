package net.trinketina.frogpetting.resourcegen;

import com.nimbusds.jose.shaded.gson.*;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.trinketina.frogpetting.PettingAnimations;
import net.trinketina.frogpetting.PettingClient;
import org.joml.Vector3f;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PettingResourceLoader implements SimpleSynchronousResourceReloadListener{

    @Override
    public Identifier getFabricId() {
        return Identifier.of("frog-petting", "offsets");
    }

    @Override
    public void reload(ResourceManager manager) {
        PettingClient.LOGGER.info(getFabricId().toString());
        loadOffsets(manager);
        loadAnimations(manager);

    }

    private void loadOffsets(ResourceManager manager) {
        for (Identifier id : manager.findResources("offsets", path -> path.toString().endsWith(".json")).keySet()) {
            PettingClient.LOGGER.info(id.getPath());
            try (BufferedReader reader = manager.getResource(id).get().getReader()) {
                //offsets should be formatted like "offsets/mod_id/entity.json"
                String entity_namespace = id.getPath().substring(id.getPath().indexOf("/") + 1, id.getPath().lastIndexOf("/"));
                if (entity_namespace.isEmpty()) {
                    continue;
                }
                String entity = "entity." + entity_namespace + "." + id.getPath().substring(id.getPath().lastIndexOf("/") + 1, id.getPath().lastIndexOf(".json"));

                Gson gson = new GsonBuilder().setPrettyPrinting().create();

                PettingJsonData offset_json = gson.fromJson(reader, PettingJsonData.class);

                PettingClient.OFFSETS.put(entity, offset_json);

                //PettingClient.LOGGER.info(entity + " = [" + offset_json.offset[0] + ", " + offset_json.offset[1] + "]");
                reader.close();
            } catch (Exception e) {
                PettingClient.LOGGER.error("Error occurred while loading resource json" + id.toString(), e);
            }
        }
    }
    private void loadAnimations(ResourceManager manager) {
        for (Identifier id : manager.findResources("animations", path -> path.toString().endsWith(".json")).keySet()) {
            PettingClient.LOGGER.info(id.getPath());
            try (BufferedReader reader = manager.getResource(id).get().getReader()) {
                //animations should be formatted like "animations/mod_id/entity.json"
                String entity_namespace = id.getPath().substring(id.getPath().indexOf("/") + 1, id.getPath().lastIndexOf("/"));
                if (entity_namespace.isEmpty()) {
                    continue;
                }
                String entity = "entity." + entity_namespace + "." + id.getPath().substring(id.getPath().lastIndexOf("/") + 1, id.getPath().lastIndexOf(".json"));

                JsonElement animation_json = JsonParser.parseReader(reader);

                JsonObject root = animation_json.getAsJsonObject();
                JsonObject animations = root.getAsJsonObject("animations");
                JsonObject petting_animation = animations.getAsJsonObject("animation.petting");
                JsonObject bones = petting_animation.getAsJsonObject("bones");

                PettingAnimationData animation_data = new PettingAnimationData();
                animation_data.animation_length = petting_animation.get("animation_length").getAsFloat();
                //initialize bones
                List<BoneAnimation> bone_animations = new ArrayList<>();
                //iterate through the bones
                for (Map.Entry<String, JsonElement> boneEntry : bones.entrySet()) {
                    BoneAnimation bone_animation = new BoneAnimation();

                    //load bone_name
                    bone_animation.bone_name = boneEntry.getKey();
                    PettingClient.LOGGER.info(bone_animation.bone_name);

                    //initialize keyframeEntry hashmap
                    //bone_animation.animated_elements = new HashMap<>();
                    //iterate through keyframes
                    for (Map.Entry<String, JsonElement> modifierEntry : boneEntry.getValue().getAsJsonObject().entrySet()) {
                        String transformation_target_type = modifierEntry.getKey();

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
                                //skip keyframeEntry if invalid target type
                                continue;
                        }

                        List<Keyframe> scale_keyframes = new ArrayList<>();
                        List<Keyframe> rotate_keyframes = new ArrayList<>();
                        List<Keyframe> translate_keyframes = new ArrayList<>();
                        for (Map.Entry<String, JsonElement> keyframeEntry : modifierEntry.getValue().getAsJsonObject().entrySet()) {
                            float keyframe_position = Float.parseFloat(keyframeEntry.getKey());
                            //PettingClient.LOGGER.info(keyframe_position);

                            String vector_string = keyframeEntry.getValue().toString();
                            String[] vector_values = vector_string.substring(vector_string.indexOf("[") + 1, vector_string.indexOf("]")).split(",");

                            Vector3f keyframe_vector;
                            if (transformation_target == Transformation.Targets.SCALE) {
                                double x = Double.parseDouble(vector_values[0]);
                                double y = Double.parseDouble(vector_values[1]);
                                double z = Double.parseDouble(vector_values[2]);

                                keyframe_vector = AnimationHelper.createScalingVector(x, y, z);

                                Keyframe keyframe = new Keyframe(keyframe_position, keyframe_vector, Transformation.Interpolations.LINEAR);
                                scale_keyframes.add(keyframe);
                            }
                            else if (transformation_target == Transformation.Targets.ROTATE) {
                                float x = Float.parseFloat(vector_values[0]);
                                float y = Float.parseFloat(vector_values[1]);
                                float z = Float.parseFloat(vector_values[2]);

                                keyframe_vector = AnimationHelper.createRotationalVector(x, y, z);

                                Keyframe keyframe = new Keyframe(keyframe_position, keyframe_vector, Transformation.Interpolations.LINEAR);
                                rotate_keyframes.add(keyframe);
                            }
                            else {
                                float x = Float.parseFloat(vector_values[0]);
                                float y = Float.parseFloat(vector_values[1]);
                                float z = Float.parseFloat(vector_values[2]);

                                keyframe_vector = AnimationHelper.createTranslationalVector(x, y, z);

                                Keyframe keyframe = new Keyframe(keyframe_position, keyframe_vector, Transformation.Interpolations.LINEAR);
                                translate_keyframes.add(keyframe);
                            }
                            //PettingClient.LOGGER.info("[" + x + ", " + y + ", " + z + "]");

                            //TODO:: parse interpolation of keyframeEntry
                        }

                        List<AnimationElements> animation_elements = new ArrayList<>();
                        if (!scale_keyframes.isEmpty()) {
                            animation_elements.add(new AnimationElements(Transformation.Targets.SCALE, scale_keyframes.toArray(Keyframe[]::new)));
                        }
                        if (!rotate_keyframes.isEmpty()) {
                            animation_elements.add(new AnimationElements(Transformation.Targets.ROTATE, rotate_keyframes.toArray(Keyframe[]::new)));
                        }
                        if (!translate_keyframes.isEmpty()) {
                            animation_elements.add(new AnimationElements(Transformation.Targets.MOVE_ORIGIN, translate_keyframes.toArray(Keyframe[]::new)));
                        }
                        //AnimationElements animation_elements = new AnimationElements();

                        bone_animation.animation_elements = animation_elements;

                        bone_animations.add(bone_animation);
                    }
                }
                animation_data.bone_animations = bone_animations;





                PettingAnimations.PETTING_ANIMATIONS.put(entity, buildAnimation(animation_data));

                /*Gson gson = new GsonBuilder().setPrettyPrinting().create();

                PettingAnimationData animation_json = gson.fromJson(reader, PettingAnimationData.class);*/

                //PettingClient.LOGGER.info(animation_json.bone_animation.getFirst().bone_name);

                //PettingClient.LOGGER.info(entity + " = [" + offset_json.offset[0] + ", " + offset_json.offset[1] + "]");
                reader.close();
            } catch (Exception e) {
                PettingClient.LOGGER.error("Error occurred while loading resource json" + id.toString(), e);
            }
        }
    }
    private Animation buildAnimation(PettingAnimationData animation_data) {
        float animation_length = animation_data.animation_length;
        String current_bone_name = animation_data.bone_animations.getFirst().bone_name;
        Transformation.Target current_transformation_target = animation_data.bone_animations.getFirst().animation_elements.getFirst().transformation_target;
        Keyframe[] current_keyframes = animation_data.bone_animations.getFirst().animation_elements.getFirst().keyframes;

        Animation.Builder animation_builder = Animation.Builder.create(animation_length);

        for (BoneAnimation bone_animation : animation_data.bone_animations) {
            for (AnimationElements animation_elements: bone_animation.animation_elements) {
                animation_builder.addBoneAnimation(
                        bone_animation.bone_name,
                        new Transformation(animation_elements.transformation_target, animation_elements.keyframes));
                PettingClient.LOGGER.info("Added animation to " + bone_animation.bone_name + ": " + animation_elements.transformation_target.toString());
            }
        }

        return  animation_builder.build();
    }

}

