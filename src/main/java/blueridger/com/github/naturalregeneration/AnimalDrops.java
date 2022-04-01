package blueridger.com.github.naturalregeneration;

import java.util.Random;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import blueridger.com.github.naturalregeneration.Item.ModItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class AnimalDrops {
	private static final Random random = new Random();
	private static final Logger LOGGER = LogUtils.getLogger();

	private static void addDrop(LivingDropsEvent event, ItemStack itemStack, int chance) {
		if (!Utils.oneIn(chance))
			return;
		Entity ent = event.getEntityLiving();
		event.getDrops().add(new ItemEntity(ent.getLevel(), ent.getX(), ent.getY(), ent.getZ(), itemStack));
	}

	@SubscribeEvent
	public void additionalAnimalDrops(LivingDropsEvent event) {

		if (event.getEntityLiving().getLevel().isClientSide)
			return;
		LOGGER.debug("Checking drops for " + event.getEntityLiving().getType().getRegistryName().toString());
		
		switch (event.getEntityLiving().getType().getRegistryName().toString()) {

		case "minecraft:polar_bear":
		case "minecraft:panda":
			addDrop(event, new ItemStack(ModItems.ANIMAL_FAT.get(), Utils.oneTo(4)), 1);
			addDrop(event, new ItemStack(Items.BONE, Utils.oneTo(4)), 1);
			addDrop(event, new ItemStack(Items.LEATHER, Utils.oneTo(2)), 1);
			addDrop(event, new ItemStack(Items.STRING, Utils.oneTo(4)), 1);
			break;

		case "minecraft:cow":
			addDrop(event, new ItemStack(ModItems.ANIMAL_FAT.get(), 1), 1);
			addDrop(event, new ItemStack(Items.BONE, Utils.oneTo(4)), 1);
			addDrop(event, new ItemStack(Items.STRING, Utils.oneTo(4)), 1);
			break;

		case "minecraft:horse":
		case "minecraft:donkey":
		case "minecraft:mule":
			addDrop(event, new ItemStack(Items.BONE, Utils.oneTo(4)), 1);
			addDrop(event, new ItemStack(Items.STRING, Utils.oneTo(4)), 1);
			break;

		case "minecraft:llama":
			addDrop(event, new ItemStack(Items.BONE, Utils.oneTo(4)), 1);
			addDrop(event, new ItemStack(Items.STRING, Utils.oneTo(4)), 1);
			addDrop(event, new ItemStack(Items.YELLOW_WOOL, 1), 2);
			break;

		case "minecraft:pig":
			addDrop(event, new ItemStack(ModItems.ANIMAL_FAT.get(), Utils.oneTo(4)), 1);
			addDrop(event, new ItemStack(Items.BONE, Utils.oneTo(2)), 1);
			addDrop(event, new ItemStack(Items.STRING, 1), 2);
			break;

		case "minecraft:chicken":
			addDrop(event, new ItemStack(Items.STRING, 1), 2);
			break;

		case "minecraft:goat":
			addDrop(event, new ItemStack(Items.BONE, Utils.oneTo(2)), 2);
			addDrop(event, new ItemStack(Items.STRING, 1), 2);
			break;

		case "minecraft:turtle":
			addDrop(event, new ItemStack(Items.MUSHROOM_STEW, 1), 1);
			break;
		}
	}
}
