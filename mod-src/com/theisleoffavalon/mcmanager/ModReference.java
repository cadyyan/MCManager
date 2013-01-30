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

package com.theisleoffavalon.mcmanager;

/**
 * Contains references to mod specific information.
 * 
 * @author Cadyyan
 *
 */
public class ModReference
{
	/**
	 * The mod ID.
	 */
	public static final String ID = "MCManager";
	
	/**
	 * The mod name.
	 */
	public static final String NAME = "MCManager";
	
	/**
	 * The mod version.
	 */
	public static final String VERSION = "1.0.0";
	
	/**
	 * The client side proxy.
	 */
	public static final String CLIENT_PROXY = "com.theisleoffavalon.mcmanager.proxy.MCManagerClientProxy";
	
	/**
	 * The server side proxy.
	 */
	public static final String SERVER_PROXY = "com.theisleoffavalon.mcmanager.proxy.MCManagerServerProxy";
	
	/**
	 * Hidden constructor.
	 */
	private ModReference()
	{
	}
}
