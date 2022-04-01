package blueridger.com.github.naturalregeneration.entity;

import blueridger.com.github.naturalregeneration.NaturalRegeneration;
import blueridger.com.github.naturalregeneration.entity.custom.Crab;
import blueridger.com.github.naturalregeneration.entity.custom.ShipEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES,
			NaturalRegeneration.MODID);

//	public static final RegistryObject<EntityType<Crab>> CRAB = ENTITIES.register("crab",
//			() -> EntityType.Builder.of(Crab::new, MobCategory.WATER_CREATURE).sized(1f, 1f)
//					.build(new ResourceLocation(NaturalRegeneration.MODID, "crab").toString()));
	
//	public static final RegistryObject<EntityType<ShipEntity>> SHIP = ENTITIES.register("ship",
//			() -> EntityType.Builder.of(ShipEntity::new, MobCategory.MISC).sized(1f, 1f)
//					.build(new ResourceLocation(NaturalRegeneration.MODID, "ship").toString()));


	public static void register(IEventBus eventBus) {
		ENTITIES.register(eventBus);
	}
}
