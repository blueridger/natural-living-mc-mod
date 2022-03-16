package blueridger.com.github.naturalregeneration;

import java.util.Random;

import net.minecraft.world.entity.Entity;

public class Utils {
	private static Random random = new Random();

	public static boolean oneIn(int i) {
		return random.nextInt(i) == 0;
	}

	public static int sameWithin(Entity ent, double searchRadius) {
		return ent.getLevel()
				.getEntitiesOfClass(ent.getClass(),
						new net.minecraft.world.phys.AABB(ent.getX() + searchRadius, ent.getY() + searchRadius,
								ent.getZ() + searchRadius, ent.getX() - searchRadius, ent.getY() - searchRadius,
								ent.getZ() - searchRadius))
				.size();
	}
}