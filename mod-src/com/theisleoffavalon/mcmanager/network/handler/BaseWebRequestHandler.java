package com.theisleoffavalon.mcmanager.network.handler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
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
public abstract class BaseWebRequestHandler extends AbstractHandler implements IWebRequestHandler
{
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jetty.server.Handler#handle(java.lang.String, org.eclipse.jetty.server.Request, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			String methodName = request.getMethod().toLowerCase();
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			PrintStream wrappedStream = new PrintStream(buffer);
			
			try
			{
				Class<? extends IWebRequestHandler> type = getClass();
				Method method = type.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class, PrintStream.class);
				
				int code = (int)method.invoke(this, request, response, wrappedStream);
				
				byte byteBuffer[] = buffer.toByteArray();
				int length = byteBuffer.length;
				
				response.setStatus(code);
				response.getOutputStream().write(byteBuffer);
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.theisleoffavalon.mcmanager.network.handler.IWebRequestHandler#head(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.io.PrintStream)
	 */
	@Override
	public int head(HttpServletRequest request, HttpServletResponse response, PrintStream out)
	{
		throw new NotImplementedException();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.theisleoffavalon.mcmanager.network.handler.IWebRequestHandler#put(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.io.PrintStream)
	 */
	@Override
	public int put(HttpServletRequest request, HttpServletResponse response, PrintStream out)
	{
		throw new NotImplementedException();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.theisleoffavalon.mcmanager.network.handler.IWebRequestHandler#delete(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.io.PrintStream)
	 */
	@Override
	public int delete(HttpServletRequest request, HttpServletResponse response, PrintStream out)
	{
		throw new NotImplementedException();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.theisleoffavalon.mcmanager.network.handler.IWebRequestHandler#trace(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.io.PrintStream)
	 */
	@Override
	public int trace(HttpServletRequest request, HttpServletResponse response, PrintStream out)
	{
		throw new NotImplementedException();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.theisleoffavalon.mcmanager.network.handler.IWebRequestHandler#connect(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.io.PrintStream)
	 */
	@Override
	public int connect(HttpServletRequest request, HttpServletResponse response, PrintStream out)
	{
		throw new NotImplementedException();
	}
}
