package com.theisleoffavalon.mcmanager;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.theisleoffavalon.mcmanager.network.handler.jsonrpc.RpcMethod;
import com.theisleoffavalon.mcmanager.network.handler.jsonrpc.RpcRequest;
import com.theisleoffavalon.mcmanager.network.handler.jsonrpc.RpcResponse;

/**
 * Listens for chat/console messages and buffers them up for
 * retrieval.
 * 
 * @author Cadyyan
 *
 */
public class ConsoleMonitor extends Handler
{
	/**
	 * A sneaky instance of the FML logger.
	 */
	private Logger fmlLogger;
	
	/**
	 * The next available log ID.
	 */
	private int nextLogID;
	
	/**
	 * The list of records.
	 */
	private List<LogRecord> records;
	
	/**
	 * Creates a new chat/console monitor.
	 */
	public ConsoleMonitor()
	{
		this.fmlLogger = Logger.getLogger("ForgeModLoader");
		
		this.nextLogID = 0;
		this.records = new ArrayList<LogRecord>();
	}

	@Override
	public void close() throws SecurityException
	{
		// Make sure we cover our tracks.
		records.clear();
	}

	@Override
	public void flush()
	{
		records.clear();
	}

	@Override
	public void publish(LogRecord record)
	{
		bufferRecord(record);
	}
	
	/**
	 * Starts logging messages.
	 */
	public void startLogging()
	{
		fmlLogger.addHandler(this);
	}
	
	/**
	 * Stops logging messages.
	 */
	public synchronized void stopLogging()
	{
		fmlLogger.removeHandler(this);
		
		synchronized(records)
		{
			records.clear();
		}
	}
	
	/**
	 * Buffers up another log record for retrieval upon request.
	 * 
	 * @param record - the log record
	 */
	private void bufferRecord(LogRecord record)
	{
		synchronized(records)
		{
			int id = nextLogID++;
			
			// Check for overflow.
			if(nextLogID < 0)
				nextLogID = 0;
			
			if(id == records.size())
				records.add(record);
			else
				records.set(id, record);
		}
	}
	
	/**
	 * Handles RPC requests for console messages.
	 * 
	 * @param request - the request
	 * @param response - the response
	 */
	@RpcMethod(method = "consoleMessages", description = "Retrieves console messages." +
														 "A list of messages are returned " +
														 " that have come in since this was last called." +
														 "Each message has an ID associated with it. The last " +
														 "ID should be sent as a parameter the next time this is " +
														 "called. The parameter for this index is called \"from\". " +
														 "If this is the first call this parameter should be ommitted." +
														 "The console messages are returned in the \"messages\" parameter.")
	public void getConsoleMessages(RpcRequest request, RpcResponse response)
	{
		JSONArray messages = new JSONArray();
		
		synchronized(records)
		{
			Integer index = null;
			Object param = request.getParameter("from");
			if(param != null)
			{
				if(param instanceof Integer)
					index = (int)param;
				else if(param instanceof Long)
					index = (int)(long)param;
				else if(param instanceof String)
					index = Integer.parseInt((String)param);
				
				index++;
			}
			else
				index = records.size() - 1; // We'll give them the last message that came in.
			
			while(index < records.size())
			{
				JSONObject record = new JSONObject();
				record.put("id", index);
				record.put("message", records.get(index).getMessage());
				
				messages.add(record);
				
				index++;
			}
		}
		
		response.addResult("messages", messages);
	}
}
