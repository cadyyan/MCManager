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
