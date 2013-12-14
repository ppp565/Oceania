package oceania.items;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.oredict.OreDictionary;
import oceania.Oceania;

public class Items
{
	public static final int DEFAULT_ID_RANGE = 3750;
	
	public static ItemDivingHelmet itemDivingHelmet;
	public static ItemMulti itemMulti;
	
	public static void initItems()
	{
		Configuration cfg = Oceania.CONFIG;
		
		/** Set up instances */
		itemDivingHelmet = new ItemDivingHelmet(cfg.getItem("itemDivingHelmet", DEFAULT_ID_RANGE).getInt(DEFAULT_ID_RANGE));
		itemMulti = new ItemMulti(cfg.getItem("itemMulti", DEFAULT_ID_RANGE + 1).getInt(DEFAULT_ID_RANGE + 1));
		
		/** Add to registry */
		GameRegistry.registerItem(itemDivingHelmet, Oceania.MOD_ID + "itemDivingHelmet");
		GameRegistry.registerItem(itemMulti, Oceania.MOD_ID + "itemMulti");
		
		/** Ore Dictionary support */
		OreDictionary.registerOre("oceaniaCraftingItems", itemMulti);
		
		/** Add language */
		LanguageRegistry.addName(itemDivingHelmet, "Atlantium Helm");
	}
}