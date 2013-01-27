package com.theisleoffavalon.mcmanager.proxy;

import java.io.IOException;

import com.theisleoffavalon.mcmanager.network.WebServer;

/**
 * A proxy class that hides the details of the client and server implementations of common
 * features. It is recommended that you make this class abstract over a normal interface
 * so that the methods don't have to be implemented on proxies that don't actually
 * implement that functionality. For instance the server doesn't need to do anything
 * on a render call.
 * 
 * @author Cadyyan
 *
 */
public abstract class MCManagerProxy
{
	/**
	 * Creates an instance of a web server. This should be done server side only.
	 * 
	 * @return a web server instance or null if this is the client
	 * @throws IOException thrown when a network error prevents instantiation
	 */
	public WebServer createWebServer() throws IOException
	{
		return null;
	}
}
