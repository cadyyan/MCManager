package com.theisleoffavalon.mcmanager.db;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.theisleoffavalon.mcmanager.db.model.Model;
import com.theisleoffavalon.mcmanager.db.model.Model.IDAttribute;
import com.theisleoffavalon.mcmanager.db.model.Model.ModelAttribute;
import com.theisleoffavalon.mcmanager.db.model.User;

/**
 * A base implementation of the {@link IDatabase} interface.
 * 
 * @author Cadyyan
 *
 */
public abstract class BaseDatabase implements IDatabase
{
	/**
	 * Describes a model. This includes knowing what the ID is for the model,
	 * all the fields and their attributes, and the model values.
	 * 
	 * @author Cadyyan
	 *
	 */
	protected class ModelSchema
	{
		/**
		 * The version of the schema.
		 */
		private int schemaVersion;
		
		/**
		 * The ID attribute of the model.
		 */
		private Field idAttribute;
		
		/**
		 * The fields that should be serialized to the database.
		 */
		private List<Field> modelAttributes;
		
		/**
		 * Creates a schema for the given model object.
		 * 
		 * @param model - the model
		 */
		public ModelSchema(Object model)
		{
			Class klass = model.getClass();
			
			schemaVersion = klass.hashCode();
			System.out.println(schemaVersion);
			
			// Check if this is a model.
			if(klass.getAnnotation(Model.class) == null)
				throw new ModelSchemaException(klass, "The given class is not a model and a schema could not be made.");
			
			modelAttributes = new LinkedList<Field>();
			for(Field field : klass.getFields())
			{
				// Check if this is a model attribute at all.
				if(field.getAnnotation(ModelAttribute.class) == null)
					continue;
				
				modelAttributes.add(field);
				
				// We also want to know if this is the ID field.
				IDAttribute idAttr = null;
				if((idAttr = field.getAnnotation(IDAttribute.class)) != null)
				{
					if(idAttribute != null)
						throw new ModelSchemaException(klass, "Multiple ID fields were given. There can only be one.");
					
					idAttribute = field;
				}
			}
		}
		
		/**
		 * The model schema version.
		 * 
		 * @return the version
		 */
		public int getSchemaVersion()
		{
			return schemaVersion;
		}
		
		/**
		 * The ID attribute for the model.
		 * 
		 * @return the ID attribute
		 */
		public Field getIDAttribute()
		{
			return idAttribute;
		}
		
		/**
		 * The model attributes that should be serialized.
		 * 
		 * @return the attributes
		 */
		public List<Field> getModelAttributes()
		{
			return modelAttributes;
		}
	}
	
	/**
	 * Thrown when there's a problem building or using a model schema.
	 * 
	 * @author Cadyyan
	 *
	 */
	public static class ModelSchemaException extends RuntimeException
	{
		/**
		 * Creates an exception for the given model class.
		 * 
		 * @param modelClass - the model class
		 * @param message - the message
		 */
		public ModelSchemaException(Class modelClass, String message)
		{
			super("Error with " + modelClass.toString() + " model:" + message);
		}
	}
	
	/**
	 * The collection of model schemas that are cached.
	 */
	private Map<Class, ModelSchema> schemas;
	
	/**
	 * Creates a base database implementation.
	 */
	public BaseDatabase()
	{
		this.schemas = new HashMap<Class, ModelSchema>();
	}
	
	@Override
	public boolean attemptLogin(String username, String password) throws IOException
	{
		if(username == null || username.isEmpty())
			throw new IllegalArgumentException("Login requires a username.");
		
		User user = deserialize(getSchema(User.class), "username = ?", username);
		return password.equals(password);
	}
	
	/**
	 * Gets the schema for the model.
	 * 
	 * @param model - the model
	 * @return the schema
	 */
	protected ModelSchema getSchema(Class modelClass)
	{
		if(!schemas.containsKey(modelClass))
		{
			try
			{
				schemas.put(modelClass, new ModelSchema(modelClass.newInstance()));
			}
			catch(InstantiationException e)
			{
				throw new ModelSchemaException(modelClass, "Could not create model instance.");
			}
			catch(IllegalAccessException e)
			{
				throw new ModelSchemaException(modelClass, "Could not create model instance.");
			}
		}
		
		return schemas.get(modelClass);
	}
	
	/**
	 * Serializes a model to the database.
	 * 
	 * @param schema - the model schema
	 * @param model - the model
	 * @throws IOException thrown on failure to serialize model
	 */
	protected abstract <T> void serialize(ModelSchema schema, T model) throws IOException;
	
	/**
	 * Serializes a collection of models to the database.
	 * 
	 * @param schema - the model schema
	 * @param models - the list of models
	 * @throws IOException thrown on failure to serialize model
	 */
	protected abstract <T> void serializeAll(ModelSchema schema, Collection<T> models) throws IOException;
	
	/**
	 * Deserializes a single model from the database. If there was
	 * more than one model that matches the given criteria the first
	 * found is taken.
	 * 
	 * @param schema - the model schema
	 * @param criteria - the search criteria for finding the model
	 * @param criteriaArgs - the actual criteria arguments
	 * @return the model if it exists, <code>null</code> if no such model exists
	 * @throws IOException thrown on failure to deserialize model
	 */
	protected abstract <T> T deserialize(ModelSchema schema, String criteria, String ... criteriaArgs) throws IOException;
	
	/**
	 * Deserializes a list of models form the database.
	 * 
	 * @param schema - the model schema
	 * @param criteria - the search criteria for finding models
	 * @param criteriaArgs - the actual criteria arguments
	 * @return a list of models
	 * @throws IOException thrown on failure to deserialize model
	 */
	protected abstract <T> List<T> deserializeAll(ModelSchema schema, String criteria, String ... criteriaArgs) throws IOException;
}
