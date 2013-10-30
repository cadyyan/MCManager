package com.theisleoffavalon.mcmanager.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

/**
 * A base implementation of the {@link CommandBase}. This provides
 * some of the common parts of all MCManager commands.
 * 
 * @author Cadyyan
 *
 */
public abstract class Command extends CommandBase
{
	/**
	 * The name of the command.
	 */
	protected String name;
	
	// TODO: add parameter types and names
	
	/**
	 * Creates a new command with the given command name.
	 * 
	 * @param name - the name
	 */
	public Command(String name)
	{
		this.name = name;
	}
	
	@Override
	public String getCommandName()
	{
		return name;
	}

	/**
	 * Gets the command usage.
	 * 
	 * @param sender - the sender of the command
	 */
	@Override
	public abstract String getCommandUsage(ICommandSender sender);
	
	/**
	 * Handles a command being called.
	 * 
	 * @param sender - the sender of the command
	 * @param args - the command arguments
	 */
	@Override
	public abstract void processCommand(ICommandSender sender, String[] args);
}
