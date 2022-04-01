package blueridger.com.github.naturalregeneration.Item;

import blueridger.com.github.naturalregeneration.Item.custom.AnimalFat;
import blueridger.com.github.naturalregeneration.Item.custom.ShipItem;
import blueridger.com.github.naturalregeneration.Item.custom.WeavingMaterial;
import blueridger.com.github.naturalregeneration.NaturalRegeneration;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.world.entity.vehicle.Boat;

public class ModItems {

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
			NaturalRegeneration.MODID);

	public static final RegistryObject<Item> ANIMAL_FAT = ITEMS.register("animal_fat",
			() -> new AnimalFat(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

	public static final RegistryObject<Item> WEAVING_MATERIAL = ITEMS.register("weaving_material",
			() -> new WeavingMaterial(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

	public static final RegistryObject<Item> BONE_KNIFE = ITEMS.register("bone_knife",
			() -> new SwordItem(ModTiers.BONE, 2, -1f, new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));

//	public static final RegistryObject<Item> SHIP = ITEMS.register("ship",
//			() -> new ShipItem(Boat.Type.ACACIA, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

	public static void register(IEventBus eventBus) {
		ITEMS.register(eventBus);
	}
}
