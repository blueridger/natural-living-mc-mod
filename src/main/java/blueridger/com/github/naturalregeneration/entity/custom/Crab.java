package blueridger.com.github.naturalregeneration.entity.custom;

import blueridger.com.github.naturalregeneration.entity.ModEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

public class Crab extends Animal {

	public Crab(EntityType<? extends Animal> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob parent) {
		return ModEntities.CRAB.get().create(level);
	}
	
	@Override
	protected void registerGoals() {
	      this.goalSelector.addGoal(0, new BreedGoal(this, 1.0D));
	      this.goalSelector.addGoal(1, new TemptGoal(this, 2.0D, Ingredient.of(Items.CHICKEN), false));
	      this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1.0D));
	}
	
	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 4.0D).add(Attributes.MOVEMENT_SPEED, 0.3D).add(Attributes.ATTACK_DAMAGE, 2.0D);
	}
	
}
