package com.alexrnl.gameoflife.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.alexrnl.commons.error.TopLevelError;
import com.alexrnl.gameoflife.world.World;

/**
 * Test suite for the {@link GameOfLifeController} class.
 * @author barfety_a
 */
public class GameOfLifeControllerTest {
	/** The controller to test */
	private GameOfLifeController	controller;
	/** The world used by the controller */
	private World					world;
	/** The mocked listener */
	@Mock
	private WorldListener			listener;
	
	/**
	 * Set up test attributes.
	 * @throws NoSuchAlgorithmException
	 *         if the algorithm does not exists.
	 */
	@Before
	public void setUp () throws NoSuchAlgorithmException {
		initMocks(this);
		world = new World(4, 4);
		controller = new GameOfLifeController(world);
		controller.addWorldListener(listener);
	}
	
	/**
	 * Test method for {@link GameOfLifeController#addWorldListener(WorldListener)}.
	 */
	@Test
	public void testAddWorldListener () {
		final WorldListener secondListener = mock(WorldListener.class);
		controller.addWorldListener(secondListener);
		
		final List<WorldListener> listeners = controller.getWorldListeners();
		assertEquals(2, listeners.size());
		assertTrue(listeners.contains(listener));
		assertTrue(listeners.contains(secondListener));
	}
	
	/**
	 * Test that adding a <code>null</code> listener is not permitted.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAddNullWorldListener () {
		controller.addWorldListener(null);
	}
	
	/**
	 * Test method for {@link GameOfLifeController#removeWorldListener(WorldListener)}.
	 */
	@Test
	public void testRemoveWorldListener () {
		controller.removeWorldListener(listener);
		assertTrue(controller.getWorldListeners().isEmpty());
	}
	
	/**
	 * Test that removing a <code>null</code> listener is not permitted.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testRemoveNullWorldListener () {
		controller.removeWorldListener(null);
	}
	
	/**
	 * Test method for {@link GameOfLifeController#getWorldListeners()}.
	 */
	@Test
	public void testGetWorldListeners () {
		final List<WorldListener> listeners = controller.getWorldListeners();
		assertEquals(1, listeners.size());
		assertEquals(listener, listeners.get(0));
	}
	
	/**
	 * Test method for {@link GameOfLifeController#grow()}.
	 */
	@Test
	public void testGrow () {
		// Set-up world representation
		world.getCellAt(2, 1).live();
		world.getCellAt(2, 2).live();
		world.getCellAt(2, 3).live();
		
		controller.grow();
		verify(listener).newGeneration(any(World.class));
		controller.grow();
		verify(listener, times(2)).newGeneration(any(World.class));
		controller.grow();
		verify(listener).loopGeneration(any(World.class), eq(2));
	}
	
	/**
	 * Test method for {@link GameOfLifeController#grow()}.
	 * Check that an exception in a listener does not disturb other listeners.
	 */
	@Test
	public void testGrowListenerException () {
		final WorldListener secondListener = mock(WorldListener.class);
		controller.addWorldListener(secondListener);
		doThrow(NullPointerException.class).when(listener).loopGeneration(any(World.class), eq(1));
		
		controller.grow();
		verify(secondListener).loopGeneration(any(World.class), eq(1));
	}
	
	/**
	 * Test method for {@link GameOfLifeController#grow()}.
	 * Check that a clone exception is throwing a {@link TopLevelError}.
	 */
	@Test(expected = TopLevelError.class)
	public void testGrowWorldCloneException () {
		doThrow(CloneNotSupportedException.class).when(listener).loopGeneration(any(World.class), eq(1));
		controller.grow();
	}
}
