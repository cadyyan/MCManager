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
