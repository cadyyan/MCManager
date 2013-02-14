package com.theisleoffavalon.mcmanager;

import java.util.List;
import java.util.Map;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.StringTranslate;
import net.minecraft.util.StringUtils;

import org.eclipse.jetty.server.Request;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.theisleoffavalon.mcmanager.network.handler.jsonrpc.RpcMethod;
import com.theisleoffavalon.mcmanager.network.handler.jsonrpc.RpcRequest;
import com.theisleoffavalon.mcmanager.network.handler.jsonrpc.RpcResponse;
import com.theisleoffavalon.mcmanager.util.LogHelper;

/**
 * Handles commands for the mod as well as being a source for retrieving
 * the commands for other mods.
 * 
 * @author Cadyyan
 *
 */
public class CommandManager implements ICommandSender
{
	public static enum CommandParameterType
	{
		/**
		 * An integer.
		 */
		INT,
		
		/**
		 * A string.
		 */
		STRING,
		
		/**
		 * A player name (alias of string).
		 */
		PLAYER;
	}
	
	/**
	 * The server command manager.
	 */
	private ICommandManager commandManager;
	
	/**
	 * Creates a new instance of the command manager.
	 */
	public CommandManager()
	{
		this.commandManager = MinecraftServer.getServer().getCommandManager();
	}
	
	/**
	 * Gets all the commands available on the server.
	 * 
	 * @param request - the request
	 * @param response - the response
	 * @param baseRequest - the base request
	 */
	@RpcMethod(method = "getAllCommands", description = "Sends a list of strings that represents the commands the server has available.")
	public void getAllCommands(RpcRequest request, RpcResponse response, Request baseRequest)
	{
		JSONObject commands = new JSONObject();
		Map<String, ICommand> commandMap = (Map<String, ICommand>)MinecraftServer.getServer().getCommandManager().getCommands();
		for(String command : commandMap.keySet())
		{
			JSONObject commandObj = new JSONObject();
			
			String commandName = command.split("=")[0];
			
			// Currently there's no means for retrieving command parameters so we'l not be using this.
			JSONArray params = new JSONArray();
			JSONArray paramTypes = new JSONArray();
			
			// Add some default value
			params.add("args");
			paramTypes.add(CommandParameterType.STRING.name());
			
			JSONObject commandParameters = new JSONObject();
			commandParameters.put("params", params);
			commandParameters.put("paramTypes", paramTypes);
			
			commands.put(commandName, commandParameters);
		}
		
		response.setResult(commands);
	}
	
	/**
	 * Handles requests for Minecraft commands sent over RPC.
	 * 
	 * @param request - the request
	 * @param response - the response
	 * @param baseRequest - the base request
	 */
	@RpcMethod(method = "command", description = "Handles the client sending a command to the Minecraft server.\n" +
												 "This method expects the server command to be sent in the \"command\"\n" +
												 "parameter. The arguments for the command are given as a list of\n" +
												 "strings in the \"args\" parameter.")
	public void command(RpcRequest request, RpcResponse response, Request baseRequest)
	{
		Map<String, Object> commandMap = request.getParametersAsMap();
		
		String commandName = (String)commandMap.get("command");
		
		StringBuilder sb = new StringBuilder();
		Map<String, Object> args = (Map<String, Object>)commandMap.get("args");
		for(Object arg : args.values())
		{
			if(sb.length() > 0)
				sb.append(" ");
			
			sb.append(arg.toString());
		}
		
		String commandArgs = sb.toString();
		
		String command = commandName + " " + commandArgs;
		commandManager.executeCommand(this, command);
		
		response.setResult("success");
	}

	/**
	 * Gets the name that should show up as the person that issued the
	 * command.
	 * 
	 * @return the command sender name
	 */
	@Override
	public String getCommandSenderName()
	{
		return "RPC"; // TODO: this should also eventually include the person who remotely
					  // TODO: issued the command.
	}

	@Override
	public void sendChatToPlayer(String var1)
	{
		LogHelper.info(StringUtils.stripControlCodes(var1));
	}

	@Override
	public boolean canCommandSenderUseCommand(int var1, String var2)
	{
		return true; // TODO: eventually this should probably have some form of
					 // TODO: check on who the person issuing the remote command
					 // TODO: is.
	}

	@Override
	public String translateString(String var1, Object... var2)
	{
		return StringTranslate.getInstance().translateKeyFormat(var1, var2);
	}

	/**
	 * Gets the position of the command sender.
	 * 
	 * @return the in-game position of the command sender
	 */
	@Override
	public ChunkCoordinates getPlayerCoordinates()
	{
		return new ChunkCoordinates(0, 0, 0);
	}
}
