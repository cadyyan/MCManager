package com.theisleoffavalon.mcmanager.network.handler;

import java.io.IOException;
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
public abstract class BaseWebRequestHandler<WrapperType> extends AbstractHandler implements IWebRequestHandler<WrapperType>
{
	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			String methodName = request.getMethod().toLowerCase();
			StringWriter writer = new StringWriter();
			WrapperType wrapper = wrapWriter(writer);
			
			try
			{
				Class<? extends IWebRequestHandler> type = getClass();
				Method method = type.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class, wrapper.getClass());
				
				int code = (int)method.invoke(this, request, response, writer);
				
				writer.flush();
				String outputString = writer.toString();
				
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public int head(HttpServletRequest request, HttpServletResponse response, WrapperType writer)
	{
		throw new NotImplementedException();
	}
	
	@Override
	public int put(HttpServletRequest request, HttpServletResponse response, WrapperType writer)
	{
		throw new NotImplementedException();
	}
	
	@Override
	public int delete(HttpServletRequest request, HttpServletResponse response, WrapperType writer)
	{
		throw new NotImplementedException();
	}
	
	@Override
	public int trace(HttpServletRequest request, HttpServletResponse response, WrapperType writer)
	{
		throw new NotImplementedException();
	}
	
	@Override
	public int connect(HttpServletRequest request, HttpServletResponse response, WrapperType writer)
	{
		throw new NotImplementedException();
	}
	
	/**
	 * Wraps the given {@link StringWriter} to for specialized use. This is
	 * called automatically on request and is passed to the final implementation
	 * automatically.
	 * 
	 * @param writer - the writer to wrap
	 * @return the wrapped string writer
	 */
	protected abstract WrapperType wrapWriter(StringWriter writer);
}
