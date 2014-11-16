package com.alexrnl.gameoflife.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.MockitoAnnotations.initMocks;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;

import com.alexrnl.gameoflife.world.World;

/**
 * Test suite for the {@link GameOfLifeController} class.
 * @author barfety_a
 */
public class GameOfLifeControllerTest {
	/** The controller to test */
	private GameOfLifeController	controller;
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
		controller = new GameOfLifeController(new World(4, 4));
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
	@Ignore @Test
	public void testGrow () {
		fail("Not yet implemented");
	}
}
