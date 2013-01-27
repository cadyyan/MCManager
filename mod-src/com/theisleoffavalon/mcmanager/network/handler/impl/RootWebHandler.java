package com.theisleoffavalon.mcmanager.network.handler.impl;

import java.io.IOException;
import java.io.PrintStream;

import com.sun.net.httpserver.HttpExchange;
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
	/*
	 * (non-Javadoc)
	 * @see com.theisleoffavalon.mcmanager.network.handler.IWebRequestHandler#get(com.sun.net.httpserver.HttpExchange, java.io.PrintStream)
	 */
	@Override
	public StatusCode get(HttpExchange exchange, PrintStream out)
	{
		out.println("<html><head><title>It Works!</title></head><body><h1>It Works!</h1></body></html>");
		
		return StatusCode.OK;
	}

	/*
	 * (non-Javadoc)
	 * @see com.theisleoffavalon.mcmanager.network.handler.IWebRequestHandler#post(com.sun.net.httpserver.HttpExchange)
	 */
	@Override
	public StatusCode post(HttpExchange exchange, PrintStream out)
	{
		return null;
	}
}
