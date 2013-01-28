package com.theisleoffavalon.mcmanager.network;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.eclipse.jetty.server.Server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import com.theisleoffavalon.mcmanager.network.handler.HtmlWebRequestHandler;
import com.theisleoffavalon.mcmanager.network.handler.IWebRequestHandler;
import com.theisleoffavalon.mcmanager.network.handler.impl.RootWebHandler;
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
	private static final long STOP_WAIT_TIME = 2;
	
	/**
	 * The HTTP web server.
	 */
	//private HttpServer webServer;
	private Server webServer;
	
	/**
	 * The handler for the root context.
	 */
	private HtmlWebRequestHandler rootHandler;
	
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
		webServer.setStopTimeout(STOP_WAIT_TIME);
		
		rootHandler = new RootWebHandler();
		addHandler("/", rootHandler);
	}
	
	/**
	 * Starts the web server to handle requests.
	 */
	public void start()
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
	public void stop()
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
	public void addHandler(String context, IWebRequestHandler handler)
	{
		// TODO: implement handler adding
	}
}
