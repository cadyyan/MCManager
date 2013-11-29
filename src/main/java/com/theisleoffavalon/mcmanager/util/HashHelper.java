package com.theisleoffavalon.mcmanager.util;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Hashing helper.
 *
 * @author Cadyyan
 *
 */
public class HashHelper
{
	public static final Charset CHARSET = Charset.forName("UTF-8");

	/**
	 * Hashes the given string with SHA-256.
	 *
	 * @param string - the string to hash
	 * @return a hashed string
	 */
	public static String hashStringSHA256(String string)
	{
		try
		{
			return hashString(string, MessageDigest.getInstance("SHA-256"));
		}
		catch(NoSuchAlgorithmException e)
		{
			return null; // This should never happen since this is a valid algorithm.
		}
	}

	/**
	 * Performs the given hash on a string.
	 *
	 * @param string - the string to hash
	 * @param digest - the hashing algorithm
	 * @return a hashed string
	 */
	public static String hashString(String string, MessageDigest digest)
	{
		byte originalBytes[] = string.getBytes(CHARSET);
		byte digestedBytes[] = digest.digest(originalBytes);

		return bytesToHexString(digestedBytes);
	}

	/**
	 * Converts a byte array to a hex string.
	 *
	 * @param bytes - a byte array
	 * @return a hex string
	 */
	public static String bytesToHexString(byte bytes[])
	{
		StringBuilder sb = new StringBuilder();

		for(byte b : bytes)
			sb.append(String.format("%02x", b));

		return sb.toString();
	}

	/**
	 * Prevent instantiation.
	 */
	private HashHelper()
	{
	}
}

