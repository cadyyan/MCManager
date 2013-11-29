package com.theisleoffavalon.mcmanager;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import net.minecraftforge.common.Configuration;

import com.theisleoffavalon.mcmanager.network.PacketHandler;
import com.theisleoffavalon.mcmanager.proxy.MCManagerProxy;
import com.theisleoffavalon.mcmanager.util.LogHelper;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;

/**
 * The main class for the Forge mod.
 *
 * @author Cadyyan
 *
 */
@Mod(modid = ModReference.ID, name = ModReference.NAME, version = ModReference.VERSION)
@NetworkMod(channels = {},
			clientSideRequired = false, serverSideRequired = true,
			packetHandler = PacketHandler.class)
public class MCManager
{
	/**
	 * The mod instance. This is required by Forge. Plus it just makes getting mod resources easier. This is assigned by Forge
	 * at runtime.
	 */
	@Instance("MCManager")
	public static MCManager instance;

	/**
	 * The proxy class. This is created by Forge at runtime.
	 */
	@SidedProxy(clientSide = ModReference.CLIENT_PROXY, serverSide = ModReference.SERVER_PROXY)
	public static MCManagerProxy proxy;

	/**
	 * The mod config directory.
	 */
	private File configDir;

	/**
	 * The main mod configuration.
	 */
	private Configuration coreConfig;

	/**
	 * Called when the mod is in the pre-initialization phase.
	 *
	 * @param event - the event information
	 * @throws IOException thrown on one of the many IO errors
	 */
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) throws IOException
	{
		LogHelper.info("Pre-initializing...");

		this.configDir = new File(event.getSuggestedConfigurationFile().getParent(), "MCManager");
		if(!configDir.exists())
			configDir.mkdir();

		this.coreConfig = new Configuration(new File(configDir, "MCManager.cfg"));

		try
		{
			coreConfig.load();
		}
		catch (Exception e)
		{
			LogHelper.severe("There was a problem when trying to load the configuration.");
		}
		finally
		{
			if (coreConfig.hasChanged())
				coreConfig.save();
		}
	}

	/**
	 * Called when the mod is in the initialization phase.
	 *
	 * @param event - the event information
	 * @throws IOException thrown when the web server can't start
	 */
	@EventHandler
	public void load(FMLInitializationEvent event) throws IOException
	{
		LogHelper.info("Initializing...");
	}

	/**
	 * Called when the mod is in the post-initialization phase.
	 *
	 * @param event - the event information
	 */
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		LogHelper.info("Finished initializing!");
	}

	/**
	 * Gets the configuration directory for the mod.
	 *
	 * @return the configuration directory
	 */
	public File getConfigDir()
	{
		return configDir;
	}

	/**
	 * Gets the core config for the mod.
	 *
	 * @return the core configuration
	 */
	public Configuration getCoreConfig()
	{
		return coreConfig;
	}
}

