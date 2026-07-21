package net.trinketina.frogpetting.mixin;

import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.trinketina.frogpetting.PettingClient;
import net.trinketina.frogpetting.PettingData;
import net.trinketina.frogpetting.interfaces.IContextModelRootProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityModelSet.class)
public class EntityRendererProviderContextMixin {
/*    @Unique
    public ModelPart root;*/

    @Inject(method = "bakeLayer", at = @At("RETURN"))
    public void onBakeLayer(ModelLayerLocation modelLayerLocation, CallbackInfoReturnable<ModelPart> cir) {
        try {
            PettingData.LAST_CREATED_ROOT = cir.getReturnValue();
        }
        catch (Exception e) {
            PettingClient.LOGGER.warn("Could not get root model at " + modelLayerLocation, e);
        }

    }

    /*@Override
    public ModelPart getLastCreatedRoot() {
        return root;
    }*/
}
