package com.theisleoffavalon.mcmanager;

import java.util.Map;

import net.minecraft.command.ICommand;
import net.minecraft.server.MinecraftServer;

import org.json.simple.JSONObject;

import com.theisleoffavalon.mcmanager.network.handler.jsonrpc.RpcMethod;
import com.theisleoffavalon.mcmanager.network.handler.jsonrpc.RpcRequest;
import com.theisleoffavalon.mcmanager.network.handler.jsonrpc.RpcResponse;

/**
 * Handles commands for the mod as well as being a source for retrieving
 * the commands for other mods.
 * 
 * @author Cadyyan
 *
 */
public class CommandManager
{
	/**
	 * Gets all the commands available on the server.
	 * 
	 * @param request - the request
	 * @param response - the response
	 */
	@RpcMethod(method = "getAllCommands", description = "Sends a list of strings that represents the commands the server has available.")
	public void getAllCommands(RpcRequest request, RpcResponse response)
	{
		JSONObject commands = new JSONObject();
		Map<String, ICommand> commandMap = (Map<String, ICommand>)MinecraftServer.getServer().getCommandManager().getCommands();
		for(String command : commandMap.keySet())
		{
			JSONObject commandObj = new JSONObject();
			
			String commandName = command.split("=")[0];
			
			// Currently there's no means for retrieving command parameters so we'l not be using this.
			JSONObject commandParameters = new JSONObject();
			
			commands.put(commandName, commandParameters);
		}
		
		response.addResult("commands", commands);
	}
}
