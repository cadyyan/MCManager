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
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
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
	 * The server instance.
	 */
	private MinecraftServer server;

	/**
	 * The mod config directory.
	 */
	private File configDir;

	/**
	 * The main mod configuration.
	 */
	private Configuration coreConfig;

	/**
	 * The flag saying if the web server should be enabled.
	 */
	private boolean webServerEnabled;

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
	 * The player manager.
	 */
	private PlayerManager playerManager;

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

		// TODO: find a way to make this not work client side.
		//if(proxy instanceof MCManagerClientProxy)
		//	throw new RuntimeException("This is a server-side only mod.");

		server = MinecraftServer.getServer();

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
	@EventHandler
	public void load(FMLInitializationEvent event) throws IOException
	{
		LogHelper.info("Initializing...");

		webServerEnabled = coreConfig.get(WebServer.WEBSERVER_CONFIG_CATEGORY, "enable", true).getBoolean(true);

		if(webServerEnabled)
			webServer = new WebServer(coreConfig);
		serverMonitor = new ServerMonitor();
		consoleMonitor = new ConsoleMonitor();
		commandManager = new CommandManager();
		playerManager = new PlayerManager();

		commandManager.registerCommands();
	}

	/**
	 * Called when the mod is in the post-initialization phase.
	 *
	 * @param event - the event information
	 */
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		consoleMonitor.startLogging();

		if(webServerEnabled)
		{
			webServer.startServer();
			webServer.getRpcHandler().addHandler(serverMonitor);
			webServer.getRpcHandler().addHandler(consoleMonitor);
			webServer.getRpcHandler().addHandler(commandManager);
			webServer.getRpcHandler().addHandler(playerManager);
		}

		coreConfig.save();

		LogHelper.info("Finished initializing!");
	}

	/**
	 * Called when the server is stopped.
	 *
	 * @param event - the event information
	 */
	@EventHandler
	public void shutdown(FMLServerStoppedEvent event)
	{
		LogHelper.info("Stopping MCManager...");

		if(webServerEnabled)
			webServer.stopServer();
		consoleMonitor.stopLogging();

		LogHelper.info("MCManager stopped.");
	}

	/**
	 * Gets the server instance that is running this mod.
	 *
	 * @return the server
	 */
	public MinecraftServer getServer()
	{
		return server;
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

