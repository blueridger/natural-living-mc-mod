package blueridger.com.github.naturalregeneration.block;

import java.util.function.Supplier;

import blueridger.com.github.naturalregeneration.NaturalRegeneration;
import blueridger.com.github.naturalregeneration.Item.ModItems;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DeadBushBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
			NaturalRegeneration.MODID);

	public static final RegistryObject<Block> THATCH_BLOCK = registerBlock("thatch_block",
			() -> new Block(BlockBehaviour.Properties.of(Material.WOOD).strength(0.4F).sound(SoundType.GRASS)),
			CreativeModeTab.TAB_BUILDING_BLOCKS);

	public static final RegistryObject<Block> AGAVE_PLANT = registerBlock("agave_plant",
			() -> new DeadBushBlock(BlockBehaviour.Properties.of(Material.PLANT).strength(0.4F).dynamicShape().noOcclusion().sound(SoundType.GRASS)),
			CreativeModeTab.TAB_DECORATIONS);

	public static final RegistryObject<Block> POTTED_AGAVE_PLANT = registerBlockWithoutBlockItem("potted_agave_plant",
			() -> new FlowerPotBlock(null, AGAVE_PLANT, BlockBehaviour.Properties.copy(Blocks.POTTED_DANDELION).noOcclusion().sound(SoundType.GRASS)),
			CreativeModeTab.TAB_DECORATIONS);
	
	

	private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block,
			CreativeModeTab tab) {
		RegistryObject<T> toReturn = BLOCKS.register(name, block);
		registerBlockItem(name, toReturn, tab);
		return toReturn;
	}

	private static <T extends Block> RegistryObject<T> registerBlockWithoutBlockItem(String name, Supplier<T> block,
			CreativeModeTab tab) {
		return BLOCKS.register(name, block);
	}

	private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block,
			CreativeModeTab tab) {
		return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
	}

	public static void register(IEventBus eventBus) {
		BLOCKS.register(eventBus);
	}
}
