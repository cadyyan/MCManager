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
