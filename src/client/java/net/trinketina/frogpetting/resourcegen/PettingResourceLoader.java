package net.trinketina.frogpetting.resourcegen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.trinketina.frogpetting.PettingData;
import net.trinketina.frogpetting.PettingClient;
import net.trinketina.frogpetting.animation.Animation;
import net.trinketina.frogpetting.resourcegen.jsondata.PettingOffsetData;
import net.trinketina.frogpetting.resourcegen.jsondata.AnimationData;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.util.Map;

public class PettingResourceLoader implements ResourceManagerReloadListener {

    private String getEntityType(Identifier id) {
        int index_first_slash = id.getPath().indexOf("/");
        int  index_last_slash = id.getPath().lastIndexOf("/");
        if (index_first_slash == index_last_slash) {
            //file not in a mod_id folder
            return null;
        }
        String entity_namespace = id.getPath().substring(index_first_slash + 1, index_last_slash);
        if (entity_namespace.isEmpty()) {
            return null;
        }
        //build the Entity.type name e.g. [entity.mod_id.entity_id]
        return "entity." + entity_namespace + "." + id.getPath().substring(index_last_slash + 1, id.getPath().lastIndexOf(".json"));

    }
    private void loadOffsets(ResourceManager manager) {
        PettingData.OFFSETS.clear();
        for (Identifier id : manager.listResources("offsets", path -> path.toString().endsWith(".json")).keySet()) {

            try (BufferedReader reader = manager.getResource(id).get().openAsReader()) {
                //offsets should be formatted like [offsets/mod_id/entity_id.json]

                String entity = getEntityType(id);

                Gson gson = new GsonBuilder().setPrettyPrinting().create();

                PettingOffsetData offset_json = gson.fromJson(reader, PettingOffsetData.class);

                //successfully added an offset
                PettingData.OFFSETS.put(entity, offset_json);

            } catch (Exception e) {
                PettingClient.LOGGER.error("Error occurred while loading resource json" + id.toString(), e);
            }
        }
    }
    private void loadAnimations(ResourceManager manager, String animation_name, Map<String, Animation> animations, boolean is_necessary) {
        animations.clear();

        for (Identifier id : manager.listResources("animations", path -> path.toString().endsWith(".json")).keySet()) {
            //PettingClient.LOGGER.info(id.getPath());
            try (BufferedReader reader = manager.getResource(id).get().openAsReader()) {
                //animations should be formatted like [animations/mod_id/entity_id.json]
                String entity_id = getEntityType(id);

                AnimationData animation_data = AnimationDataReader.readAnimation(reader, animation_name, is_necessary);
                if (animation_data == null) {
                    if (is_necessary)
                        PettingClient.LOGGER.warn("Error occurred while loading animation: " + animation_name + " for " + entity_id);
                }
                else {
                    //successfully added an animation
                    animations.put(entity_id, AnimationDataReader.buildAnimation(animation_data));
                }

            } catch (Exception e) {
                if (is_necessary)
                    PettingClient.LOGGER.error("Error occurred while loading resource json" + id.toString(), e);
            }
        }
    }

    @Override
    public void onResourceManagerReload(@NotNull ResourceManager resourceManager) {
        PettingClient.LOGGER.info("loading offsets");
        loadOffsets(resourceManager);

        PettingClient.LOGGER.info("loading animations");
        loadAnimations(resourceManager, "animation.petting", PettingData.PETTING_ANIMATIONS, true);
        loadAnimations(resourceManager, "animation.petting_baby", PettingData.BABY_PETTING_ANIMATIONS, false);
    }

/*    private void loadSounds(ResourceManager manager) {
        PettingData.PETTING_SOUNDS.clear();

        for (Identifier id : manager.findResources("sounds", path -> path.toString().endsWith(".json")).keySet()) {
            //PettingClient.LOGGER.info(id.getPath());
            try (BufferedReader reader = manager.getResource(id).get().getReader()) {
                //sounds should be formatted like [sounds/mod_id/entity_id.json]

                String entity = getEntityType(id);

                Gson gson = new GsonBuilder().setPrettyPrinting().create();

                SoundEvent sound_event = gson.fromJson(reader, SoundEvent.class);

                PettingData.PETTING_SOUNDS.put(entity, sound_event);

            } catch (Exception e) {
                PettingClient.LOGGER.error("Error occurred while loading resource json" + id.toString(), e);
            }
        }

    }*/
}

