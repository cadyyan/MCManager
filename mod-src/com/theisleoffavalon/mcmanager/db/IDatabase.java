package com.theisleoffavalon.mcmanager.db;

import java.io.Closeable;
import java.io.IOException;

/**
 * A simple database interface for retrieving, storing, and modifying data
 * required by the server.
 * 
 * @author Cadyyan
 *
 */
public interface IDatabase extends Closeable
{
	/**
	 * Attempts a login. This merely checks the given username and password pair
	 * against what's stored in the database. If either of them doesn't meet
	 * what is stored the attempt fails. The expecteted password input
	 * is a SHA-1 hashed password.
	 * 
	 * @param username - the username
	 * @param password - the password
	 * @return true if the attempt was successful, false otherwise
	 * @throws IOException thrown when failing to read the database
	 */
	public boolean attemptLogin(String username, String password) throws IOException;
	
	/**
	 * Closes the database and ensures everything is written out if needed.
	 */
	@Override
	public void close() throws IOException;
}
