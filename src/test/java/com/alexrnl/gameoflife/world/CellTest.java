package com.alexrnl.gameoflife.world;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.alexrnl.commons.utils.object.ReflectUtils;

/**
 * Test suite for the {@link Cell} class.
 * @author barfety_a
 */
public class CellTest {
	/** An alive cell */
	private Cell	aliveCell;
	/** A dead cell */
	private Cell	deadCell;
	
	/**
	 * Set up test attributes.
	 */
	@Before
	public void setUp () {
		aliveCell = new Cell(State.ALIVE);
		deadCell = new Cell(State.DEAD);
	}
	
	/**
	 * Trigger a full enum coverage on the {@link State} enum.
	 */
	@Test
	public void fullStateEnumCoverage () {
		ReflectUtils.fullEnumCoverage(State.class);
	}
	
	/**
	 * Test method for {@link Cell#isAlive()}.
	 */
	@Test
	public void testIsAlive () {
		assertTrue(aliveCell.isAlive());
		assertFalse(deadCell.isAlive());
	}
	
	/**
	 * Test method for {@link Cell#isDead()}.
	 */
	@Test
	public void testIsDead () {
		assertFalse(aliveCell.isDead());
		assertTrue(deadCell.isDead());
	}
	
	/**
	 * Test method for {@link Cell#die()}.
	 */
	@Test
	public void testDie () {
		aliveCell.die();
		assertTrue(aliveCell.isDead());
		deadCell.die();
		assertTrue(deadCell.isDead());
	}
	
	/**
	 * Test method for {@link Cell#live()}.
	 */
	@Test
	public void testLive () {
		aliveCell.live();
		assertTrue(aliveCell.isAlive());
		deadCell.live();
		assertTrue(deadCell.isAlive());
	}
	
	/**
	 * Test method for {@link Cell#clone()}.
	 * @throws CloneNotSupportedException
	 *         if a clone cannot be performed.
	 */
	@Test
	public void testClone () throws CloneNotSupportedException {
		final Cell clone = aliveCell.clone();
		clone.die();
		assertTrue(clone.isDead());
		assertFalse(aliveCell.isDead());
	}
}
