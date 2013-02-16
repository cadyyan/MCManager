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

package com.theisleoffavalon.mcmanager.network;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import net.minecraftforge.common.Configuration;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.session.HashSessionIdManager;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.util.thread.ExecutorThreadPool;
import org.eclipse.jetty.util.thread.ThreadPool;

import com.theisleoffavalon.mcmanager.network.handler.JsonRpcHandler;
import com.theisleoffavalon.mcmanager.network.handler.RegExContextHandler;
import com.theisleoffavalon.mcmanager.network.handler.RegExContextHandlerCollection;
import com.theisleoffavalon.mcmanager.network.handler.ResourceHandler;
import com.theisleoffavalon.mcmanager.network.handler.RootHttpHandler;
import com.theisleoffavalon.mcmanager.util.LogHelper;

/**
 * Handles network connections over HTTP. This properly dispatches web requests to
 * the correct URI handler. URI handlers can also be registered by other mods.
 * 
 * @author Cadyyan
 *
 */
public class WebServer
{
	/**
	 * The web server configuration category.
	 */
	public static String WEBSERVER_CONFIG_CATEGORY = "webserver";
	
	/**
	 * The number of seconds to wait for requests before stopping the web server.
	 */
	private static final int STOP_WAIT_TIME = 2;
	
	/**
	 * The HTTP web server.
	 */
	private Server webServer;
	
	/**
	 * The collection of handlers that are able to handle requests for the web server.
	 */
	private HandlerCollection handlers;
	
	/**
	 * The session handler.
	 */
	private SessionHandler sessionHandler;
	
	/**
	 * The handler for the root context.
	 */
	private Handler rootHandler;
	
	/**
	 * The resource handler.
	 */
	private Handler resourceHandler;
	
	/**
	 * The JSON RPC handler.
	 */
	private Handler rpcHandler;
	
	/**
	 * Creates an instance of the web server but does not actually start
	 * it. To start it you must call the {@link start} method.
	 * 
	 * @param config - the mod's core config
	 * 
	 * @throws IOException thrown when the web socket could not be created and bound
	 */
	public WebServer(Configuration config) throws IOException
	{
		// TODO: set this up to use HTTPS instead when requested
		webServer = new Server(config.get(WEBSERVER_CONFIG_CATEGORY, "port", 1716).getInt());
		webServer.setGracefulShutdown(STOP_WAIT_TIME);
		webServer.setSessionIdManager(new HashSessionIdManager());
		
		int maxConnections = config.get(WEBSERVER_CONFIG_CATEGORY, "max-sessions", 20).getInt();
		if(maxConnections < 2)
		{
			LogHelper.warning("The selected number of minimum connections allowed is too low. Using low default instead.");
			maxConnections = 2;
		}
		
		LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(maxConnections);
		ThreadPool tp = new ExecutorThreadPool(2, maxConnections, 60, TimeUnit.SECONDS, queue);
		webServer.setThreadPool(tp);
		
		handlers = new RegExContextHandlerCollection();
		webServer.setHandler(handlers);
		
		sessionHandler = new SessionHandler();
		sessionHandler.getSessionManager().setSessionIdManager(webServer.getSessionIdManager());
		addHandler("/", sessionHandler);
		
		rootHandler = new RootHttpHandler();
		sessionHandler.setHandler(rootHandler);
		
		resourceHandler = new ResourceHandler();
		addHandler("^/resources/.*$", resourceHandler);
		
		rpcHandler = new JsonRpcHandler();
		addHandler("/rpc/*", rpcHandler);
	}
	
	/**
	 * Starts the web server to handle requests.
	 */
	public void startServer()
	{
		try
		{
			webServer.start();
		}
		catch(Exception e)
		{
			LogHelper.error("Unable to start web server.\n" +
							e.getMessage());
		}
	}
	
	/**
	 * Stops the web server from handling requests.
	 */
	public void stopServer()
	{
		try
		{
			webServer.stop();
			webServer.join();
		}
		catch(Exception e)
		{
			LogHelper.error("Unable to start web server.\n" +
							e.getMessage());
		}
	}
	
	/**
	 * Adds a web request handler for a given web context.
	 * 
	 * @param context - the web context this handler listens on
	 * @param handler - the request handler
	 */
	public void addHandler(String context, Handler handler)
	{
		RegExContextHandler wrapper = new RegExContextHandler(context, handler);
		
		handlers.addHandler(wrapper);
	}
	
	/**
	 * Gets the JSON RPC handler.
	 * 
	 * @return the JSON RPC handler
	 */
	public JsonRpcHandler getRpcHandler()
	{
		return (JsonRpcHandler)rpcHandler;
	}
}
