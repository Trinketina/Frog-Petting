package net.trinketina.frogpetting;

import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.GsonBuilder;
import com.nimbusds.jose.shaded.gson.JsonDeserializer;
import com.nimbusds.jose.shaded.gson.JsonObject;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.advancement.criterion.BeeNestDestroyedCriterion;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.Vec2f;

import java.io.BufferedReader;
import java.io.InputStream;
import java.util.Objects;

public class PettingResourceLoader implements SimpleSynchronousResourceReloadListener{
    @Override
    public Identifier getFabricId() {
        return Identifier.of("frog-petting", "offsets");
    }

    @Override
    public void reload(ResourceManager manager) {
        PettingClient.LOGGER.info(getFabricId().toString());

        for (Identifier id : manager.findResources("offsets", path -> path.toString().endsWith(".json")).keySet()) {
            PettingClient.LOGGER.info(id.getPath());
            try (BufferedReader reader = manager.getResource(id).get().getReader()) {
                //offsets should be formatted like "offsets/mod_id/entity.json"
                String entity_namespace = id.getPath().substring(id.getPath().indexOf("/") + 1, id.getPath().lastIndexOf("/"));
                if (entity_namespace.isEmpty()) {
                    continue;
                }
                String entity = entity_namespace + ":" + id.getPath().substring(id.getPath().lastIndexOf("/") + 1, id.getPath().lastIndexOf(".json"));

                Gson gson = new GsonBuilder().setPrettyPrinting().create();

                OffsetJsonFormat offset_json = gson.fromJson(reader, OffsetJsonFormat.class);


                PettingClient.OFFSETS.put(entity, offset_json.offset);

                //PettingClient.LOGGER.info(entity + " = [" + offset_json.offset[0] + ", " + offset_json.offset[1] + "]");
                reader.close();
            } catch (Exception e) {
                PettingClient.LOGGER.error("Error occurred while loading resource json" + id.toString(), e);
            }
        }

    }
}

class OffsetJsonFormat {
    double[] offset;
}
