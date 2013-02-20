package com.theisleoffavalon.mcmanager.db.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Denotes a class as being a database model. Database models
 * are handled by the database implementation automatically.
 * A database model is searched for database attributes and
 * the database attributes are then serialized and
 * deserialized automatically.
 * 
 * @author Cadyyan
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Model
{
	/**
	 * Denotes a model attribute that will be written to the database.
	 * 
	 * @author Cadyyan
	 *
	 */
	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface ModelAttribute
	{
	}
	
	/**
	 * Decorates a model attribute to denote that the field is
	 * an ID for the model. This is only allowed on integer
	 * fields.
	 * 
	 * @author Cadyyan
	 *
	 */
	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface IDAttribute
	{
	}
}
