package com.theisleoffavalon.mcmanager.network.handler.jsonrpc;

/**
 * A method decorator that tells the JSON RPC implementation
 * which method to dispatch a request to based on the request's
 * method name.
 * 
 * @author Cadyyan
 *
 */
public @interface RpcMethod
{
	/**
	 * The method that this RPC method handles.
	 * 
	 * @return the RPC method
	 */
	public String method();
	
	/**
	 * A description of this method. This should include information
	 * about expected parameters.
	 * 
	 * @return the method description
	 */
	public String description();
}
