package blueridger.com.github.naturalregeneration.world.feature;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

public class ModPlacedFeatures {
	public static final Holder<PlacedFeature> AGAVE_PLANT_PLACED = PlacementUtils.register("agave_plant_placed",
			ModConfiguredFeatures.AGAVE_PLANT, RarityFilter.onAverageOnceEvery(64), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
}
