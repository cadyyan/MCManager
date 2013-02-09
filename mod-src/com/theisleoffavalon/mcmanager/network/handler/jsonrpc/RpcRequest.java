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
	private Map<String, Object> params;
	
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
		this(method, new LinkedHashMap<String, Object>(), createID ? UUID.randomUUID().toString() : "");
	}
	
	/**
	 * Creates a JSON RPC request object assuming all information about the request
	 * is already known.
	 * 
	 * @param method - the method
	 * @param parameters - the parameters
	 * @param id - the ID of the request (this could be <code>null</code>)
	 */
	public RpcRequest(String method, Map<String, Object> parameters, String id)
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
	 * Gets the RPC method for this request/response.
	 * 
	 * @return the RPC method
	 */
	public String getMethod()
	{
		return method;
	}
	
	/**
	 * Gets all of the parameters for this request/response.
	 * 
	 * @return all of the parameters
	 */
	public Map<String, Object> getParameters()
	{
		return params;
	}
	
	/**
	 * Gets the name of all of the parameters for this request/response.
	 * 
	 * @return the set of parameter names
	 */
	public Set<String> getParameterNames()
	{
		return params.keySet();
	}
	
	/**
	 * Gets a parameter from the request/response. If
	 * the parameter does not exist then <code>null</code>
	 * is returned.
	 * 
	 * @param parameterName - the parameter name
	 * @return a parameter value
	 */
	public Object getParameter(String parameterName)
	{
		if(params.containsKey(parameterName))
			return params.get(parameterName);
		else
			return null;
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
	 * Adds a parameter to the request/response.
	 * 
	 * @param parameterName - the name of the parameter
	 * @param parameterValue - the value of the parameter
	 */
	public void addParameter(String parameterName, Object parameterValue)
	{
		if(parameterName == null)
			throw new NullPointerException("JSON RPC parameter names cannot be null.");
		else if(parameterName.isEmpty())
			throw new InvalidParameterException("JSON RPC parameter names cannot be empty strings.");
		
		params.put(parameterName, parameterValue);
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
		obj.put(PARAMETERS_PARAM, params);
		obj.put(ID_PARAM, id);
		
		return obj.toJSONString();
	}
}
