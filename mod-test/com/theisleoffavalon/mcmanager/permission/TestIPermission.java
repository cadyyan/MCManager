package com.theisleoffavalon.mcmanager.permission;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests the basic {@link IPermission} interface implementation.
 * Since we are testing an abstract implementation we will be using
 * the {@link GenericPermission} class as our instance type.
 * 
 * @author Cadyyan
 *
 */
public class TestIPermission
{
	@Test
	public void testCreation()
	{
		new GenericPermission("*", true);
		new GenericPermission("*", false);
		
		new GenericPermission("some", true);
		new GenericPermission("some.*", true);
		new GenericPermission("some.other", false);
		new GenericPermission("some.other.*", false);
		
		String invalidActions[] = new String[] { null, "", "*.test", "test.*.something",
												 "test,something", "no spaces", "test!", "test@", "test#", "test$", "test%", "test^", "test&", "test*", "test(", "test)", "test-",
												 "test=", "test+", "test`", "test~", "test[", "test]", "test{", "test}", "test\\", "test|" };
		
		for(String invalidAction : invalidActions)
		{
			try
			{
				new GenericPermission(invalidAction, false);
				fail("Failed to reject invalid action string: \"" + invalidAction + "\"");
			}
			catch(Exception e)
			{ }
		}
	}
	
	@Test
	public void testGetAction()
	{
		String action = "some.action";
		
		IPermission perm = new GenericPermission(action, true);
		assertEquals(action, perm.getAction());
		
		perm = new GenericPermission(action, false);
		assertEquals(action, perm.getAction());
	}
	
	@Test
	public void testIsAllowed()
	{
		IPermission perm = new GenericPermission("some.action", true);
		assertTrue(perm.isAllowed());
		
		perm = new GenericPermission("another.action", false);
		assertFalse(perm.isAllowed());
	}
	
	@Test
	public void testSetAllowed()
	{
		IPermission perm = new GenericPermission("some.action", false);
		perm.setAllowed(true);
		assertFalse(perm.isAllowed());
	}
	
	@Test
	public void testAddPermission()
	{
		IPermission parent = new GenericPermission("*", true);
		IPermission child = new GenericPermission("parent.child", false);
	}
	
	@Test
	public void testGetPermission()
	{
		fail("Unimplemented");
	}
	
	@Test
	public void testGetAllPermissions()
	{
		fail("Unimplemented");
	}
	
	@Test
	public void testRemovePermissions()
	{
		fail("Unimplemented");
	}
	
	@Test
	public void removeAllPermissions()
	{
		fail("Unimplemented");
	}
}
