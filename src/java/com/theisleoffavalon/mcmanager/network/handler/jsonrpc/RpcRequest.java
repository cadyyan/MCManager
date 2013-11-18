package com.theisleoffavalon.mcmanager.network.handler.jsonrpc;

import java.io.IOException;
import java.io.Writer;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Map;
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
public class RpcRequest implements JSONAware, JSONStreamAware
{
	/**
	 * The JSON RPC compliance version.
	 */
	public static final String JSON_RPC_VERSION = "2.0";
	
	/**
	 * The JSON RPC version parameter.
	 */
	public static final String VERSION_PARAM = "jsonrpc";
	
	/**
	 * The method parameter.
	 */
	public static final String METHOD_PARAM = "method";
	
	/**
	 * The parameters parameter.
	 */
	public static final String PARAMETERS_PARAM = "params";
	
	/**
	 * The ID parameter.
	 */
	public static final String ID_PARAM = "id";
	
	/**
	 * The RPC method.
	 */
	private String method;
	
	/**
	 * The method parameters.
	 */
	private Object params;
	
	/**
	 * The request/response ID. This might be <code>null</code>.
	 */
	private String id;
	
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
		this(method, null, createID ? UUID.randomUUID().toString() : "");
	}
	
	/**
	 * Creates a JSON RPC request object assuming all information about the request
	 * is already known.
	 * 
	 * @param method - the method
	 * @param parameters - the parameters
	 * @param id - the ID of the request (this could be <code>null</code>)
	 */
	public RpcRequest(String method, Object parameters, String id)
	{
		if(method == null)
			throw new NullPointerException("The method name of a JSON RPC request/response must not be null.");
		else if(method.isEmpty())
			throw new InvalidParameterException("The method name of a JSON RPC request/response must not be empty.");
		
		this.method = method;
		this.params = parameters;
		this.id = id;
	}
	
	/**
	 * Gets the RPC method for this request.
	 * 
	 * @return the RPC method
	 */
	public String getMethod()
	{
		return method;
	}
	
	/**
	 * Gets all of the parameters for this request.
	 * 
	 * @return all of the parameters
	 */
	public Object getParameters()
	{
		return params;
	}
	
	/**
	 * Gets the parameters as a boolean.
	 * 
	 * @return a boolean
	 */
	public boolean getParametersAsBoolean()
	{
		return (Boolean)params;
	}
	
	/**
	 * Gets the parameters as an integer.
	 * 
	 * @return an integer
	 */
	public int getParametersAsInt()
	{
		return (Integer)params;
	}
	
	/**
	 * Gets the parameters as a long.
	 * 
	 * @return a long
	 */
	public long getParametersAsLong()
	{
		return (Long)params;
	}
	
	/**
	 * Gets the parameters as a double.
	 * 
	 * @return a double
	 */
	public double getParametersAsDouble()
	{
		return (Double)params;
	}
	
	/**
	 * Gets the parameters as a string.
	 * 
	 * @return a string
	 */
	public String getParametersAsString()
	{
		return (String)params;
	}
	
	/**
	 * Gets the parameters as a list.
	 * 
	 * @return a list
	 */
	public <T> List<T> getParametersAsList()
	{
		return (List<T>)params;
	}
	
	/**
	 * Gets the parameters as a map.
	 * 
	 * @return a map
	 */
	public <K, V> Map<K, V> getParametersAsMap()
	{
		return (Map<K, V>)params;
	}
	
	/**
	 * Gets the ID for this request/response. This might
	 * return <code>null</code> for requests.
	 * 
	 * @return the request ID
	 */
	public String getID()
	{
		return id;
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
	
	@Override
	public void writeJSONString(Writer out) throws IOException
	{
		out.append(toJSONString());
	}

	@Override
	public String toJSONString()
	{
		JSONObject obj = new JSONObject();
		
		obj.put(VERSION_PARAM, JSON_RPC_VERSION);
		obj.put(METHOD_PARAM, method);
		obj.put(PARAMETERS_PARAM, params == null ? "" : params);
		obj.put(ID_PARAM, id);
		
		return obj.toJSONString();
	}
}
