package com.theisleoffavalon.mcmanager.network.handler;

import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Handler;

/**
 * A specialized implementation of the {@link HttpHandler}
 * interface. This interface allows us to format the incoming
 * request as needed to send and receive into the expected format.
 * Handlers should not write directly to the output stream. They
 * should instead write to the stream that is provided to each
 * handler method.
 * 
 * @author Cadyyan
 *
 */
public interface IWebRequestHandler extends Handler
{
	/**
	 * Handles HTTP-GET requests.
	 * 
	 * @param request - the request object
	 * @param response - the response object
	 * @param writer - the stream to write out to
	 * @return the status code result
	 */
	public int get(HttpServletRequest request, HttpServletResponse response, StringWriter writer);
	
	/**
	 * Handles HTTP-HEAD requests. This is optional
	 * to implement.
	 * 
	 * @param request - the request object
	 * @param response - the response object
	 * @param writer - the stream to write out to
	 * @return the status code result
	 */
	public int head(HttpServletRequest request, HttpServletResponse response, StringWriter writer);
	
	/**
	 * Handles HTTP-POST requests.
	 * 
	 * @param request - the request object
	 * @param response - the response object
	 * @param writer - the stream to write out to
	 * @return the status code result
	 */
	public int post(HttpServletRequest request, HttpServletResponse response, StringWriter writer);
	
	/**
	 * Handles HTTP-PUT requests. This is optional
	 * to implement.
	 * 
	 * @param request - the request object
	 * @param response - the response object
	 * @param writer - the stream to write out to
	 * @return the status code result
	 */
	public int put(HttpServletRequest request, HttpServletResponse response, StringWriter writer);
	
	/**
	 * Handles HTTP-DELETE requests. This is optional
	 * to implement.
	 * 
	 * @param request - the request object
	 * @param response - the response object
	 * @param writer - the stream to write out to
	 * @return the status code result
	 */
	public int delete(HttpServletRequest request, HttpServletResponse response, StringWriter writer);
	
	/**
	 * Handles the HTTP-TRACE requests. This is optional
	 * to implement.
	 * 
	 * @param request - the request object
	 * @param response - the response object
	 * @param writer - the stream to write out to
	 * @return the status code result
	 */
	public int trace(HttpServletRequest request, HttpServletResponse response, StringWriter writer);
	
	/**
	 * Handles the HTTP-CONNECT requests. This is optional
	 * to implement.
	 * 
	 * @param request - the request object
	 * @param response - the response object
	 * @param writer - the stream to write out to
	 * @return the status code result
	 */
	public int connect(HttpServletRequest request, HttpServletResponse response, StringWriter writer);
}
