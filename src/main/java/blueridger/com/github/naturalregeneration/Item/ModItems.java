package blueridger.com.github.naturalregeneration.Item;

import blueridger.com.github.naturalregeneration.Item.custom.AnimalFat;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import blueridger.com.github.naturalregeneration.NaturalRegeneration;
import blueridger.com.github.naturalregeneration.Utils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
	private static final Logger LOGGER = LogUtils.getLogger();

	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
			NaturalRegeneration.MODID);

	public static final RegistryObject<Item> ANIMAL_FAT = ITEMS.register("animal_fat",
			() -> new AnimalFat(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

	public static void register(IEventBus eventBus) {
		ITEMS.register(eventBus);
	}

	private static final Random random = new Random();

	private static void addDrop(LivingDropsEvent event, ItemStack itemStack) {
		Entity ent = event.getEntityLiving();
		event.getDrops().add(new ItemEntity(ent.getLevel(), ent.getX(), ent.getY(), ent.getZ(), itemStack));
	}
	

	@SubscribeEvent
	public void additionalAnimalDrops(LivingDropsEvent event) {

		if (event.getEntityLiving().getLevel().isClientSide)
			return;
		switch (event.getEntityLiving().getType().toShortString()) {

		case "polar_bear":
		case "panda":
			addDrop(event, new ItemStack(ModItems.ANIMAL_FAT.get(), Utils.oneTo(4)));
			addDrop(event, new ItemStack(Items.BONE, Utils.oneTo(4)));
			addDrop(event, new ItemStack(Items.LEATHER, Utils.zeroTo(1)));
			addDrop(event, new ItemStack(Items.STRING, Utils.zeroTo(2)));
			break;

		case "cow":
			addDrop(event, new ItemStack(ModItems.ANIMAL_FAT.get(), Utils.zeroTo(1)));
			addDrop(event, new ItemStack(Items.BONE, Utils.oneTo(2)));
			addDrop(event, new ItemStack(Items.STRING, Utils.zeroTo(2)));
			break;

		case "horse":
		case "donkey":
		case "mule":
			addDrop(event, new ItemStack(Items.BONE, Utils.zeroTo(2)));
			addDrop(event, new ItemStack(Items.STRING, Utils.zeroTo(2)));
			break;

		case "llama":
			addDrop(event, new ItemStack(Items.BONE, Utils.zeroTo(2)));
			addDrop(event, new ItemStack(Items.STRING, Utils.zeroTo(2)));
			addDrop(event, new ItemStack(Items.YELLOW_WOOL, Utils.zeroTo(1)));
			break;

		case "pig":
			addDrop(event, new ItemStack(ModItems.ANIMAL_FAT.get(), Utils.oneTo(3)));
			break;

		case "goat":
			addDrop(event, new ItemStack(Items.BONE, Utils.zeroTo(1)));
			break;

		case "turtle":
			addDrop(event, new ItemStack(Items.BOWL, Utils.zeroTo(1)));
			break;
		}
	}

	@SubscribeEvent
	public void additionalDrops(BlockEvent.BreakEvent event) {

		if (event.getWorld().isClientSide())
			return;
//		LOGGER.debug("d " + event.getState().get);
//		LOGGER.debug(event.getState().getBlockState().getDescriptionId());
//		switch (event.getState().getBlock().getRegistryType().descriptorString()) {
//		
//		case "polar_bear":
//		case "panda":
//			addDrop(event, new ItemStack(ModItems.ANIMAL_FAT.get(), oneTo(4)));
	}

}
