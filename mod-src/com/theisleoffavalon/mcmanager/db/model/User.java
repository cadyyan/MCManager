package com.theisleoffavalon.mcmanager.db.model;

import com.theisleoffavalon.mcmanager.db.model.Model.IDAttribute;
import com.theisleoffavalon.mcmanager.db.model.Model.ModelAttribute;

/**
 * A user of the system. This has an attached username, password, and more information.
 * 
 * @author Cadyyan
 *
 */
@Model
public class User
{
	/**
	 * The user ID.
	 */
	@ModelAttribute
	@IDAttribute
	private int id;
	
	/**
	 * The username.
	 */
	private String username;
	
	/**
	 * A secure version of the user password. The password should NOT be reversible.
	 */
	@ModelAttribute
	private String password;
	
	/**
	 * Creates a new user instance with default values. This should only be
	 * used by the model handling database code.
	 */
	public User()
	{
		this("", "");
	}
	
	/**
	 * Creates a new user with the given username and password.
	 * 
	 * @param username - the username
	 * @param password - the password
	 */
	public User(String username, String password)
	{
		this.id = -1;
		this.username = username;
		this.password = password;
	}
	
	/**
	 * Gets the ID of the user.
	 * 
	 * @return the user ID
	 */
	public int getID()
	{
		return id;
	}
	
	/**
	 * Gets the username.
	 * 
	 * @return the username
	 */
	public String getUsername()
	{
		return username;
	}
	
	/**
	 * Gets the password.
	 * 
	 * @return the password
	 */
	public String getPassword()
	{
		return password;
	}
	
	/**
	 * Changes the password.
	 * 
	 * @param password - the new password
	 */
	public void setUsername(String password)
	{
		this.password = password;
	}
}
