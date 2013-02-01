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

package com.theisleoffavalon.mcmanager.network.handler.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.theisleoffavalon.mcmanager.network.handler.HtmlWebRequestHandler;
import com.theisleoffavalon.mcmanager.util.LogHelper;

/**
 * Handles requests that go to the root context. This should
 * send back HTML that serves as a web interface. That is a
 * web based interface to the mod.
 * 
 * @author Cadyyan
 *
 */
public class RootWebHandler extends HtmlWebRequestHandler
{
	@Override
	public int get(HttpServletRequest request, HttpServletResponse response, String formattedResponse, StringWriter writer)
	{
		try
		{
			// Not the most efficient way of serving up a file but it'll work.
			InputStream html = getClass().getClassLoader().getResourceAsStream("web/control_panel.html");
			
			byte buf[] = new byte[html.available()];
			html.read(buf);
			writer.write(new String(buf));
			
			return HttpServletResponse.SC_OK;
		}
		catch(IOException e)
		{
			LogHelper.error(e.getMessage());
			return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
		}
	}

	@Override
	public int post(HttpServletRequest request, HttpServletResponse response, String formattedResponse, StringWriter writer)
	{
		return -1;
	}
}
