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

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;

import com.theisleoffavalon.mcmanager.network.handler.JsonRpcHandler;
import com.theisleoffavalon.mcmanager.network.handler.RegExContextHandler;
import com.theisleoffavalon.mcmanager.network.handler.RegExContextHandlerCollection;
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
	 * The handler for the root context.
	 */
	private Handler rootHandler;
	
	/**
	 * The JSON RPC handler.
	 */
	private Handler rpcHandler;
	
	/**
	 * Creates an instance of the web server but does not actually start
	 * it. To start it you must call the {@link start} method.
	 * 
	 * @throws IOException thrown when the web socket could not be created and bound
	 */
	public WebServer() throws IOException
	{
		// TODO: set this up to use HTTPS instead when requested
		// TODO: change the port value to be a configuration setting
		webServer = new Server(1716);
		webServer.setGracefulShutdown(STOP_WAIT_TIME);
		
		// TODO: change the number of threads to be a configuration setting
		webServer.setThreadPool(null);
		
		handlers = new RegExContextHandlerCollection();
		webServer.setHandler(handlers);
		
		rootHandler = new RootHttpHandler();
		addHandler("/", rootHandler);
		
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
