package com.theisleoffavalon.mcmanager;

import java.util.Arrays;
import java.util.List;

import net.minecraft.server.MinecraftServer;

import com.theisleoffavalon.mcmanager.network.handler.jsonrpc.RpcMethod;
import com.theisleoffavalon.mcmanager.network.handler.jsonrpc.RpcRequest;
import com.theisleoffavalon.mcmanager.network.handler.jsonrpc.RpcResponse;

/**
 * Monitors the server and provides easy access to different information about the server.
 * 
 * @author Cadyyan
 *
 */
public class ServerMonitor
{
	/**
	 * An instance of the server.
	 */
	private MinecraftServer server;
	
	/**
	 * The start time of the monitor in seconds. This is assumed to be the up time of the
	 * server.
	 */
	private long startTime;
	
	/**
	 * Creates a new server monitor instance.
	 */
	public ServerMonitor()
	{
		this.server = MinecraftServer.getServer();
		this.startTime = System.currentTimeMillis();
	}
	
	/**
	 * Gets the up time of the server.
	 * 
	 * @return the up time in milliseconds
	 */
	public long getUpTime()
	{
		return System.currentTimeMillis() - startTime;
	}
	
	/**
	 * Gets the maximum amount of memory the server is allocated.
	 * 
	 * @return the amount of memory allocated in bytes
	 */
	public long getMaxAllocatedMemory()
	{
		return Runtime.getRuntime().maxMemory();
	}
	
	/**
	 * Gets the amount of memory that is currently being used.
	 * 
	 * @return the amount of memory in use in bytes
	 */
	public long getUsedMemory()
	{
		return getMaxAllocatedMemory() - Runtime.getRuntime().freeMemory();
	}
	
	/**
	 * Gets a list of all the currently online players.
	 * 
	 * @return the current player list
	 */
	public List<String> getAllOnlinePlayers()
	{
		return Arrays.asList(server.getAllUsernames());
	}
	
	/**
	 * Handles requests for system information.
	 * 
	 * @param request - the request
	 * @param response - the response
	 */
	@RpcMethod(method = "systemInfo", description = "Gets information about the server.")
	public void getSystemInfo(RpcRequest request, RpcResponse response)
	{
		response.addParameter("uptime", getUpTime());
		response.addParameter("usedMemory", getUsedMemory());
		response.addParameter("maxMemory", getMaxAllocatedMemory());
		response.addParameter("players", getAllOnlinePlayers());
	}
}
