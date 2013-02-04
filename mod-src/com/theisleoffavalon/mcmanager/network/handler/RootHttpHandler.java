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

package com.theisleoffavalon.mcmanager.network.handler;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import com.theisleoffavalon.mcmanager.util.LogHelper;

/**
 * Handles requests that go to the root context. This should
 * send back HTML that serves as a web interface. That is a
 * web based interface to the mod.
 * 
 * @author Cadyyan
 *
 */
public class RootHttpHandler extends AbstractHandler
{
	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		try
		{
			// Not the most efficient way of serving up a file but it'll work.
			InputStream html = getClass().getClassLoader().getResourceAsStream("web/control_panel.html");
			
			byte buf[] = new byte[html.available()];
			html.read(buf);
			
			response.setStatus(HttpServletResponse.SC_OK);
			response.getOutputStream().write(buf);
		}
		catch(IOException e)
		{
			LogHelper.error(e.getMessage());
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		finally
		{
			response.getOutputStream().close();
		}
	}
}
