package com.theisleoffavalon.mcmanager.network.handler;

import java.io.IOException;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import com.sun.net.httpserver.HttpExchange;
import com.theisleoffavalon.mcmanager.util.LogHelper;

/**
 * A base implementation of {@link IWebRequestHandler}. This is the class that should
 * be extended over implementing the interface directly.
 * 
 * @author Cadyyan
 *
 */
public abstract class BaseWebRequestHandler implements IWebRequestHandler
{
	/**
	 * Handles the incoming HTTP request. This method then pulls out the basic required information
	 * and then calls the formatting on the request and finally processing the request.
	 * 
	 * @param exchange - 
	 */
	@Override
	public void handle(HttpExchange exchange)
	{
		try
		{
			String method = exchange.getRequestMethod();
			
			try
			{
				if(method.equals("GET"))
					get(exchange);
				else if(method.equals("HEAD"))
					head(exchange);
				else if(method.equals("POST"))
					post(exchange);
				else if(method.equals("PUT"))
					put(exchange);
				else if(method.equals("DELETE"))
					delete(exchange);
				else if(method.equals("TRACE"))
					trace(exchange);
				else if(method.equals("CONNECT"))
					connect(exchange);
				else
				{
					LogHelper.warning("Received unknown method request on web server: " + method);
					exchange.sendResponseHeaders(IWebRequestHandler.StatusCode.NOT_IMPLMENTED.getHttpCode(), 0);
				}
			}
			catch(NotImplementedException e)
			{
				exchange.sendResponseHeaders(IWebRequestHandler.StatusCode.METHOD_NOT_ALLOWED.getHttpCode(), 0);
			}
		}
		catch(IOException e)
		{
			LogHelper.error("There was a severe error when trying to communicate with a web client.");
		}
		finally
		{
			exchange.close();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.theisleoffavalon.mcmanager.network.handler.IWebRequestHandler#head(com.sun.net.httpserver.HttpExchange)
	 */
	@Override
	public void head(HttpExchange exchange)
	{
		throw new NotImplementedException();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.theisleoffavalon.mcmanager.network.handler.IWebRequestHandler#put(com.sun.net.httpserver.HttpExchange)
	 */
	@Override
	public void put(HttpExchange exchange)
	{
		throw new NotImplementedException();
	}
	
	/**
	 * Handles HTTP-DELETE requests. This is optional
	 * to implement.
	 * 
	 * @param exchange - the exchange between client and server
	 */
	public void delete(HttpExchange exchange)
	{
		throw new NotImplementedException();
	}
	
	/**
	 * Handles the HTTP-TRACE requests. This is optional
	 * to implement.
	 * 
	 * @param exchange - the exchange between client and server
	 */
	public void trace(HttpExchange exchange)
	{
		throw new NotImplementedException();
	}
	
	/**
	 * Handles the HTTP-CONNECT requests. This is optional
	 * to implement.
	 * 
	 * @param exchange - the exchange between client and server
	 */
	public void connect(HttpExchange exchange)
	{
		throw new NotImplementedException();
	}
}
