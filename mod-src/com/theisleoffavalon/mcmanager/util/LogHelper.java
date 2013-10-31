package com.theisleoffavalon.mcmanager.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.theisleoffavalon.mcmanager.ModReference;

import cpw.mods.fml.common.FMLLog;

/**
 * A utility class for handling global logging.
 * 
 * @author Cadyyan
 * 
 */
public class LogHelper
{
	/**
	 * The logger instance.
	 */
	private static final Logger log = Logger.getLogger(ModReference.NAME);
	static
	{
		log.setParent(FMLLog.getLogger());
	}

	/**
	 * Writes a debug message to the log.
	 * 
	 * @param message
	 *            - the message
	 */
	public static void debug(String message)
	{
		log.log(Level.FINEST, message);
	}

	/**
	 * Writes an info message to the log.
	 * 
	 * @param message
	 *            - the message
	 */
	public static void info(String message)
	{
		log.info(message);
	}

	/**
	 * Writes a warning message to the log.
	 * 
	 * @param message
	 *            - the message
	 */
	public static void warning(String message)
	{
		log.warning(message);
	}

	/**
	 * Writes an error message to the log.
	 * 
	 * @param message
	 *            - the message
	 */
	public static void error(String message)
	{
		log.severe(message);
	}

	/**
	 * Hidden constructor to stop instantiation.
	 */
	private LogHelper()
	{
	}
}
