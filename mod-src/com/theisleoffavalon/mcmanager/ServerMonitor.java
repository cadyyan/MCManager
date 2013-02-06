package com.theisleoffavalon.mcmanager;

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
	 * The start time of the monitor in seconds. This is assumed to be the up time of the
	 * server.
	 */
	private long startTime;
	
	/**
	 * Creates a new server monitor instance.
	 */
	public ServerMonitor()
	{
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
	}
}
