package com.theisleoffavalon.mcmanager.network.handler;

import java.io.StringWriter;

/**
 * An implementation of the {@link IWebRequestHandler} interface that
 * handles requests for HTML.
 * 
 * @author Cadyyan
 *
 */
public abstract class HtmlWebRequestHandler extends BaseWebRequestHandler<StringWriter>
{
	@Override
	public StringWriter wrapWriter(StringWriter writer)
	{
		return writer;
	}
}
