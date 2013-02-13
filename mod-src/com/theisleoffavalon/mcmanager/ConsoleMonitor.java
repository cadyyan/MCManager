package com.theisleoffavalon.mcmanager;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Formatter;
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
	 * Formats the log messages. Apparently the ConsoleLogFormatter that's built in
	 * has to be package level visibility so no one can use it. That's cool. I guess.
	 * 
	 * @author Cadyyan
	 *
	 */
	public class LogFormatter extends Formatter
	{
		/**
		 * The date formatter.
		 */
		private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		@Override
		public String format(LogRecord record)
		{
			StringBuilder sb = new StringBuilder();
			
			sb.append(dateFormatter.format(Long.valueOf(record.getMillis())));
			
			Level level = record.getLevel();
			sb.append(" [");
			sb.append(level.toString());
			sb.append("] ");
			
			sb.append(record.getMessage());
			sb.append("\n");
			
			Throwable thrown = record.getThrown();
			if(thrown != null)
			{
				StringWriter sw = new StringWriter();
				thrown.printStackTrace(new PrintWriter(sw));
				sb.append(sw.toString());
			}
			
			return sb.toString();
		}
	}
	
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
		this.setFormatter(new LogFormatter());
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
					index = (Integer)param;
				else if(param instanceof Long)
					index = ((Long)param).intValue();
				else if(param instanceof String)
					index = Integer.parseInt((String)param);
				index++;
			}
			
			if(param == null || index == -1)
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
		
		response.setResult(messages);
	}
}
