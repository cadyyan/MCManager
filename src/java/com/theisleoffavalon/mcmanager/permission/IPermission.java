package com.theisleoffavalon.mcmanager.permission;

import java.util.Set;

/**
 * A permission represents an action and the corresponding acceptance of that action. This
 * action could be a command, a crafting attempt, a block placement attempt, etc. The acceptance
 * means that the action is allowed. For instance, a player may or may not have permission to op
 * another player or themselves. This permission system also supports wildcards. A wildcard affects
 * all child permission nodes by allowing them to inherit the permission of the parent wildcard.
 * A child can still be overridden to have a different permission value than a wildcard parent.
 * 
 * @author Cadyyan
 *
 */
public interface IPermission
{
	/**
	 * The character that delimits the different parts of a permission string.
	 */
	public static final String PERMISSION_NODE_DELIMITER = ".";
	
	/**
	 * The character that denotes a wildcard.
	 */
	public static final String PERMISSION_WILDCARD = "*";
	
	/**
	 * Gets the action string for the permission.
	 * 
	 * @return the action string
	 */
	public String getAction();
	
	/**
	 * Checks if the given action is allowed.
	 * 
	 * @return true if allowed, false if not
	 */
	public boolean isAllowed();
	
	/**
	 * Sets whether the permission is allowed or not.
	 * 
	 * @param isAllowed - true if the permission is allowed, false otherwise
	 */
	public void setAllowed(boolean isAllowed);
	
	/**
	 * Is a top level permission node. That is it has no parents.
	 * 
	 * @return true if its the top level node for its tree
	 */
	public boolean isTopLevel();
	
	/**
	 * Adds a new permission as a child to this one. If the given permission already exists
	 * then the old value is overwritten. This does not affect any child permissions.
	 * 
	 * @param permission - the permission
	 */
	public void addPermission(IPermission permission);
	
	/**
	 * Gets the permission for a given action string. If the requested
	 * permission does not exist a default is given from the nearest wildcard/parent.
	 * 
	 * @param action - the action string
	 * @return the permission
	 */
	public IPermission getPermission(String action);
	
	/**
	 * Returns the set of all child permission nodes as well as the current
	 * permission node.
	 * 
	 * @return the set of all permissions contained by this and all child nodes
	 */
	public Set<IPermission> getAllPermissions();
	
	/**
	 * Removes a permission from the permission mapping.
	 * 
	 * @param action - the action string
	 */
	public void removePermission(String action);
	
	/**
	 * Removes all permissions from this node and resets this node to be
	 * not allowed.
	 */
	public void removeAllPermissions();
}
