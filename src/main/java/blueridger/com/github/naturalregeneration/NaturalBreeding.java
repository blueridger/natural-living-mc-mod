package blueridger.com.github.naturalregeneration;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cod;
import net.minecraft.world.entity.animal.Salmon;
import net.minecraft.world.entity.animal.TropicalFish;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class NaturalBreeding {
	private static final Logger LOGGER = LogUtils.getLogger();
	protected static final double DEFAULT_SEARCH_RADIUS = 100;
	protected static final int DEFAULT_MAX_HERD_SIZE = 5;
	protected static final int DEFAULT_LOVE_INTERVAL_SECONDS = 500;
	protected static final int DEFAULT_NEW_SCHOOL_SEARCH_RADIUS = 400;
	protected static final int DEFAULT_NEW_SCHOOL_SEARCH_COUNT = 5;
	protected static final int DEFAULT_SCHOOL_FORK_CHANCE = 30;
	protected static final Map<String, Double> SEARCH_RADIUS = new HashMap<String, Double>();
	protected static final Map<String, Integer> MAX_HERD_SIZE = new HashMap<String, Integer>();
	protected static final Map<String, Integer> LOVE_INTERVAL_SECONDS = new HashMap<String, Integer>();

	public NaturalBreeding() {
		MAX_HERD_SIZE.put("rabbit", 10);
		LOVE_INTERVAL_SECONDS.put("rabbit", 100);

		MAX_HERD_SIZE.put("turtle", 4);
		SEARCH_RADIUS.put("turtle", 300.0);

		MAX_HERD_SIZE.put("cow", 10);
		SEARCH_RADIUS.put("cow", 200.0);

		MAX_HERD_SIZE.put("sheep", 10);
		SEARCH_RADIUS.put("sheep", 200.0);

		SEARCH_RADIUS.put("bee", 200.0);

		MAX_HERD_SIZE.put("panda", 4);
		MAX_HERD_SIZE.put("polar_bear", 4);

		MAX_HERD_SIZE.put("axolotl", 3);
		SEARCH_RADIUS.put("axolotl", 200.0);

		MAX_HERD_SIZE.put("salmon", 15);
		MAX_HERD_SIZE.put("cod", 30);
	}

	@SubscribeEvent
	public void naturalLove(LivingUpdateEvent event) {
		LivingEntity ent = event.getEntityLiving();
		if (!Animal.class.isInstance(ent) || event.getEntityLiving().getLevel().isClientSide) {
			return;
		}
		Animal animal = (Animal) ent;
		int loveInterval = Optional.ofNullable(LOVE_INTERVAL_SECONDS.get(animal.getType().toShortString()))
				.orElse(DEFAULT_LOVE_INTERVAL_SECONDS);

		// For easy testing
//		if (animal.getAge() > 1000)
//			animal.setAge(1000);
//		if (animal.getAge() < -1000)
//			animal.setAge(-1000);

		if (animal.getAge() == 0 && Utils.oneIn(loveInterval * 20) && !animal.isInLove()) {
			int maxHerdSize = Optional.ofNullable(MAX_HERD_SIZE.get(animal.getType().toShortString()))
					.orElse(DEFAULT_MAX_HERD_SIZE);
			double searchRadius = Optional.ofNullable(SEARCH_RADIUS.get(animal.getType().toShortString()))
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
		if (!AbstractSchoolingFish.class.isInstance(ent) || ent.getLevel().isClientSide) {
			return;
		}
		AbstractSchoolingFish fish = (AbstractSchoolingFish) ent;
		int loveInterval = LOVE_INTERVAL_SECONDS.get(fish.getType().toShortString()) != null
				? LOVE_INTERVAL_SECONDS.get(fish.getType().toShortString())
				: DEFAULT_LOVE_INTERVAL_SECONDS;

		if (Utils.oneIn(loveInterval * 20)) {
			int maxHerdSize = MAX_HERD_SIZE.get(ent.getType().toShortString()) != null
					? MAX_HERD_SIZE.get(ent.getType().toShortString())
					: DEFAULT_MAX_HERD_SIZE;
			double searchRadius = SEARCH_RADIUS.get(ent.getType().toShortString()) != null
					? SEARCH_RADIUS.get(ent.getType().toShortString())
					: DEFAULT_SEARCH_RADIUS;

			int actualHerdSize = Utils.sameWithin(ent, searchRadius);
			if (actualHerdSize >= maxHerdSize || actualHerdSize < 2)
				return;

			AbstractSchoolingFish newFish = null;
			if (Salmon.class.isInstance(fish))
				newFish = new Salmon(EntityType.SALMON, fish.getLevel());
			if (Cod.class.isInstance(fish))
				newFish = new Cod(EntityType.COD, fish.getLevel());
			if (TropicalFish.class.isInstance(fish))
				newFish = new TropicalFish(EntityType.TROPICAL_FISH, fish.getLevel());
			if (newFish == null) {
				LOGGER.debug("Unexpected fish type: " + fish.getType());
				return;
			}
			newFish.setPos(fish.position());
			fish.getLevel().addFreshEntity(newFish);
			newFish.setPersistenceRequired();
			if (!Utils.oneIn(DEFAULT_SCHOOL_FORK_CHANCE))
				newFish.startFollowing(fish);
		}
	}
}
