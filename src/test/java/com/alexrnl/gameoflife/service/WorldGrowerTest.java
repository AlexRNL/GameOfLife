package com.alexrnl.gameoflife.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.alexrnl.commons.utils.object.ImmutablePair;
import com.alexrnl.gameoflife.world.World;

/**
 * Test suite for the {@link WorldGrower} class.
 * @author Alex
 */
public class WorldGrowerTest {
	/** The grower to test */
	private WorldGrower	grower;
	/** World for tests */
	private World		world;

	/**
	 * Set up test attributes.
	 */
	@Before
	public void setUp () {
		grower = new WorldGrower();
		world = new World(4, 4);
	}
	
	/**
	 * Test method for {@link WorldGrower#computeNextGeneration(World)}.
	 */
	@Ignore @Test
	public void testComputeNextGeneration () {
		fail("Not yet implemented"); // TODO
	}
	
	/**
	 * Test method for {@link WorldGrower#getNumberOfLivingNeighbours(World, ImmutablePair)}.
	 */
	@Test
	public void testGetNumberOfLivingNeighbours () {
		world.getCellAt(1, 1).live();
		assertEquals(0, grower.getNumberOfLivingNeighbours(world, new ImmutablePair<>(1, 1)));
		assertEquals(1, grower.getNumberOfLivingNeighbours(world, new ImmutablePair<>(1, 2)));
		assertEquals(1, grower.getNumberOfLivingNeighbours(world, new ImmutablePair<>(2, 1)));
		assertEquals(1, grower.getNumberOfLivingNeighbours(world, new ImmutablePair<>(2, 2)));
		assertEquals(1, grower.getNumberOfLivingNeighbours(world, new ImmutablePair<>(4, 1)));
		assertEquals(1, grower.getNumberOfLivingNeighbours(world, new ImmutablePair<>(4, 2)));
		assertEquals(1, grower.getNumberOfLivingNeighbours(world, new ImmutablePair<>(1, 4)));
		assertEquals(1, grower.getNumberOfLivingNeighbours(world, new ImmutablePair<>(2, 4)));
		assertEquals(1, grower.getNumberOfLivingNeighbours(world, new ImmutablePair<>(4, 4)));
		
		world.getCellAt(3, 2).live();
		assertEquals(0, grower.getNumberOfLivingNeighbours(world, new ImmutablePair<>(1, 1)));
		assertEquals(1, grower.getNumberOfLivingNeighbours(world, new ImmutablePair<>(1, 2)));
		assertEquals(2, grower.getNumberOfLivingNeighbours(world, new ImmutablePair<>(2, 1)));
		assertEquals(2, grower.getNumberOfLivingNeighbours(world, new ImmutablePair<>(2, 2)));
		assertEquals(2, grower.getNumberOfLivingNeighbours(world, new ImmutablePair<>(4, 1)));
		assertEquals(2, grower.getNumberOfLivingNeighbours(world, new ImmutablePair<>(4, 2)));
		assertEquals(1, grower.getNumberOfLivingNeighbours(world, new ImmutablePair<>(1, 4)));
		assertEquals(1, grower.getNumberOfLivingNeighbours(world, new ImmutablePair<>(2, 4)));
		assertEquals(1, grower.getNumberOfLivingNeighbours(world, new ImmutablePair<>(4, 4)));
	}
}
