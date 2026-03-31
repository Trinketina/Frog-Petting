package net.trinketina.frogpetting.resourcegen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.trinketina.frogpetting.PettingData;
import net.trinketina.frogpetting.PettingClient;
import net.trinketina.frogpetting.resourcegen.jsondata.PettingOffsetData;
import net.trinketina.frogpetting.resourcegen.jsondata.AnimationData;

import java.io.BufferedReader;

public class PettingResourceLoader implements SimpleSynchronousResourceReloadListener {

    public static String animation_name = "animation.petting";

    @Override
    public Identifier getFabricId() {
        return Identifier.tryBuild("frog-petting", "offsets");
    }

    /*@Override
    public void reload(ResourceManager manager) {
        PettingClient.LOGGER.info(getFabricId().toString());
        loadOffsets(manager);
        loadAnimations(manager);

        //Registry.register(Registries.SOUND_EVENT, )
    }*/

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
        //build the Entity.type name e.g. [entity_id.mod_id.entity_id]
        return "entity." + entity_namespace + "." + id.getPath().substring(index_last_slash + 1, id.getPath().lastIndexOf(".json"));

    }
    private void loadOffsets(ResourceManager manager) {
        PettingData.OFFSETS.clear();
        for (Identifier id : manager.listResources("offsets", path -> path.toString().endsWith(".json")).keySet()) {
            //PettingClient.LOGGER.info(id.getPath());
            try (BufferedReader reader = manager.getResource(id).get().openAsReader()) {
                //offsets should be formatted like [offsets/mod_id/entity_id.json]

                String entity = getEntityType(id);

                Gson gson = new GsonBuilder().setPrettyPrinting().create();

                PettingOffsetData offset_json = gson.fromJson(reader, PettingOffsetData.class);

                PettingData.OFFSETS.put(entity, offset_json);

            } catch (Exception e) {
                PettingClient.LOGGER.error("Error occurred while loading resource json" + id.toString(), e);
            }
        }
    }
    private void loadAnimations(ResourceManager manager) {
        PettingData.PETTING_ANIMATIONS.clear();
        for (Identifier id : manager.listResources("animations", path -> path.toString().endsWith(".json")).keySet()) {
            //PettingClient.LOGGER.info(id.getPath());
            try (BufferedReader reader = manager.getResource(id).get().openAsReader()) {
                //animations should be formatted like [animations/mod_id/entity_id.json]
                String entity_id = getEntityType(id);


                AnimationData animation_data = AnimationDataReader.readAnimation(reader, animation_name);
                PettingData.PETTING_ANIMATIONS.put(entity_id, AnimationDataReader.buildAnimation(animation_data));
                //PettingClient.LOGGER.info("Added animation for: " + entity_id);

            } catch (Exception e) {
                PettingClient.LOGGER.error("Error occurred while loading resource json" + id.toString(), e);
            }
        }
    }

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
        PettingClient.LOGGER.info(getFabricId().toString());
        loadOffsets(resourceManager);
        loadAnimations(resourceManager);
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

