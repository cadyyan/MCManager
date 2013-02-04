package com.theisleoffavalon.mcmanager.network.handler.jsonrpc;

import java.io.IOException;
import java.io.Writer;
import java.security.InvalidParameterException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;

/**
 * This is to be used for holding a JSON RPC request.
 * This is compliant with the JSON RPC Version 2 Revised format.
 * 
 * @author Cadyyan
 *
 */
public class RpcRequest extends RpcRequestResponse
{
	/**
	 * Creates a JSON RPC request object with only the method name specified.
	 * There WILL be an ID associated with this request that is automatically
	 * generated from a UUID.
	 * 
	 * @param method - the method name
	 */
	public RpcRequest(String method)
	{
		this(method, true);
	}
	
	/**
	 * Creates a JSON RPC request object with only the method name specified.
	 * There will NOT be an ID associated with this request.
	 * 
	 * @param method - the method name
	 * @param createID - if an ID should be auto generated
	 */
	public RpcRequest(String method, boolean createID)
	{
		this(method, new LinkedHashMap<String, String>(), createID ? UUID.randomUUID().toString() : "");
	}
	
	/**
	 * Creates a JSON RPC request object assuming all information about the request
	 * is already known.
	 * 
	 * @param method - the method
	 * @param parameters - the parameters
	 * @param id - the ID of the request (this could be <code>null</code>)
	 */
	public RpcRequest(String method, Map<String, String> parameters, String id)
	{
		super(method, parameters, id);
	}
	
	/**
	 * Checks if this request is a notification.
	 * 
	 * @return true if it is a notification, false otherwise
	 */
	public boolean isNotification()
	{
		return id == null;
	}
}
