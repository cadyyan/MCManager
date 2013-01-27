package com.theisleoffavalon.mcmanager.proxy;

import java.io.IOException;

import com.theisleoffavalon.mcmanager.network.WebServer;

/**
 * The server side proxy implementation.
 * 
 * @author Cadyyan
 *
 */
public class MCManagerServerProxy extends MCManagerProxy
{
	/*
	 * (non-Javadoc)
	 * @see com.theisleoffavalon.mcmanager.proxy.MCManagerProxy#createWebServer()
	 */
	@Override
	public WebServer createWebServer() throws IOException
	{
		return new WebServer();
	}
}
