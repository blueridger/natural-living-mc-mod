package blueridger.com.github.naturalregeneration.world.gen;


import java.util.List;
import java.util.Set;

import blueridger.com.github.naturalregeneration.world.feature.ModPlacedFeatures;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class ModPlantGeneration {
	public static void generatePlants(final BiomeLoadingEvent event) {
		ResourceKey<Biome> key = ResourceKey.create(Registry.BIOME_REGISTRY, event.getName());
		Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(key);
		
		if (types.contains(BiomeDictionary.Type.DRY)) {
			List<Holder<PlacedFeature>> base = event.getGeneration().getFeatures(Decoration.VEGETAL_DECORATION);
			
			base.add(ModPlacedFeatures.AGAVE_PLANT_PLACED);
		}
	}
}
