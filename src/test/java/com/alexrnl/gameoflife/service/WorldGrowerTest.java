package com.alexrnl.gameoflife.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

import com.alexrnl.commons.error.TopLevelError;
import com.alexrnl.commons.utils.object.ImmutablePair;
import com.alexrnl.gameoflife.world.Cell;
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
	@Test
	public void testComputeNextGeneration () {
		world.getCellAt(2, 1).live();
		world.getCellAt(2, 2).live();
		world.getCellAt(2, 3).live();
		grower.computeNextGeneration(world);
		
		for (final Entry<ImmutablePair<Integer, Integer>, Cell> entry : world) {
			if (entry.getKey().getRight() == 2 && entry.getKey().getLeft() < 4) {
				assertTrue(entry.getValue().isAlive());
			} else {
				assertTrue(entry.getValue().isDead());
			}
		}
		
		grower.computeNextGeneration(world);
		for (final Entry<ImmutablePair<Integer, Integer>, Cell> entry : world) {
			if (entry.getKey().getLeft() == 2 && entry.getKey().getRight() < 4) {
				assertTrue(entry.getValue().isAlive());
			} else {
				assertTrue(entry.getValue().isDead());
			}
		}
	}
	
	/**
	 * Test that a {@link TopLevelError} is thrown when a clone exception occurs.
	 * @throws CloneNotSupportedException
	 *         should not be thrown.
	 */
	@Test(expected = TopLevelError.class)
	public void testComputeNextGenerationCloneException () throws CloneNotSupportedException {
		final World mockedWorld = mock(World.class);
		when(mockedWorld.clone()).thenThrow(new CloneNotSupportedException());
		grower.computeNextGeneration(mockedWorld);
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
