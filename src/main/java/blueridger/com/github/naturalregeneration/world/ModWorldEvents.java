package blueridger.com.github.naturalregeneration.world;

import blueridger.com.github.naturalregeneration.NaturalRegeneration;
import blueridger.com.github.naturalregeneration.world.gen.ModPlantGeneration;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NaturalRegeneration.MODID)
public class ModWorldEvents {
	@SubscribeEvent
	public static void biomeLoadingEvent(final BiomeLoadingEvent event) {
		ModPlantGeneration.generatePlants(event);
	}
	
}
