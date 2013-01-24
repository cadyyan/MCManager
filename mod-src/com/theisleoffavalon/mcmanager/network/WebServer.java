package com.theisleoffavalon.mcmanager.network;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

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
	private HttpServer webServer;
	
	/**
	 * Creates an instance of the web server but does not actually start
	 * it. To start it you must call the {@link start} method.
	 * @throws IOException thrown when the web socket could not be created and bound
	 */
	public WebServer() throws IOException
	{
		// TODO: set this up to use HTTPS instead when requested
		// TODO: change the port value to be a configuration setting
		// TODO: change the backlog value to be a configuration setting
		webServer = HttpServer.create(new InetSocketAddress(1716), 0);
		
		// TODO: set the executor to be of fixed size which is determined by a configuration setting
		webServer.setExecutor(null);
	}
	
	/**
	 * Starts the web server to handle requests.
	 */
	public void start()
	{
		webServer.start();
	}
	
	/**
	 * Stops the web server from handling requests.
	 */
	public void stop()
	{
		webServer.stop(STOP_WAIT_TIME);
	}
}
