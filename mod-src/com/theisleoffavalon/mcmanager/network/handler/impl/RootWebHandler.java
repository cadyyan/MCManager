package com.theisleoffavalon.mcmanager.network.handler.impl;

import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.theisleoffavalon.mcmanager.network.handler.HtmlWebRequestHandler;

/**
 * Handles requests that go to the root context. This should
 * send back HTML that serves as a web interface. That is a
 * web based interface to the mod.
 * 
 * @author Cadyyan
 *
 */
public class RootWebHandler extends HtmlWebRequestHandler
{
	/*
	 * (non-Javadoc)
	 * @see com.theisleoffavalon.mcmanager.network.handler.IWebRequestHandler#get(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.io.StringWriter)
	 */
	@Override
	public int get(HttpServletRequest request, HttpServletResponse response, StringWriter writer)
	{
		writer.append("<html><head><title>It Works!</title></head><body><h1>It Works!</h1></body></html>");
		
		return HttpServletResponse.SC_OK;
	}

	/*
	 * (non-Javadoc)
	 * @see com.theisleoffavalon.mcmanager.network.handler.IWebRequestHandler#post(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.io.StringWriter)
	 */
	@Override
	public int post(HttpServletRequest request, HttpServletResponse response, StringWriter writer)
	{
		return -1;
	}
}
