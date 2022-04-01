package blueridger.com.github.naturalregeneration;

import com.mojang.logging.LogUtils;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import blueridger.com.github.naturalregeneration.Item.ModItems;
import blueridger.com.github.naturalregeneration.block.ModBlocks;
import blueridger.com.github.naturalregeneration.entity.ModEntities;

import org.slf4j.Logger;
import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("naturalregeneration")
public class NaturalRegeneration {
	// Directly reference a slf4j logger
	private static final Logger LOGGER = LogUtils.getLogger();
	public static final String MODID = "naturalregeneration";
	
	public static final Foraging foragingModule = new Foraging();

	public NaturalRegeneration() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

		ModItems.register(eventBus);
		ModBlocks.register(eventBus);
		ModEntities.register(eventBus);
		
		// Register the setup method for modloading
		eventBus.addListener(this::setup);
		// Register the enqueueIMC method for modloading
		eventBus.addListener(this::enqueueIMC);
		// Register the processIMC method for modloading
		eventBus.addListener(this::processIMC);
		
		eventBus.addListener(this::clientSetup);

		// Register ourselves for server and other game events we are interested in
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(new AnimalDrops());
		MinecraftForge.EVENT_BUS.register(new NaturalBreeding());
		MinecraftForge.EVENT_BUS.register(new SpawnRules());
		MinecraftForge.EVENT_BUS.register(foragingModule);
	}
	
	private void clientSetup(final FMLClientSetupEvent event) {
		ItemBlockRenderTypes.setRenderLayer(ModBlocks.AGAVE_PLANT.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(ModBlocks.POTTED_AGAVE_PLANT.get(), RenderType.cutout());
	}

	private void setup(final FMLCommonSetupEvent event) {
		// some preinit code
		LOGGER.debug("HELLO FROM PREINIT");
		event.enqueueWork(() -> {
			((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.AGAVE_PLANT.getId(), ModBlocks.POTTED_AGAVE_PLANT);
		});
		foragingModule.setup();
//		LOGGER.debug("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
	}

	private void enqueueIMC(final InterModEnqueueEvent event) {
		// Some example code to dispatch IMC to another mod
//		InterModComms.sendTo("examplemod", "helloworld", () -> {
//			LOGGER.info("Hello world from the MDK");
//			return "Hello world";
//		});
	}

	private void processIMC(final InterModProcessEvent event) {
		// Some example code to receive and process InterModComms from other mods
		LOGGER.debug("Got IMC {}",
				event.getIMCStream().map(m -> m.messageSupplier().get()).collect(Collectors.toList()));
	}

	// You can use SubscribeEvent and let the Event Bus discover methods to call
	@SubscribeEvent
	public void onServerStarting(ServerStartingEvent event) {
		// Do something when the server starts
		LOGGER.debug("HELLO from server starting");
	}

	// You can use EventBusSubscriber to automatically subscribe events on the
	// contained class (this is subscribing to the MOD
	// Event bus for receiving Registry Events)
	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistryEvents {
		@SubscribeEvent
		public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
			// Register a new block here
			LOGGER.debug("HELLO from Register Block");
		}
	}



}
