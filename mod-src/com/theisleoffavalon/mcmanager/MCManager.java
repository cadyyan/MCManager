package com.theisleoffavalon.mcmanager;

import com.theisleoffavalon.mcmanager.proxy.MCManagerProxy;
import com.theisleoffavalon.mcmanager.util.LogHelper;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ModReference.ID, name = ModReference.NAME, version = ModReference.VERSION)
public class MCManager
{
	/**
	 * The mod instance. This is required by Forge. Plus it just makes getting mod resources easier. This is assigned by Forge
	 * at runtime.
	 */
	@Instance
	public static MCManager instance;
	
	/**
	 * The proxy class. This is created by Forge at runtime.
	 */
	@SidedProxy(clientSide = ModReference.CLIENT_PROXY, serverSide = ModReference.SERVER_PROXY)
	public MCManagerProxy proxy;
	
	/**
	 * Called when the mod is in the pre-initialization phase.
	 * 
	 * @param event - the event information
	 */
	@PreInit
	public void preInit(FMLPreInitializationEvent event)
	{
		LogHelper.info("Pre-initializing...");
		
		// TODO: pre-init
	}
	
	/**
	 * Called when the mod is in the initialization phase.
	 * 
	 * @param event - the event information
	 */
	@Init
	public void init(FMLInitializationEvent event)
	{
		LogHelper.info("Initializing...");
		
		// TODO: init
	}
	
	/**
	 * Called when the mod is in the post-initialization phase.
	 * 
	 * @param event - the event information
	 */
	@PostInit
	public void postInit(FMLPostInitializationEvent event)
	{
		// TODO: post-init
		
		LogHelper.info("Finished initializing!");
	}
}
