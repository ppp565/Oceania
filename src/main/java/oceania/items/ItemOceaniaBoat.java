package oceania.items;

import java.util.List;

import oceania.Oceania;
import oceania.entity.EntityOceaniaBoat;
import oceania.entity.EntityOceaniaBoatNormal;
import oceania.entity.EntitySubmarine;
import oceania.items.ItemMulti.ItemMultiType;
import oceania.util.BoatType;
import oceania.util.IconRegistry;
import oceania.util.OUtil;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBoat;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemOceaniaBoat extends ItemBoat 
{

	public ItemOceaniaBoat(int id) 
	{
		super(id);
		setCreativeTab(Oceania.CREATIVE_TAB);
		this.initLangNames();
	}
	
	public void initLangNames()
	{
		for(BoatType boat : BoatType.values()) 
		{
			LanguageRegistry.instance().addStringLocalization("item." + boat.unloc + ".name", boat.loc);
		}
	}
	
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        float pitch = player.rotationPitch;
        float yaw = player.rotationYaw;
        double pX = player.posX;
        double pY = player.posY;
        double pZ = player.posZ;
        Vec3 playerPos = world.getWorldVec3Pool().getVecFromPool(pX, pY, pZ);
        float yawOffZ = MathHelper.cos((-yaw * OUtil.PI_OVER_180) - (float) Math.PI);
        float yawOffX = MathHelper.sin((-yaw * OUtil.PI_OVER_180) - (float) Math.PI);
        float pitchOffZ = -MathHelper.cos((-pitch) * OUtil.PI_OVER_180);
        float pitchOffY = MathHelper.sin((-pitch) * OUtil.PI_OVER_180);
        float offsetX = yawOffX * pitchOffZ;
        float offsetZ = yawOffZ * pitchOffZ;
        double radius = 5.0;
        Vec3 offset = playerPos.addVector((double)offsetX * radius, (double)pitchOffY * radius, (double)offsetZ * radius);
        MovingObjectPosition hitPos = world.clip(playerPos, offset, true);

        if (hitPos == null)
        {
            return stack;
        }
        else if (hitPos.typeOfHit == EnumMovingObjectType.TILE)
        {
        	int bX = hitPos.blockX;
        	int bY = hitPos.blockY;
        	int bZ = hitPos.blockZ;
        	int bID = world.getBlockId(bX, bY, bZ);
        	
        	if (bID == Block.waterStill.blockID || bID == Block.waterMoving.blockID)
        	{
        		EntityOceaniaBoatNormal boat = new EntityOceaniaBoatNormal(world, bX + 0.5, bY + 1.0, bZ + 0.5);
                boat.rotationYaw = (float)(((MathHelper.floor_double((double)(player.rotationYaw * 4.0f / 360.0f) + 0.5) & 3) - 1) * 90);
                boat.setBoatType(BoatType.values()[stack.getItemDamage()]);
                //boat.setOwner(player.getEntityName());
                
        		if (!world.getCollidingBoundingBoxes(boat, boat.boundingBox.expand(-0.1D, -0.1D, -0.1D)).isEmpty())
                    return stack;
        		if (!world.isRemote)
        		{
        			world.spawnEntityInWorld(boat);
        		}
        		if (!player.capabilities.isCreativeMode)
        			stack.stackSize--;
        	}
        }
    	
    	return stack;
    }
		
	@Override
	@SideOnly(Side.CLIENT)
    public String getUnlocalizedName(ItemStack stack) 
	{
		return "item." + BoatType.values()[stack.getItemDamage()].unloc;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister registry)
	{
		for(int index = 0; index < BoatType.values().length; index++) 
		{
			IconRegistry.setIcon(BoatType.values()[index].unloc, registry.registerIcon(BoatType.values()[index].namespace + ":" + BoatType.values()[index].unloc));
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public Icon getIconFromDamage(int meta)
	{
		return IconRegistry.getIcon(BoatType.values()[meta].unloc);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void getSubItems(int ID, CreativeTabs tabs, List list) 
	{
		for(int index = 0; index < BoatType.values().length - 1 /* Don't include the submarine */; index++) 
		{
			list.add(new ItemStack(ID, 1, index));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List descriptionList, boolean noClueWhatThisEvenDoes)
	{
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
		{
			switch(itemStack.getItemDamage())
			{
			case 0:
				descriptionList.add(OUtil.colorString("A boat made of &&esturdy planks&&7."));
				descriptionList.add("Doubt it will last long.");
				break;
			case 1:
				descriptionList.add(OUtil.colorString("A boat made of &&fIron&&7, should"));
				descriptionList.add("last a while.");
				break;
			case 2: 
				descriptionList.add(OUtil.colorString("Made of purely &&6Atlantium&&7,"));
				descriptionList.add("almost indestructable.");
			}
		}
		else
		{
			descriptionList.add(OUtil.colorString("Hold &&9SHIFT &&7for more information"));
		}
	}

}
