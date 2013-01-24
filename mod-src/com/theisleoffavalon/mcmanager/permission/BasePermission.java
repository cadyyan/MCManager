package com.theisleoffavalon.mcmanager.permission;

import java.security.InvalidParameterException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * A base implementation of the {@link IPermission} interface. This is the class
 * that should be extended for all permission implementation classes.
 * 
 * @author Cadyyan
 *
 */
public abstract class BasePermission implements IPermission
{
	/**
	 * The parent permission node.
	 */
	protected IPermission parent;
	
	/**
	 * A string representation of the action. This is a set of strings that are
	 * delimited by the PERMISSION_NODE_DELIMITER character.
	 */
	protected String action;
	
	/**
	 * The full action string of the node.
	 */
	protected String fullAction;
	
	/**
	 * Is the permission allowed.
	 */
	protected boolean isAllowed;
	
	/**
	 * The child permission nodes.
	 */
	protected Map<String, BasePermission> childPermissions;
	
	/**
	 * Creates a permission object for the given action.
	 * 
	 * @param action - the action string describing the action
	 * @param isAllowed - whether the action is allowed or not
	 */
	public BasePermission(String action, boolean isAllowed)
	{
		validateActionString(action);
		
		this.parent = null;
		this.fullAction = action;
		this.action = action;
		this.isAllowed = isAllowed;
		this.childPermissions = new LinkedHashMap<String, BasePermission>();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.theisleoffavalon.mcmanager.permission.IPermission#getAction()
	 */
	@Override
	public String getAction()
	{
		return fullAction;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.theisleoffavalon.mcmanager.permission.IPermission#isAllowed()
	 */
	@Override
	public boolean isAllowed()
	{
		return isAllowed;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.theisleoffavalon.mcmanager.permission.IPermission#setAllowed(boolean)
	 */
	@Override
	public void setAllowed(boolean isAllowed)
	{
		this.isAllowed = isAllowed;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.theisleoffavalon.mcmanager.permission.IPermission#isTopLevel()
	 */
	@Override
	public boolean isTopLevel()
	{
		return parent == null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.theisleoffavalon.mcmanager.permission.IPermission#addPermission(com.theisleoffavalon.mcmanager.permission.IPermission)
	 */
	@Override
	public void addPermission(IPermission permission)
	{
		if(permission == null)
			throw new NullPointerException("Cannot add a null permission.");
		
		if(permission.getAction().equals(action))
		{
			isAllowed = permission.isAllowed();
			return;
		}
		
		String actionStrings[] = permission.getAction().split(IPermission.PERMISSION_NODE_DELIMITER);
		int index = 0;
		String path = "";
		String actionNode;
		BasePermission perm = this;
		
		do
		{
			actionNode = actionStrings[index++];
			path = (path.isEmpty() ? "" : IPermission.PERMISSION_NODE_DELIMITER) + path;
			
			if(perm.childPermissions.containsKey(actionNode))
				perm = perm.childPermissions.get(actionNode);
			else
			{
				BasePermission generic = new GenericPermission(path, perm.isAllowed());
				perm.childPermissions.put(actionNode, generic);
				perm = generic;
			}
		}
		while(index < actionStrings.length - 1);
		
		actionNode = actionStrings[index];
		perm.childPermissions.put(actionNode, (BasePermission)permission);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.theisleoffavalon.mcmanager.permission.IPermission#getPermission(java.lang.String)
	 */
	@Override
	public IPermission getPermission(String action)
	{
		validateActionString(action);
		
		String actionStrings[] = action.split(IPermission.PERMISSION_NODE_DELIMITER);
		return getPermission(actionStrings, 0);
	}
	
	/**
	 * Gets the permission for a given action. If the requested
	 * permission does not exist a default is given from the nearest wildcard/parent.
	 * 
	 * @param actionNodes - the action
	 * @param current - the current location in the node list (default 0)
	 * @return the permission
	 */
	protected IPermission getPermission(String actionNodes[], int current)
	{
		if(current == actionNodes.length)
			return this;
		
		String action = actionNodes[current];
		if(actionNodes.length - current == 1 && actionNodes[current].equals(IPermission.PERMISSION_WILDCARD))
			return this;
		
		BasePermission next = childPermissions.get(action);
		return next.getPermission(actionNodes, current + 1);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.theisleoffavalon.mcmanager.permission.IPermission#getAllPermissions()
	 */
	@Override
	public Set<IPermission> getAllPermissions()
	{
		Set<IPermission> perms = new LinkedHashSet<IPermission>();
		
		getAllPermissions(perms);
		
		return perms;
	}
	
	/**
	 * Returns the set of all child permission nodes.
	 * 
	 * @param perms - the set to add the permissions to
	 */
	protected void getAllPermissions(Set<IPermission> perms)
	{
		perms.add(this);
		for(BasePermission perm : childPermissions.values())
			perm.getAllPermissions(perms);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.theisleoffavalon.mcmanager.permission.IPermission#removePermission(java.lang.String)
	 */
	@Override
	public void removePermission(String action)
	{
		validateActionString(action);
		
		String actionStrings[] = action.split(IPermission.PERMISSION_NODE_DELIMITER);
		int index = 0;
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.theisleoffavalon.mcmanager.permission.IPermission#removeAllPermissions()
	 */
	@Override
	public void removeAllPermissions()
	{
		throw new RuntimeException("Unimplemented");
	}
	
	/**
	 * Checks that the given action string is valid.
	 * 
	 * @param action - the action string
	 */
	protected void validateActionString(String action)
	{
		if(action == null)
			throw new NullPointerException("The action string for a permission cannot be null.");
		
		if(action.isEmpty())
			throw new InvalidParameterException("The action string cannot be empty.");
		
		// TODO: perform a regex check
	}
}
