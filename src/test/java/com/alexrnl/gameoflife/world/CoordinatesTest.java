package com.alexrnl.gameoflife.world;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link Coordinates} class.
 * @author Alex
 */
public class CoordinatesTest {
	/** The coordinates to test */
	private Coordinates	coordinates;
	
	/**
	 * Set up test attributes.
	 */
	@Before
	public void setUp () {
		coordinates = new Coordinates(1, 2);
	}
	
	/**
	 * Test method for {@link Coordinates#getX()}.
	 */
	@Test
	public void testGetX () {
		assertEquals(Integer.valueOf(1), coordinates.getX());
	}
	
	/**
	 * Test method for {@link Coordinates#getY()}.
	 */
	@Test
	public void testGetY () {
		assertEquals(Integer.valueOf(2), coordinates.getY());
	}
}
