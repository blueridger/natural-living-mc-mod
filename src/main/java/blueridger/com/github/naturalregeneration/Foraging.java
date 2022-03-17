package blueridger.com.github.naturalregeneration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Predicate;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags.Blocks;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class Foraging {
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
		private final int nonceMax;
		private int nonce;

		protected Drops(int nonceMax) {
			this.drops = new ArrayList();
			this.nonceMax = nonceMax;
			this.nonce = 0;
		}

		protected Drops add(Item item, int count, int chance) {
			this.drops.add(new Drop(item, count, chance));
			return this;
		}

		protected ArrayList<ItemStack> rollDrops(int nonceCheck) {
			LOGGER.debug("checking " + (nonceCheck % this.nonceMax) + " against nonce " + nonce);
			if (nonceCheck % this.nonceMax != this.nonce)
				return null;
			LOGGER.debug("nonce check succeeded");
			this.nonce = random.nextInt(this.nonceMax);
			ArrayList<ItemStack> result = new ArrayList();
			for (Drop drop : this.drops) {
				if (Utils.oneIn(drop.chance)) {
					LOGGER.debug("adding drop");
					result.add(new ItemStack(drop.item, drop.count));
				}
			}
			return result;

		}
	}

	private static final Logger LOGGER = LogUtils.getLogger();
	private static final Random random = new Random();

	private static final Map<TagKey<Block>, Drops> dropsByTag = new HashMap();

	public Foraging() {
		dropsByTag.put(BlockTags.LEAVES, new Drops(40).add(Items.STICK, 2, 2));
		dropsByTag.put(Blocks.GRAVEL, new Drops(80).add(Items.FLINT, 1, 1));
	}

	@SubscribeEvent
	public void foraging(PlayerInteractEvent.RightClickBlock event) {
		if (event.getEntityLiving().getLevel().isClientSide)
			return;

		for (Map.Entry<TagKey<Block>, Drops> entry : dropsByTag.entrySet()) {
			if (event.getEntity().getLevel().getBlockState(event.getPos()).getTags()
					.anyMatch(Predicate.isEqual(entry.getKey()))) {
				LOGGER.debug("Found " + entry.getKey());
				ArrayList<ItemStack> result = entry.getValue()
						.rollDrops(event.getPos().getX() + event.getPos().getY() + event.getPos().getZ());
				if (result == null)
					return;
				for (ItemStack itemStack : result) {
					event.getPlayer().drop(itemStack, true);
				}
			}
		}
	}

}
