package blueridger.com.github.naturalregeneration.client.event;

import blueridger.com.github.naturalregeneration.NaturalRegeneration;
import blueridger.com.github.naturalregeneration.client.renderer.CrabRenderer;
import blueridger.com.github.naturalregeneration.client.renderer.model.CrabModel;
import blueridger.com.github.naturalregeneration.entity.ModEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = NaturalRegeneration.MODID, bus = Bus.MOD, value = Dist.CLIENT)
public final class ClientModEvents {

	@SubscribeEvent
	public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
//		event.registerLayerDefinition(CrabModel.LAYER_LOCATION, CrabModel::createBodyLayer);
	}
	
	@SubscribeEvent
	public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
//		event.registerEntityRenderer(ModEntities.CRAB.get(), CrabRenderer::new);
	}
}
