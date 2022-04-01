package blueridger.com.github.naturalregeneration.client.renderer;

import blueridger.com.github.naturalregeneration.NaturalRegeneration;
import blueridger.com.github.naturalregeneration.client.renderer.model.CrabModel;
import blueridger.com.github.naturalregeneration.entity.custom.Crab;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class CrabRenderer<T extends Crab> extends MobRenderer<T, CrabModel<T>> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(NaturalRegeneration.MODID, "textures/entity/crab.png");
	
	public CrabRenderer(Context context) {
		super(context, new CrabModel(context.bakeLayer(CrabModel.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(T pEntity) {
		return TEXTURE;
	}
	
}
