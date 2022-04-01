package blueridger.com.github.naturalregeneration.entity.custom;

import com.mojang.math.Vector3d;

import blueridger.com.github.naturalregeneration.entity.ModEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ShipEntity extends Boat {

	public ShipEntity(EntityType<? extends Boat> p_38290_, Level p_38291_) {
		super(p_38290_, p_38291_);
		// TODO Auto-generated constructor stub
	}
	
	public ShipEntity(Level worldIn, double x, double y, double z) {
        this(ModEntities.SHIP.get(), worldIn);
        this.setPos(x, y, z);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    @Override
    public Item getItemBoat() {
        return 
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(ForgeRegistries.ITEMS.getValue(
                new ResourceLocation(TutorialMod.MOD_ID, this.getWoodType() + "_boat")));
    }

    @Nonnull
    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
	
}
