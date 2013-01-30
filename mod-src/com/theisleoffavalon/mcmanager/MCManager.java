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

import java.io.IOException;

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
	 * The web server instance.
	 */
	private WebServer webServer;
	
	/**
	 * Called when the mod is in the pre-initialization phase.
	 * 
	 * @param event - the event information
	 * @throws IOException thrown when the web server can't start
	 */
	@PreInit
	public void preInit(FMLPreInitializationEvent event) throws IOException
	{
		LogHelper.info("Pre-initializing...");
		
		// TODO: pre-init
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
		
		webServer = proxy.createWebServer();
		if(webServer == null)
			LogHelper.warning("Could not create the web server. Is this a client? If so this is a server mod only.");
	}
	
	/**
	 * Called when the mod is in the post-initialization phase.
	 * 
	 * @param event - the event information
	 */
	@PostInit
	public void postInit(FMLPostInitializationEvent event)
	{
		if(webServer != null)
			webServer.startServer();
		
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
		
		if(webServer != null)
			webServer.stopServer();
		
		LogHelper.info("MCManager stopped.");
	}
}
