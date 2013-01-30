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
 * @param <InputFormat> this is the type of formatted input
 * @param <OutputFormat> this is the type of stream or container that holds
 * the data before its written out
 * 
 * @author Cadyyan
 *
 */
public interface IWebRequestHandler<InputFormat, OutputFormat> extends Handler
{
	/**
	 * Handles HTTP-GET requests.
	 * 
	 * @param request - the request object
	 * @param response - the response object
	 * @param formattedRequest - the formatted request
	 * @param formattedResponse - the stream to write out to
	 * @return the status code result
	 */
	public int get(HttpServletRequest request, HttpServletResponse response, InputFormat formattedRequest, OutputFormat formattedResponse);
	
	/**
	 * Handles HTTP-HEAD requests. This is optional
	 * to implement.
	 * 
	 * @param request - the request object
	 * @param response - the response object
	 * @param formattedRequest - the formatted request
	 * @param formattedResponse - the stream to write out to
	 * @return the status code result
	 */
	public int head(HttpServletRequest request, HttpServletResponse response, InputFormat formattedRequest, OutputFormat formattedResponse);
	
	/**
	 * Handles HTTP-POST requests.
	 * 
	 * @param request - the request object
	 * @param response - the response object
	 * @param formattedRequest - the formatted request
	 * @param formattedResponse - the stream to write out to
	 * @return the status code result
	 */
	public int post(HttpServletRequest request, HttpServletResponse response, InputFormat formattedRequest, OutputFormat formattedResponse);
	
	/**
	 * Handles HTTP-PUT requests. This is optional
	 * to implement.
	 * 
	 * @param request - the request object
	 * @param response - the response object
	 * @param formattedRequest - the formatted request
	 * @param formattedResponse - the stream to write out to
	 * @return the status code result
	 */
	public int put(HttpServletRequest request, HttpServletResponse response, InputFormat formattedRequest, OutputFormat formattedResponse);
	
	/**
	 * Handles HTTP-DELETE requests. This is optional
	 * to implement.
	 * 
	 * @param request - the request object
	 * @param response - the response object
	 * @param formattedRequest - the formatted request
	 * @param formattedResponse - the stream to write out to
	 * @return the status code result
	 */
	public int delete(HttpServletRequest request, HttpServletResponse response, InputFormat formattedRequest, OutputFormat formattedResponse);
	
	/**
	 * Handles the HTTP-TRACE requests. This is optional
	 * to implement.
	 * 
	 * @param request - the request object
	 * @param response - the response object
	 * @param formattedRequest - the formatted request
	 * @param formattedResponse - the stream to write out to
	 * @return the status code result
	 */
	public int trace(HttpServletRequest request, HttpServletResponse response, InputFormat formattedRequest, OutputFormat formattedResponse);
	
	/**
	 * Handles the HTTP-CONNECT requests. This is optional
	 * to implement.
	 * 
	 * @param request - the request object
	 * @param response - the response object
	 * @param formattedRequest - the formatted request
	 * @param formattedResponse - the stream to write out to
	 * @return the status code result
	 */
	public int connect(HttpServletRequest request, HttpServletResponse response, InputFormat formattedRequest, OutputFormat formattedResponse);
}
