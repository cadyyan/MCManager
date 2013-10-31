package com.theisleoffavalon.mcmanager.permission;

/**
 * An implementation of the {@link IPermission} interface specific for
 * commands.
 * 
 * @author Cadyyan
 *
 */
public class CommandPermission extends BasePermission
{
	/**
	 * The block subtype permission node.
	 */
	public static final String BLOCK_PERMISSION_NODE = "block";
	
	/**
	 * Creates a permission node for a command.
	 * 
	 * @param command - the command
	 * @param isAllowed - whether or not the command is allowed
	 */
	public CommandPermission(String command, boolean isAllowed)
	{
		super(BLOCK_PERMISSION_NODE + IPermission.PERMISSION_NODE_DELIMITER + command, isAllowed);
	}
}
