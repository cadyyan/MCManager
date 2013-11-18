package com.theisleoffavalon.mcmanager.network.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

/**
 * Meant to be wrapper around an existing handler. This steals
 * from the Django way of handling context paths. The Django
 * way is to use regular expression matching to handle routing
 * the different requests to different handlers.
 * 
 * @author Cadyyan
 *
 */
public class RegExContextHandler extends AbstractHandler
{
	/**
	 * The regular expression pattern to test for.
	 */
	private final String pattern;
	
	/**
	 * The base handler this is decorating.
	 */
	private final Handler baseHandler;
	
	/**
	 * Creates a new regular expression based context handler that
	 * decorates the given handler.
	 * 
	 * @param pattern - the context path pattern
	 * @param handler - the handler to decorate
	 */
	public RegExContextHandler(String pattern, Handler handler)
	{
		this.pattern = pattern;
		this.baseHandler = handler;
	}
	
	/**
	 * Checks if this context handler wants to handle the given context path.
	 * 
	 * @param context - the context path
	 * @return true if the handler will handle the context, false otherwise
	 */
	public boolean matchesContext(String context)
	{
		return context.matches(pattern);
	}
	
	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		baseHandler.handle(target, baseRequest, request, response);
	}
}
