package blueridger.com.github.naturalregeneration;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cod;
import net.minecraft.world.entity.animal.Salmon;
import net.minecraft.world.entity.animal.TropicalFish;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class NaturalBreeding {
	private static final Logger LOGGER = LogUtils.getLogger();
	protected static final double DEFAULT_SEARCH_RADIUS = 100;
	protected static final int DEFAULT_MAX_HERD_SIZE = 5;
	protected static final int DEFAULT_LOVE_INTERVAL_SECONDS = 200;
	protected static final int DEFAULT_NEW_SCHOOL_SEARCH_RADIUS = 400;
	protected static final int DEFAULT_NEW_SCHOOL_SEARCH_COUNT = 5;
	protected static final int DEFAULT_SCHOOL_FORK_CHANCE = 30;
	protected static final Map<String, Double> SEARCH_RADIUS = new HashMap<String, Double>();
	protected static final Map<String, Integer> MAX_HERD_SIZE = new HashMap<String, Integer>();
	protected static final Map<String, Integer> LOVE_INTERVAL_SECONDS = new HashMap<String, Integer>();

	public NaturalBreeding() {
		MAX_HERD_SIZE.put("minecraft:rabbit", 10);
		LOVE_INTERVAL_SECONDS.put("minecraft:rabbit", 40);

		MAX_HERD_SIZE.put("minecraft:turtle", 4);
		SEARCH_RADIUS.put("minecraft:turtle", 200.0);

		MAX_HERD_SIZE.put("minecraft:cow", 10);
		SEARCH_RADIUS.put("minecraft:cow", 150.0);

		MAX_HERD_SIZE.put("minecraft:sheep", 10);
		SEARCH_RADIUS.put("minecraft:sheep", 150.0);

		MAX_HERD_SIZE.put("minecraft:pig", 7);

		SEARCH_RADIUS.put("minecraft:bee", 200.0);

		MAX_HERD_SIZE.put("minecraft:panda", 3);
		MAX_HERD_SIZE.put("minecraft:polar_bear", 3);

		MAX_HERD_SIZE.put("minecraft:axolotl", 3);

		MAX_HERD_SIZE.put("minecraft:salmon", 15);
		LOVE_INTERVAL_SECONDS.put("minecraft:salmon", 400);
		MAX_HERD_SIZE.put("minecraft:cod", 30);
		LOVE_INTERVAL_SECONDS.put("minecraft:cod", 400);
	}

	@SubscribeEvent
	public void naturalLove(LivingUpdateEvent event) {
		LivingEntity ent = event.getEntityLiving();
		if (!Animal.class.isInstance(ent) || event.getEntityLiving().getLevel().isClientSide) {
			return;
		}
		Animal animal = (Animal) ent;
		int loveInterval = Optional.ofNullable(LOVE_INTERVAL_SECONDS.get(animal.getType().getRegistryName().toString()))
				.orElse(DEFAULT_LOVE_INTERVAL_SECONDS);

		// For easy testing
//		if (animal.getAge() > 1000)
//			animal.setAge(1000);
//		if (animal.getAge() < -1000)
//			animal.setAge(-1000);

		if (animal.getAge() == 0 && Utils.oneIn(loveInterval * 20) && !animal.isInLove()) {
			int maxHerdSize = Optional.ofNullable(MAX_HERD_SIZE.get(animal.getType().getRegistryName().toString()))
					.orElse(DEFAULT_MAX_HERD_SIZE);
			double searchRadius = Optional.ofNullable(SEARCH_RADIUS.get(animal.getType().getRegistryName().toString()))
					.orElse(DEFAULT_SEARCH_RADIUS);

			int actualHerdSize = Utils.sameWithin(ent, searchRadius);
			if (actualHerdSize < maxHerdSize) {
				animal.setInLove(null);
				LOGGER.debug(animal.getType().toShortString() + " is in love. Herd size: " + actualHerdSize);
			}
		}
	}

	@SubscribeEvent
	public void naturalLoveFish(LivingUpdateEvent event) {
		LivingEntity ent = event.getEntityLiving();
		if (!(Salmon.class.isInstance(ent) || Cod.class.isInstance(ent)) || ent.getLevel().isClientSide) {
			return;
		}
		AbstractSchoolingFish fish = (AbstractSchoolingFish) ent;
		int loveInterval = LOVE_INTERVAL_SECONDS.get(fish.getType().getRegistryName().toString()) != null
				? LOVE_INTERVAL_SECONDS.get(fish.getType().getRegistryName().toString())
				: DEFAULT_LOVE_INTERVAL_SECONDS;

		if (Utils.oneIn(loveInterval * 20)) {
			int maxHerdSize = MAX_HERD_SIZE.get(ent.getType().getRegistryName().toString()) != null
					? MAX_HERD_SIZE.get(ent.getType().getRegistryName().toString())
					: DEFAULT_MAX_HERD_SIZE;
			double searchRadius = SEARCH_RADIUS.get(ent.getType().getRegistryName().toString()) != null
					? SEARCH_RADIUS.get(ent.getType().getRegistryName().toString())
					: DEFAULT_SEARCH_RADIUS;

			int actualHerdSize = Utils.sameWithin(ent, searchRadius);
			if (actualHerdSize >= maxHerdSize || actualHerdSize < 2)
				return;

			AbstractSchoolingFish newFish = null;
			if (Salmon.class.isInstance(fish))
				newFish = new Salmon(EntityType.SALMON, fish.getLevel());
			if (Cod.class.isInstance(fish))
				newFish = new Cod(EntityType.COD, fish.getLevel());
			if (newFish == null) {
				LOGGER.debug("Unsupported fish type: " + fish.getType());
				return;
			}
			newFish.setPos(fish.position());
			fish.getLevel().addFreshEntity(newFish);
			newFish.setPersistenceRequired();
			if (!Utils.oneIn(DEFAULT_SCHOOL_FORK_CHANCE))
				newFish.startFollowing(fish);
		}
	}

	@SubscribeEvent
	public void debugAnimalInteract(EntityInteract event) {
		if (!Mob.class.isInstance(event.getTarget()) || event.getEntityLiving().getLevel().isClientSide)
			return;
//		LOGGER.debug(event.getTarget().getType().toShortString() + " Age: " + ((Animal) event.getTarget()).getAge()
//				+ " Love: " + ((Animal) event.getTarget()).isInLove());
		
		LOGGER.debug("DescriptionId: " + event.getTarget().getType().getDescriptionId() + " String: "
				+ event.getTarget().getType().toString() + " toShortString: "
				+ event.getTarget().getType().toShortString() + " RegistryName: "
				+ event.getTarget().getType().getRegistryName().toString() + " Description getString: "
				+ event.getTarget().getType().getDescription().getString());
	}
}
