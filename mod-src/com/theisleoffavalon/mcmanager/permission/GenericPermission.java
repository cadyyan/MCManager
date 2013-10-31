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
