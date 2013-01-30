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

import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.theisleoffavalon.mcmanager.network.handler.HtmlWebRequestHandler;

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
		writer.append("<html><head><title>It Works!</title></head><body><h1>It Works!</h1></body></html>");
		
		return HttpServletResponse.SC_OK;
	}

	@Override
	public int post(HttpServletRequest request, HttpServletResponse response, String formattedResponse, StringWriter writer)
	{
		return -1;
	}
}
