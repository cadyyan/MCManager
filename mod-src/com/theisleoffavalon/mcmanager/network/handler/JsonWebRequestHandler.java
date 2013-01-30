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
import java.io.InputStreamReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public abstract class JsonWebRequestHandler extends BaseWebRequestHandler<JSONObject, JSONObject>
{
	/**
	 * The parser to use for JSON.
	 */
	private JSONParser parser;
	
	/**
	 * Creates a new JSON based web request handler.
	 */
	public JsonWebRequestHandler()
	{
		super();
		
		parser = new JSONParser();
	}
	
	@Override
	protected JSONObject createInputFormatter(InputStream responseBody) throws IOException
	{
		if(responseBody.available() == 0)
			return new JSONObject();
		
		try
		{
			return (JSONObject)parser.parse(new InputStreamReader(responseBody));
		}
		catch(ParseException e)
		{
			throw new IOException("Invalid JSON sent to server and server failed to parse.");
		}
	}
	
	@Override
	protected JSONObject createOutputFormatter()
	{
		return new JSONObject();
	}

	@Override
	protected String formatOutput(JSONObject formatter)
	{
		return formatter.toJSONString();
	}
}
