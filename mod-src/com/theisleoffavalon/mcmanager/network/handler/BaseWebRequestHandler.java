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
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import com.theisleoffavalon.mcmanager.util.LogHelper;

/**
 * A base implementation of {@link IWebRequestHandler}. This is the class that should
 * be extended over implementing the interface directly.
 * 
 * @author Cadyyan
 *
 */
public abstract class BaseWebRequestHandler<InputFormat, OutputFormat> extends AbstractHandler implements IWebRequestHandler<InputFormat, OutputFormat>
{
	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			String methodName = request.getMethod().toLowerCase();
			InputFormat formattedResponse = createInputFormatter(request.getInputStream());
			OutputFormat output = createOutputFormatter();
			
			try
			{
				Class<? extends IWebRequestHandler> type = getClass();
				Method method = type.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class, formattedResponse.getClass(), output.getClass());
				
				int code = (int)method.invoke(this, request, response, formattedResponse, output);
				
				String outputString = formatOutput(output);
				
				response.setStatus(code);
				response.getOutputStream().print(outputString);
			}
			catch(NotImplementedException e)
			{
				LogHelper.warning("Received a request of type " + methodName + " but the handler was has not implemented this method.");
				response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			}
			catch(NoSuchMethodException e)
			{
				LogHelper.warning("Received unknown method request on web server: " + methodName);
				response.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
			}
			catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
			{
				LogHelper.error("There was a problem calling the requested method on the web handler!");
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		}
		catch(IOException e)
		{
			LogHelper.error("There was a severe error when trying to communicate with a web client.\n" +
							e.getMessage());
		}
		finally
		{
			try
			{
				response.getOutputStream().close();
			}
			catch(IOException e)
			{
				LogHelper.error("There was a severe error when trying to communicate with a web client.\n" +
								e.getMessage());
			}
		}
	}
	
	@Override
	public int head(HttpServletRequest request, HttpServletResponse response, InputFormat formattedRequest, OutputFormat formattedResponse)
	{
		throw new NotImplementedException();
	}
	
	@Override
	public int put(HttpServletRequest request, HttpServletResponse response, InputFormat formattedRequest, OutputFormat formattedResponse)
	{
		throw new NotImplementedException();
	}
	
	@Override
	public int delete(HttpServletRequest request, HttpServletResponse response, InputFormat formattedRequest, OutputFormat formattedResponse)
	{
		throw new NotImplementedException();
	}
	
	@Override
	public int trace(HttpServletRequest request, HttpServletResponse response, InputFormat formattedRequest, OutputFormat formattedResponse)
	{
		throw new NotImplementedException();
	}
	
	@Override
	public int connect(HttpServletRequest request, HttpServletResponse response, InputFormat formattedRequest, OutputFormat formattedResponse)
	{
		throw new NotImplementedException();
	}
	
	/**
	 * Gets the formatted response body for the request.
	 * 
	 * @param responseBody - the response body as a stream
	 * @return the formatted request data
	 * @throws IOException when reading the data fails
	 */
	protected abstract InputFormat createInputFormatter(InputStream responseBody) throws IOException;
	
	/**
	 * Creates a new formatter that can be used to hold data
	 * until its ready to be sent.
	 * 
	 * @return the formatter
	 */
	protected abstract OutputFormat createOutputFormatter();
	
	/**
	 * Generates a string from the formatter.
	 * 
	 * @param formatter - the formatter object
	 * @return the string that was generated
	 */
	protected abstract String formatOutput(OutputFormat formatter);
}
