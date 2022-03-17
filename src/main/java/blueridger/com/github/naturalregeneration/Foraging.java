package blueridger.com.github.naturalregeneration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class Foraging {
	public Foraging() {
		dropsByTag.put(BlockTags.LEAVES, new Drops(25, true).add(Items.STICK, 1, 2).add(Items.FEATHER, 1, 2));

		dropsByBlock.put(Blocks.GRAVEL, new Drops(50, false).add(Items.FLINT, 1, 1));
		dropsByBlock.put(Blocks.TALL_GRASS,
				new Drops(30, true).add(Items.STRING, 1, 3).add(Items.WHEAT_SEEDS, 1, 3).add(Items.WHEAT, 1, 3));
		dropsByBlock.put(Blocks.GRASS,
				new Drops(50, true).add(Items.BEETROOT_SEEDS, 1, 3).add(Items.BEETROOT, 1, 3).add(Items.POTATO, 1, 3));
		dropsByBlock.put(Blocks.VINE, new Drops(30, true).add(Items.STRING, 1, 1).add(Items.VINE, 1, 2));
		dropsByBlock.put(Blocks.DEAD_BUSH, new Drops(6, true).add(Items.STICK, 1, 1).add(Items.ACACIA_PLANKS, 1, 8));
		dropsByBlock.put(Blocks.SAND, new Drops(50, false).add(Items.BONE, 1, 1));
		dropsByBlock.put(Blocks.CACTUS, new Drops(15, true).add(Items.APPLE, 1, 1));
		dropsByBlock.put(Blocks.OAK_LEAVES, new Drops(35, true).add(Items.APPLE, 1, 1));
	}

	private static final Logger LOGGER = LogUtils.getLogger();
	private static final Random random = new Random();

	private static final Map<TagKey<Block>, Drops> dropsByTag = new HashMap();
	private static final Map<Block, Drops> dropsByBlock = new HashMap();

	@SubscribeEvent
	public void foraging(PlayerInteractEvent.RightClickBlock event) {
		if (event.getEntityLiving().getLevel().isClientSide)
			return;

//		LOGGER.debug(event.getEntity().getLevel().getBlockState(event.getPos()).getTags().map(tag -> tag.toString())
//				.reduce("", (acc, tag) -> acc + ", " + tag.toString()));

		int moonPhase = event.getPlayer().getLevel().getMoonPhase();
		int positionHash = Math.abs(event.getPos().getX() + event.getPos().getY() + event.getPos().getZ());
		for (Map.Entry<TagKey<Block>, Drops> entry : dropsByTag.entrySet()) {
			if (event.getEntity().getLevel().getBlockState(event.getPos()).getTags()
					.anyMatch(Predicate.isEqual(entry.getKey()))) {
				LOGGER.debug("Found " + entry.getKey());
				ArrayList<ItemStack> result = entry.getValue().rollDrops(positionHash, moonPhase);
				if (result == null)
					continue;
				for (ItemStack itemStack : result) {
					event.getPlayer().drop(itemStack, true);
				}
			}
		}
		Drops blockDrops = dropsByBlock.get(event.getEntity().getLevel().getBlockState(event.getPos()).getBlock());
		if (blockDrops == null)
			return;
		ArrayList<ItemStack> result = blockDrops.rollDrops(positionHash, moonPhase);
		if (result == null)
			return;
		for (ItemStack itemStack : result) {
			event.getPlayer().drop(itemStack, true);
		}

	}

	protected class Drops {
		private class Drop {
			protected Item item;
			protected int count;
			protected int chance;

			protected Drop(Item item, int count, int chance) {
				this.item = item;
				this.count = count;
				this.chance = chance;
			}
		}

		private final ArrayList<Drop> drops;
		private final int hashMax;
		private final boolean moonAffected;
		private int hash;

		protected Drops(int hashMax, boolean moonAffected) {
			this.drops = new ArrayList();
			this.hashMax = hashMax;
			this.hash = 0;
			this.moonAffected = moonAffected;
		}

		protected Drops add(Item item, int count, int chance) {
			this.drops.add(new Drop(item, count, chance));
			return this;
		}

		/** Smaller return value means more likely to forage. */
		private static double moonPhaseToModifier(int moonPhase) {
			LOGGER.debug("moon phase " + moonPhase);
			switch (moonPhase) {
			case 0:
				return 0.75;
			case 1:
			case 7:
				return 1;
			case 2:
			case 6:
				return 1.25;
			case 3:
			case 5:
				return 1.5;
			default:
				return 2;

			}
		}

		protected ArrayList<ItemStack> rollDrops(int hashCheck, int moonPhase) {
			double moonModifier = moonPhaseToModifier(moonPhase);
			int modifiedHashMax = (int) Math.ceil(moonModifier * this.hashMax);

			LOGGER.debug("checking " + (hashCheck % this.hashMax) + " against hash " + hash + ", max " + this.hashMax
					+ ". Modified to " + (hashCheck % modifiedHashMax) + " against " + (this.hash % modifiedHashMax)
					+ ", max " + modifiedHashMax);

			if ((hashCheck % modifiedHashMax) != (this.hash % modifiedHashMax))
				return null;
			this.hash = random.nextInt(this.hashMax);
			ArrayList<ItemStack> result = new ArrayList();
			for (Drop drop : this.drops) {
				if (Utils.oneIn(drop.chance)) {
					result.add(new ItemStack(drop.item, drop.count));
				}
			}
			return result;

		}
	}

}
