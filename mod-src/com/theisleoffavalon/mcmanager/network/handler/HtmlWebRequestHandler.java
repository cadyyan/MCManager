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

import java.io.InputStream;
import java.io.StringWriter;

/**
 * An implementation of the {@link IWebRequestHandler} interface that
 * handles requests for HTML.
 * 
 * @author Cadyyan
 *
 */
public abstract class HtmlWebRequestHandler extends BaseWebRequestHandler<String, StringWriter>
{
	@Override
	protected String createInputFormatter(InputStream responseBody)
	{
		return ""; // TODO: actually use the input
	}
	
	@Override
	protected StringWriter createOutputFormatter()
	{
		return new StringWriter();
	}
	
	@Override
	protected String formatOutput(StringWriter formatter)
	{
		formatter.flush();
		return formatter.toString();
	}
}
