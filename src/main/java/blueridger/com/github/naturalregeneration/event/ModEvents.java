package blueridger.com.github.naturalregeneration.event;

import blueridger.com.github.naturalregeneration.NaturalRegeneration;
import blueridger.com.github.naturalregeneration.entity.ModEntities;
import blueridger.com.github.naturalregeneration.entity.custom.Crab;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = NaturalRegeneration.MODID, bus = Bus.MOD)
public class ModEvents {
	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
//		event.put(ModEntities.CRAB.get(), Crab.createAttributes().build());
	}
}
