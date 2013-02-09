package com.theisleoffavalon.mcmanager.network.handler.jsonrpc;

import java.io.IOException;
import java.io.Writer;
import java.security.InvalidParameterException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;

/**
 * This is to be used to hold a JSON RPC response.
 * The response implemented here is compliant
 * with the JSON RPC version 2 revised specification.
 * 
 * @author Cadyyan
 *
 */
public class RpcResponse implements JSONAware, JSONStreamAware
{
	/**
	 * A JSON RPC error code as defined in the spec.
	 * 
	 * @author Cadyyan
	 *
	 */
	public static enum ErrorCode
	{
		PARSE_ERROR(-32700),
		INVALID_REQUEST(-32600),
		METHOD_NOT_FOUND(-32601),
		INVALID_PARAMS(-32602),
		INTERNAL_ERROR(-32603);
		
		// New error codes can be defined in the -32000 to -32099 range.
		
		/**
		 * The error code.
		 */
		private int code;
		
		/**
		 * Creates a new error code.
		 * 
		 * @param code - the designated error code.
		 */
		private ErrorCode(int code)
		{
			this.code = code;
		}
		
		/**
		 * Gets the error code.
		 * 
		 * @return the error code
		 */
		public int getErrorCode()
		{
			return code;
		}
	}
	
	/**
	 * Used when the server encounters a problem facilitating a request.
	 * 
	 * @author Cadyyan
	 *
	 */
	public static class Error implements JSONAware, JSONStreamAware
	{
		/**
		 * The error code.
		 */
		public final int code;
		
		/**
		 * The error message.
		 */
		public final String message;
		
		/**
		 * Any other data that is needed by the client.
		 */
		public final Object data;
		
		/**
		 * Creates a new error condition.
		 * 
		 * @param code - the error code
		 * @param message - the error message
		 * @param data - extra data
		 */
		public Error(ErrorCode code, String message, Object data)
		{
			this.code = code.getErrorCode();
			this.message = message;
			this.data = data;
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
			
			obj.put("code", code);
			obj.put("message", message);
			obj.put("data", data);
			
			return obj.toJSONString();
		}
	}
	
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
	 * The results parameter.
	 */
	public static final String RESULT_PARAM = "result";
	
	/**
	 * The ID parameter.
	 */
	public static final String ID_PARAM = "id";
	
	/**
	 * The RPC method.
	 */
	private String method;
	
	/**
	 * The method results.
	 */
	private Map<String, Object> result;
	
	/**
	 * The request/response ID. This might be <code>null</code>.
	 */
	private String id;
	
	/**
	 * The error if there was one.
	 */
	private Error error;
	
	/**
	 * Creates a JSON RPC response with the given method name and response ID.
	 * 
	 * @param method - the method name
	 * @param id - the ID
	 */
	public RpcResponse(String method, String id)
	{
		this(method, new LinkedHashMap<String, Object>(), id);
	}
	
	/**
	 * Creates a JSON RPC response assuming all information is known already.
	 * 
	 * @param method - the RPC method
	 * @param parameters - the parameters
	 * @param id - the ID
	 */
	public RpcResponse(String method, Map<String, Object> parameters, String id)
	{
		if(method == null)
			throw new NullPointerException("The method name of a JSON RPC request/response must not be null.");
		else if(method.isEmpty())
			throw new InvalidParameterException("The method name of a JSON RPC request/response must not be empty.");
		
		if(id == null)
			throw new NullPointerException("The ID of a JSON RPC response must not be null.");
		else if(id.isEmpty())
			throw new InvalidParameterException("The ID of a JSON RPC response must not be empty.");
		
		this.method = method;
		this.result = parameters;
		this.id = id;
		this.error = null;
	}
	
	/**
	 * Creates a response based off of a request.
	 * 
	 * @param request - a request
	 */
	public RpcResponse(RpcRequest request)
	{
		this(request.getMethod(), request.getID());
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
		return result;
	}
	
	/**
	 * Gets the name of all of the parameters for this request/response.
	 * 
	 * @return the set of parameter names
	 */
	public Set<String> getParameterNames()
	{
		return result.keySet();
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
		if(result.containsKey(parameterName))
			return result.get(parameterName);
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
		
		result.put(parameterName, parameterValue);
	}
	
	/**
	 * Gets the error if there was one.
	 * 
	 * @return the error or <code>null</code>
	 */
	public Error getError()
	{
		return error;
	}
	
	/**
	 * Sets the error of the response. Passing in
	 * <code>null</code> will clear any existing
	 * error.
	 * 
	 * @param error - the error 
	 */
	public void setError(Error error)
	{
		this.error = error;
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
		obj.put(RESULT_PARAM, result);
		obj.put(ID_PARAM, id);
		
		if(error != null)
			obj.put("error", error);
		
		return obj.toJSONString();
	}
}
