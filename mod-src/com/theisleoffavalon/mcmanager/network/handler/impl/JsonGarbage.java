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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.theisleoffavalon.mcmanager.network.handler.JsonWebRequestHandler;

public class JsonGarbage extends JsonWebRequestHandler
{
	@Override
	public int get(HttpServletRequest request, HttpServletResponse response, JSONObject formattedRequest, JSONObject formattedResponse)
	{
		formattedResponse.put("test", "value");
		return HttpServletResponse.SC_OK;
	}

	@Override
	public int post(HttpServletRequest request, HttpServletResponse response, JSONObject formattedRequest, JSONObject formattedResponse)
	{
		// TODO Auto-generated method stub
		return HttpServletResponse.SC_OK;
	}
}
