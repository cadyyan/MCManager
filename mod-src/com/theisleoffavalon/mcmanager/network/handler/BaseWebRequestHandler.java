package com.theisleoffavalon.mcmanager.network.handler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.CharsetDecoder;

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
			String methodName = exchange.getRequestMethod().toLowerCase();
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			PrintStream wrappedStream = new PrintStream(buffer);
			
			try
			{
				Class<? extends IWebRequestHandler> type = getClass();
				Method method = type.getMethod(methodName, HttpExchange.class, PrintStream.class);
				
				StatusCode code = (StatusCode)method.invoke(this, exchange, wrappedStream);
				
				byte byteBuffer[] = buffer.toByteArray();
				int length = byteBuffer.length;
				
				exchange.sendResponseHeaders(code.getHttpCode(), length);
				exchange.getResponseBody().write(byteBuffer);
			}
			catch(NotImplementedException e)
			{
				LogHelper.warning("Received a request of type " + methodName + " but the handler was has not implemented this method.");
				exchange.sendResponseHeaders(StatusCode.METHOD_NOT_ALLOWED.getHttpCode(), 0);
			}
			catch(NoSuchMethodException e)
			{
				LogHelper.warning("Received unknown method request on web server: " + methodName);
				exchange.sendResponseHeaders(StatusCode.NOT_IMPLMENTED.getHttpCode(), 0);
			}
			catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
			{
				LogHelper.error("There was a problem calling the requested method on the web handler!");
				exchange.sendResponseHeaders(StatusCode.INTERNAL_SERVER_ERROR.getHttpCode(), 0);
			}
		}
		catch(IOException e)
		{
			LogHelper.error("There was a severe error when trying to communicate with a web client.\n" +
							e.getMessage());
		}
		finally
		{
			exchange.close();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.theisleoffavalon.mcmanager.network.handler.IWebRequestHandler#head(com.sun.net.httpserver.HttpExchange, java.io.PrintStream)
	 */
	@Override
	public StatusCode head(HttpExchange exchange, PrintStream out)
	{
		throw new NotImplementedException();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.theisleoffavalon.mcmanager.network.handler.IWebRequestHandler#put(com.sun.net.httpserver.HttpExchange, java.io.PrintStream)
	 */
	@Override
	public StatusCode put(HttpExchange exchange, PrintStream out)
	{
		throw new NotImplementedException();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.theisleoffavalon.mcmanager.network.handler.IWebRequestHandler#delete(com.sun.net.httpserver.HttpExchange, java.io.PrintStream)
	 */
	@Override
	public StatusCode delete(HttpExchange exchange, PrintStream out)
	{
		throw new NotImplementedException();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.theisleoffavalon.mcmanager.network.handler.IWebRequestHandler#trace(com.sun.net.httpserver.HttpExchange, java.io.PrintStream)
	 */
	@Override
	public StatusCode trace(HttpExchange exchange, PrintStream out)
	{
		throw new NotImplementedException();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.theisleoffavalon.mcmanager.network.handler.IWebRequestHandler#connect(com.sun.net.httpserver.HttpExchange, java.io.PrintStream)
	 */
	@Override
	public StatusCode connect(HttpExchange exchange, PrintStream out)
	{
		throw new NotImplementedException();
	}
}
