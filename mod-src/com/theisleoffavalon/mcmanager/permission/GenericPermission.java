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

package com.theisleoffavalon.mcmanager.permission;

/**
 * A generic and very simple permission implementation. This should not be used
 * for actual permissions. This is used for a place holder between actual permission nodes.
 * 
 * @author Cadyyan
 *
 */
public class GenericPermission extends BasePermission
{
	/**
	 * Creates a new generic permission.
	 * 
	 * @param action - the action string
	 * @param isAllowed - whether the permission is allowed or not
	 */
	public GenericPermission(String action, boolean isAllowed)
	{
		super(action, isAllowed);
	}
}
