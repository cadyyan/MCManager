package com.theisleoffavalon.mcmanager.network.handler.jsonrpc;

import java.io.IOException;
import java.io.Writer;
import java.security.InvalidParameterException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This is to be used to hold a JSON RPC response.
 * The response implemented here is compliant
 * with the JSON RPC version 2 revised specification.
 * 
 * @author Cadyyan
 *
 */
public class RpcResponse extends RpcRequestResponse
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
	public static class Error
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
	}
	
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
		super(method, parameters, id);
		
		// I know these checks should be done first but I'm not allowed to do that...
		if(id == null)
			throw new NullPointerException("The ID of a JSON RPC response must not be null.");
		else if(id.isEmpty())
			throw new InvalidParameterException("The ID of a JSON RPC response must not be empty.");
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toJSONString()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
