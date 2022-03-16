package blueridger.com.github.naturalregeneration.Item;

import blueridger.com.github.naturalregeneration.Item.custom.AnimalFat;

import java.util.Random;

import blueridger.com.github.naturalregeneration.NaturalRegeneration;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

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
	
	private static int zeroTo(int i) {
		return random.nextInt(i + 1);
	}
	
	private static int oneTo(int i) {
		return random.nextInt(i) + 1;
	}

	@SubscribeEvent
	public void additionalDrops(LivingDropsEvent event) {
		
		if (event.getEntityLiving().getLevel().isClientSide)
			return;
		switch (event.getEntityLiving().getType().toShortString()) {
		
		case "polar_bear":
		case "panda":
			addDrop(event, new ItemStack(ModItems.ANIMAL_FAT.get(), oneTo(4)));
			addDrop(event, new ItemStack(Items.BONE, oneTo(4)));
			addDrop(event, new ItemStack(Items.LEATHER, zeroTo(1)));
			addDrop(event, new ItemStack(Items.STRING, zeroTo(2)));
			break;
			
		case "cow":
			addDrop(event, new ItemStack(ModItems.ANIMAL_FAT.get(), zeroTo(1)));
			addDrop(event, new ItemStack(Items.BONE, oneTo(2)));
			addDrop(event, new ItemStack(Items.STRING, zeroTo(2)));
			break;

		case "horse":
		case "donkey":
		case "mule":
			addDrop(event, new ItemStack(Items.BONE, zeroTo(2)));
			addDrop(event, new ItemStack(Items.STRING, zeroTo(2)));
			break;
			
		case "llama":
			addDrop(event, new ItemStack(Items.BONE, zeroTo(2)));
			addDrop(event, new ItemStack(Items.STRING, zeroTo(2)));
			addDrop(event, new ItemStack(Items.YELLOW_WOOL, zeroTo(1)));
			break;
			
		case "pig":
			addDrop(event, new ItemStack(ModItems.ANIMAL_FAT.get(), oneTo(3)));
			break;
			
		case "goat":
			addDrop(event, new ItemStack(Items.BONE, zeroTo(1)));
			break;
			
		case "turtle":
			addDrop(event, new ItemStack(Items.BOWL, zeroTo(1)));
			break;
		}
	}
}
