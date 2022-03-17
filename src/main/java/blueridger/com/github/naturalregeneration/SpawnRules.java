package blueridger.com.github.naturalregeneration;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Spider;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.CheckSpawn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event.Result;


public class SpawnRules {
	private static final Logger LOGGER = LogUtils.getLogger();

	@SubscribeEvent
	public void limitAndPersistSpawns(CheckSpawn event) {
		LivingEntity ent = event.getEntityLiving();
		if (ent.getLevel().isClientSide)
			return;
		Mob mob = (Mob) ent;

		switch (event.getSpawnReason().name()) {
		case "NATURAL":
			if (Animal.class.isInstance(ent) && !Spider.class.isInstance(ent)) {
				event.setResult(Result.DENY);
				LOGGER.debug("Denied natural spawn of " + ent.getType().toShortString());
			} else if (AbstractSchoolingFish.class.isInstance(ent)) {
				if (Utils.sameWithin(ent, NaturalBreeding.DEFAULT_NEW_SCHOOL_SEARCH_RADIUS) > NaturalBreeding.DEFAULT_NEW_SCHOOL_SEARCH_COUNT) {
					event.setResult(Result.DENY);
					return;
				} else {
					mob.setPersistenceRequired();
				}
			}
			break;

		case "CHUNK_GENERATION":
			if (Animal.class.isInstance(ent))
				mob.setPersistenceRequired();
			break;

		}
//		LOGGER.debug(event.getSpawnReason().name() + " " + ent.getType().toShortString() + " "
//				+ mob.isPersistenceRequired());
	}
}
