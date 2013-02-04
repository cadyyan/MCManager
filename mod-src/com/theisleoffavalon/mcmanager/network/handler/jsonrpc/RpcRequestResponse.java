package com.theisleoffavalon.mcmanager.network.handler.jsonrpc;

import java.io.IOException;
import java.io.Writer;
import java.security.InvalidParameterException;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONAware;
import org.json.simple.JSONStreamAware;

/**
 * A base implementation of a JSON RPC request
 * and response that is compliant with the JSON
 * RPC version 2.0 revised specification.
 * 
 * @author Cadyyan
 *
 */
public abstract class RpcRequestResponse implements JSONAware, JSONStreamAware
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
	protected String method;
	
	/**
	 * The method parameters.
	 */
	protected Map<String, String> params;
	
	/**
	 * The request/response ID. This might be <code>null</code>.
	 */
	protected String id;
	
	/**
	 * Creates a new JSON RPC request/response.
	 * 
	 * @param method - the RPC method
	 * @param parameters - the parameters
	 * @param id - the ID
	 */
	public RpcRequestResponse(String method, Map<String, String> parameters, String id)
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
	public Map<String, String> getParameters()
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
	 * Gets a parameter from the request/response.
	 * 
	 * @param parameterName - the parameter name
	 * @return a parameter value
	 */
	public String getParameter(String parameterName)
	{
		return params.get(parameterName);
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
	public void addParameter(String parameterName, String parameterValue)
	{
		if(parameterName == null)
			throw new NullPointerException("JSON RPC parameter names cannot be null.");
		else if(parameterName.isEmpty())
			throw new InvalidParameterException("JSON RPC parameter names cannot be empty strings.");
		
		params.put(parameterName, parameterValue);
	}
	
	@Override
	public void writeJSONString(Writer out) throws IOException
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toJSONString()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
