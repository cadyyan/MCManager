package com.theisleoffavalon.mcmanager.network.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.AsyncContinuation;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;

/**
 * A collection of context handlers. This steals
 * from the Django way of handling context paths. The Django
 * way is to use regular expression matching to handle routing
 * the different requests to different handlers.
 * 
 * @author Cadyyan
 *
 */
public class RegExContextHandlerCollection extends HandlerCollection
{
	/**
	 * Creates an empty collection of regular expression sensitive handlers.
	 */
	public RegExContextHandlerCollection()
	{
		super(true);
	}

	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		Handler handlers[] = getHandlers();
		if(handlers == null || handlers.length == 0)
			return;
		
		AsyncContinuation async = baseRequest.getAsyncContinuation();
		if(async.isAsync())
		{
			ContextHandler context = async.getContextHandler();
			if(context != null)
			{
				context.handle(target, baseRequest, request, response);
				return;
			}
		}
		
		if(target != null && target.startsWith("/"))
		{
			for(Handler handler : handlers)
			{
				if(!(handler instanceof RegExContextHandler))
					continue;
				
				RegExContextHandler context = (RegExContextHandler)handler;
				if(!context.matchesContext(target))
					continue;
				
				context.handle(target, baseRequest, request, response);
				if(baseRequest.isHandled())
					return;
			}
		}
	}
}
