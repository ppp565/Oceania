package oceania.entity;

import oceania.Oceania;
import cpw.mods.fml.common.registry.EntityRegistry;

public class Entities 
{
	
	public static void initEntities()
	{
		EntityRegistry.registerModEntity(EntityOceaniaBoatNormal.class, "OceaniaBoat", 0, Oceania.INSTANCE, 80, 3, true);
		EntityRegistry.registerModEntity(EntityOceaniaBoatWithChest.class, "OceaniaBoatWithChest", 1, Oceania.INSTANCE, 80, 3, true);
	}
	
}
