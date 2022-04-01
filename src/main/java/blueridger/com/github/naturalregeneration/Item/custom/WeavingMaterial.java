package blueridger.com.github.naturalregeneration.Item.custom;

import javax.annotation.Nullable;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;

public class WeavingMaterial extends Item {

	public WeavingMaterial(Properties pProperties) {
		super(pProperties);
	}
	
	@Override
	public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
		return 100;
	}

}
