/*
 * DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *                    Version 2, December 2004
 * 
 * Copyright (C) 2004 Sam Hocevar <sam@hocevar.net>
 * 
 * Everyone is permitted to copy and distribute verbatim or modified
 * copies of this license document, and changing it is allowed as long
 * as the name is changed.
 * 
 *            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *   TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
 * 
 * 0. You just DO WHAT THE FUCK YOU WANT TO.
 */

package com.theisleoffavalon.mcmanager;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.Configuration;

import com.theisleoffavalon.mcmanager.network.WebServer;
import com.theisleoffavalon.mcmanager.proxy.MCManagerProxy;
import com.theisleoffavalon.mcmanager.util.LogHelper;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerStopped;
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
	 * The web server instance.
	 */
	private WebServer webServer;
	
	/**
	 * The server monitor.
	 */
	private ServerMonitor serverMonitor;
	
	/**
	 * The chat monitor.
	 */
	private ConsoleMonitor consoleMonitor;
	
	/**
	 * The command manager.
	 */
	private CommandManager commandManager;
	
	/**
	 * Called when the mod is in the pre-initialization phase.
	 * 
	 * @param event - the event information
	 * @throws IOException thrown on one of the many IO errors
	 */
	@PreInit
	public void preInit(FMLPreInitializationEvent event) throws IOException
	{
		LogHelper.info("Pre-initializing...");
		
		// TODO: find a way to make this not work client side.
		//if(proxy instanceof MCManagerClientProxy)
		//	throw new RuntimeException("This is a server-side only mod.");
		
		configDir = new File(event.getSuggestedConfigurationFile().getParent() + "/MCManager");
		if(!configDir.exists())
			configDir.mkdir();
		
		coreConfig = new Configuration(new File(configDir, "MCManager.cfg"));
	}
	
	/**
	 * Called when the mod is in the initialization phase.
	 * 
	 * @param event - the event information
	 * @throws IOException thrown when the web server can't start
	 */
	@Init
	public void init(FMLInitializationEvent event) throws IOException
	{
		LogHelper.info("Initializing...");
		
		webServer = new WebServer(coreConfig);
		serverMonitor = new ServerMonitor();
		consoleMonitor = new ConsoleMonitor();
		commandManager = new CommandManager();
	}
	
	/**
	 * Called when the mod is in the post-initialization phase.
	 * 
	 * @param event - the event information
	 */
	@PostInit
	public void postInit(FMLPostInitializationEvent event)
	{
		consoleMonitor.startLogging();
		
		webServer.startServer();
		webServer.getRpcHandler().addHandler(serverMonitor);
		webServer.getRpcHandler().addHandler(consoleMonitor);
		webServer.getRpcHandler().addHandler(commandManager);
		
		coreConfig.save();
		
		LogHelper.info("Finished initializing!");
	}
	
	/**
	 * Called when the server is stopped.
	 * 
	 * @param event - the event information
	 */
	@ServerStopped
	public void shutdown(FMLServerStoppedEvent event)
	{
		LogHelper.info("Stopping MCManager...");
		
		webServer.stopServer();
		consoleMonitor.stopLogging();
		
		LogHelper.info("MCManager stopped.");
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
