// TODO(Ravel): Failed to fully resolve file: null cannot be cast to non-null type com.intellij.psi.PsiClass
// TODO(Ravel): Failed to fully resolve file: null cannot be cast to non-null type com.intellij.psi.PsiClass
package net.trinketina.frogpetting.mixin;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.trinketina.frogpetting.IPettingAnimationState;
import net.trinketina.frogpetting.IPettingModel;
import net.trinketina.frogpetting.PettingData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Function;

@Mixin(EntityModel.class)
public abstract class PettingEntityModelMixin<T extends EntityRenderState> extends Model implements IPettingModel {

    public PettingEntityModelMixin(ModelPart root, Function renderType) {
        super(root, renderType);
    }






}
