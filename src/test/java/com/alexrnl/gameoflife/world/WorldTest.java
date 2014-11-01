package com.alexrnl.gameoflife.world;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link World} class.
 * @author barfety_a
 */
public class WorldTest {
	/** The world to test. */
	private World	world;
	
	/**
	 * Set up test attributes.
	 */
	@Before
	public void setUp () {
		world = new World(8, 12);
	}
	
	/**
	 * Test that a world cannot be build with an invalid width.
	 */
	@SuppressWarnings("unused")
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidWidth () {
		new World(0, 8);
	}
	
	/**
	 * Test that a world cannot be build with an invalid height.
	 */
	@SuppressWarnings("unused")
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidHeight () {
		new World(8, 0);
	}
	
	/**
	 * Test method for {@link World#getWidth()}.
	 */
	@Test
	public void testGetWidth () {
		assertEquals(8, world.getWidth());
	}
	
	/**
	 * Test method for {@link World#getHeight()}.
	 */
	@Test
	public void testGetHeight () {
		assertEquals(12, world.getHeight());
	}
	
	/**
	 * Test method for {@link World#getCellAt(int, int)}.
	 */
	@Test
	public void testGetCellAt () {
		for (int indexWidth = 1; indexWidth <= world.getWidth(); indexWidth++) {
			for (int indexHeight = 1; indexHeight <= world.getHeight(); indexHeight++) {
				assertNotNull(world.getCellAt(indexWidth, indexHeight));
				assertTrue(world.getCellAt(indexWidth, indexHeight).isDead());
			}
		}
		
		// check a random cell modification
		world.getCellAt(2, 4).live();
		assertTrue(world.getCellAt(2, 4).isAlive());
	}
	
	/**
	 * Test that requesting a non-existing cell is throwing a {@link NoSuchElementException}.
	 */
	@Test(expected = NoSuchElementException.class)
	public void testGetNonExistingCell () {
		world.getCellAt(0, 4);
	}
	
	/**
	 * Test method for {@link World#clone()}.
	 * @throws CloneNotSupportedException
	 *         if a clone cannot be performed.
	 */
	@Test
	public void testClone () throws CloneNotSupportedException {
		final World clone = world.clone();
		clone.getCellAt(2, 4).live();
		assertTrue(clone.getCellAt(2, 4).isAlive());
		assertFalse(world.getCellAt(2, 4).isAlive());
	}
}
