package blueridger.com.github.naturalregeneration.Item;

import java.util.function.Supplier;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;

public class ModTiers {
	public static final ForgeTier BONE = new ForgeTier(2, 1000, 1.5f, 2f, 22, BlockTags.NEEDS_STONE_TOOL,
			(Supplier<Ingredient>) () -> Ingredient.of(Items.BONE));
}
