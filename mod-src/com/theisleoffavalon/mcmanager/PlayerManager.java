package com.theisleoffavalon.mcmanager;

import java.util.Map;
import java.util.UUID;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.theisleoffavalon.mcmanager.network.handler.jsonrpc.RpcMethod;
import com.theisleoffavalon.mcmanager.network.handler.jsonrpc.RpcRequest;
import com.theisleoffavalon.mcmanager.network.handler.jsonrpc.RpcResponse;
import com.theisleoffavalon.mcmanager.network.handler.jsonrpc.RpcResponse.Error;
import com.theisleoffavalon.mcmanager.network.handler.jsonrpc.RpcResponse.ErrorCode;

/**
 * Manages player interactions in-game and through the RPC connection.
 * 
 * @author Cadyyan
 *
 */
public class PlayerManager
{
	/**
	 * A map of authorization tokens to users.
	 */
	private BiMap<String, String> users;
	
	/**
	 * Creates a new player manager.
	 */
	public PlayerManager()
	{
		this.users = HashBiMap.create();
	}
	
	/**
	 * Handles requests to authenticate over RPC.
	 * 
	 * @param request - the request
	 * @param response - the response
	 */
	@RpcMethod(method = "login", description = "A login attempt must be made with two parameters. The first\n" +
											   "is the username of the person. The second is the password. The\n" +
											   "password should be sent as a SHA-256 hashed text. The result of\n" +
											   "this operation is either an error for failure to login or success\n" +
											   "which results in an authentication token being sent to the client.\n" +
											   "The token should be used from then on to authenticate the user. The\n" +
											   "expected locale for the hashed password is UTF-8 and all characters\n" +
											   "should be lower case.")
	public void rpcLogin(RpcRequest request, RpcResponse response)
	{
		Map<String, String> loginRequest = request.getParametersAsMap();
		String username = loginRequest.get("username");
		String password = loginRequest.get("password");
		
		// Check if the user is already logged in.
		if(users.containsValue(username))
		{
			response.setError(new Error(ErrorCode.INVALID_REQUEST, "You are already logged in.", null));
			return;
		}
		
		// TODO: for now just always allow login.
		// TODO: snip actual auth code
		
		// Since we're authenticated let's generate a auth token.
		String authToken = UUID.randomUUID().toString();
		
		users.put(authToken, username);
		
		response.setResult(authToken);
	}
	
	/**
	 * Handles requests to become unauthenticated.
	 * 
	 * @param request - the request
	 * @param response - the response
	 */
	@RpcMethod(method = "logout", description = "Logs a user out if they are currently logged in. They must be\n" +
												"logged in to do this so they should have an authorization token\n" +
												"to use this method and it should be passed as the only parameter.\n" +
												"A success string is returned because RPC requires some form of return value.")
	public void rpcLogout(RpcRequest request, RpcResponse response)
	{
		String authToken = request.getParametersAsString();
		
		// Check if the user is logged in.
		if(authToken == null || !users.containsKey(authToken))
		{
			response.setError(new Error(ErrorCode.INVALID_REQUEST, "You are not logged in.", null));
			return;
		}
		
		users.remove(authToken);
		
		response.setResult("success");
	}
}
