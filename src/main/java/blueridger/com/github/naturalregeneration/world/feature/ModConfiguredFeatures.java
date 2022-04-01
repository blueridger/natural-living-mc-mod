package blueridger.com.github.naturalregeneration.world.feature;

import blueridger.com.github.naturalregeneration.block.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class ModConfiguredFeatures {
	public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> AGAVE_PLANT = FeatureUtils.register(
			"agave_plant", Feature.FLOWER,
			new RandomPatchConfiguration(256, 32, 8, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.AGAVE_PLANT.get())))));
}
