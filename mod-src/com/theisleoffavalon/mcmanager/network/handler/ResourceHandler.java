package com.theisleoffavalon.mcmanager.network.handler;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

/**
 * Handles sending out resource files that might be required for the web site.
 * Such resources might be pictures, CSS, Javascript, etc.
 * 
 * @author Cadyyan
 *
 */
public class ResourceHandler extends AbstractHandler
{
	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		// Get the targeted resource.
		String resName = target.substring("/resources/".length());
		
		// Try to get the requested resource file stream.
		InputStream resStream = getClass().getClassLoader().getResourceAsStream("web/resources/" + resName);
		if(resStream == null)
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		else
		{
			byte bytes[] = new byte[resStream.available()];
			resStream.read(bytes);
			resStream.close();
			response.getOutputStream().write(bytes);
			response.setStatus(HttpServletResponse.SC_OK);
		}
		
		baseRequest.setHandled(true);
		response.getOutputStream().close();
	}
}
