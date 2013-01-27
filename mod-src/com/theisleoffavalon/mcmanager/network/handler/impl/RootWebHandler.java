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
	 * @see com.theisleoffavalon.mcmanager.network.handler.IWebRequestHandler#get(com.sun.net.httpserver.HttpExchange)
	 */
	@Override
	public void get(HttpExchange exchange)
	{
		try
		{
			exchange.sendResponseHeaders(200, 0);
		}
		catch(IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PrintStream out = new PrintStream(exchange.getResponseBody());
		out.println("<html><head><title>It Works!</title></head><body><h1>It Works!</h1></body></html>");
	}

	/*
	 * (non-Javadoc)
	 * @see com.theisleoffavalon.mcmanager.network.handler.IWebRequestHandler#post(com.sun.net.httpserver.HttpExchange)
	 */
	@Override
	public void post(HttpExchange exchange)
	{
		// TODO Auto-generated method stub

	}
}
