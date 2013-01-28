package com.theisleoffavalon.mcmanager.network.handler;

import java.io.PrintStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.handler.AbstractHandler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * A specialized implementation of the {@link HttpHandler}
 * interface. This interface allows us to format the incoming
 * request as needed to send and receive into the expected format.
 * Handlers should not write directly to the output stream. They
 * should instead write to the stream that is provided to each
 * handler method.
 * 
 * @author Cadyyan
 *
 */
public interface IWebRequestHandler
{
	/**
	 * The HTTP status codes that are likely to be sent. This list
	 * is likely to be incomplete but it works for most cases. Definitions
	 * for status code are taken directly from the W3 Consortium RFC2616
	 * specification on HTTP/1.1.
	 * 
	 * @author Cadyyan
	 *
	 */
	public static enum StatusCode
	{
		/**
		 * <p>
		 * The request has succeeded. The information returned with the response is dependent on the method used in the request, for example:
		 * </p>
		 *
		 * <p>
		 * <strong>GET</strong> an entity corresponding to the requested resource is sent in the response;
		 * </p>
		 *
		 * <p>
		 * <strong>HEAD</strong> the entity-header fields corresponding to the requested resource are sent in the response without any message-body;
		 * </p>
		 *
		 * <p>
		 * <strong>POST</strong> an entity describing or containing the result of the action;
		 * </p>
		 *
		 * <p>
		 * <strong>TRACE</strong> an entity containing the request message as received by the end server.
		 * </p>
		 */
		OK(200),
		
		/**
		 * The request could not be understood by the server due to malformed syntax.
		 * The client SHOULD NOT repeat the request without modifications.
		 */
		BAD_REQUEST(400),
		
		/**
		 * The request requires user authentication. The response MUST include a WWW-Authenticate header
		 * field (section 14.47) containing a challenge applicable to the requested resource. The client
		 * MAY repeat the request with a suitable Authorization header field (section 14.8). If the request
		 * already included Authorization credentials, then the 401 response indicates that authorization
		 * has been refused for those credentials. If the 401 response contains the same challenge as the
		 * prior response, and the user agent has already attempted authentication at least once, then
		 * the user SHOULD be presented the entity that was given in the response, since that entity might
		 * include relevant diagnostic information. HTTP access authentication is explained in
		 * "HTTP Authentication: Basic and Digest Access Authentication" [43].
		 */
		UNAUTHORIZED(401),
		
		/**
		 * The server understood the request, but is refusing to fulfill it. Authorization will not help
		 * and the request SHOULD NOT be repeated. If the request method was not HEAD and the server wishes
		 * to make public why the request has not been fulfilled, it SHOULD describe the reason for the
		 * refusal in the entity. If the server does not wish to make this information available to the
		 * client, the status code 404 (Not Found) can be used instead.
		 */
		FORBIDDEN(403),
		
		/**
		 * The server has not found anything matching the Request-URI. No indication is given of whether
		 * the condition is temporary or permanent. The 410 (Gone) status code SHOULD be used if the server
		 * knows, through some internally configurable mechanism, that an old resource is permanently
		 * unavailable and has no forwarding address. This status code is commonly used when the server
		 * does not wish to reveal exactly why the request has been refused, or when no other response
		 * is applicable.
		 */
		NOT_FOUND(404),
		
		/**
		 * The method specified in the Request-Line is not allowed for the resource identified by the
		 * Request-URI. The response MUST include an Allow header containing a list of valid methods for
		 * the requested resource.
		 */
		METHOD_NOT_ALLOWED(405),
		
		/**
		 * The server encountered an unexpected condition which prevented it from fulfilling the request.
		 */
		INTERNAL_SERVER_ERROR(500),
		
		/**
		 * The server does not support the functionality required to fulfill the request. This is the
		 * appropriate response when the server does not recognize the request method and is not
		 * capable of supporting it for any resource.
		 */
		NOT_IMPLMENTED(501);
		
		/**
		 * The actual code for this HTTP message.
		 */
		private int httpCode;
		
		/**
		 * Creates an enum instance for the given HTTP code.
		 * 
		 * @param httpCode - the designated HTTP code
		 */
		private StatusCode(int httpCode)
		{
			this.httpCode = httpCode;
		}
		
		/**
		 * Gets the HTTP code for this status code.
		 * 
		 * @return the HTTP code
		 */
		public int getHttpCode()
		{
			return this.httpCode;
		}
	}
	
	/**
	 * Handles HTTP-GET requests.
	 * 
	 * @param request - the request object
	 * @param response - the response object
	 * @param out - the stream to write out to
	 * @return the status code result
	 */
	public StatusCode get(HttpServletRequest request, HttpServletResponse response, PrintStream out);
	
	/**
	 * Handles HTTP-HEAD requests. This is optional
	 * to implement.
	 * 
	 * @param request - the request object
	 * @param response - the response object
	 * @param out - the stream to write out to
	 * @return the status code result
	 */
	public StatusCode head(HttpServletRequest request, HttpServletResponse response, PrintStream out);
	
	/**
	 * Handles HTTP-POST requests.
	 * 
	 * @param request - the request object
	 * @param response - the response object
	 * @param out - the stream to write out to
	 * @return the status code result
	 */
	public StatusCode post(HttpServletRequest request, HttpServletResponse response, PrintStream out);
	
	/**
	 * Handles HTTP-PUT requests. This is optional
	 * to implement.
	 * 
	 * @param request - the request object
	 * @param response - the response object
	 * @param out - the stream to write out to
	 * @return the status code result
	 */
	public StatusCode put(HttpServletRequest request, HttpServletResponse response, PrintStream out);
	
	/**
	 * Handles HTTP-DELETE requests. This is optional
	 * to implement.
	 * 
	 * @param request - the request object
	 * @param response - the response object
	 * @param out - the stream to write out to
	 * @return the status code result
	 */
	public StatusCode delete(HttpServletRequest request, HttpServletResponse response, PrintStream out);
	
	/**
	 * Handles the HTTP-TRACE requests. This is optional
	 * to implement.
	 * 
	 * @param request - the request object
	 * @param response - the response object
	 * @param out - the stream to write out to
	 * @return the status code result
	 */
	public StatusCode trace(HttpServletRequest request, HttpServletResponse response, PrintStream out);
	
	/**
	 * Handles the HTTP-CONNECT requests. This is optional
	 * to implement.
	 * 
	 * @param request - the request object
	 * @param response - the response object
	 * @param out - the stream to write out to
	 * @return the status code result
	 */
	public StatusCode connect(HttpServletRequest request, HttpServletResponse response, PrintStream out);
}
