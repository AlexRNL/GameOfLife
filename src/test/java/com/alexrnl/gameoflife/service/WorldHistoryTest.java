package com.alexrnl.gameoflife.service;

import static com.alexrnl.gameoflife.service.WorldHistory.NO_PREVIOUS_OCCURENCE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import com.alexrnl.commons.error.TopLevelError;
import com.alexrnl.gameoflife.world.World;

/**
 * Test suite for the {@link WorldHistory} class.
 * @author barfety_a
 */
public class WorldHistoryTest {
	/** The history to test */
	private WorldHistory	worldHistory;
	/** The instance of the world for tests */
	private World			world;
	
	/**
	 * Set up test attributes.
	 * @throws NoSuchAlgorithmException
	 *         if the hash algorithm is not available.
	 */
	@Before
	public void setUp () throws NoSuchAlgorithmException {
		worldHistory = new WorldHistory(MessageDigest.getInstance("SHA-1"));
		world = new World(8, 8);
	}
	
	/**
	 * Test method for {@link WorldHistory#addHistory(World)}.
	 */
	@Test
	public void testAddHistory () {
		assertEquals(NO_PREVIOUS_OCCURENCE, worldHistory.addHistory(world));
		assertEquals(1, worldHistory.addHistory(world));
		world.getCellAt(1, 1).live();
		Logger.getLogger(WorldHistory.class.getName()).setLevel(Level.WARNING);
		assertEquals(NO_PREVIOUS_OCCURENCE, worldHistory.addHistory(world));
		world.getCellAt(1, 1).die();
		assertEquals(1, worldHistory.addHistory(world));
		Logger.getLogger(WorldHistory.class.getName()).setLevel(Level.INFO);
	}
	
	/**
	 * Check that a {@link TopLevelError} is thrown when the hash cannot be computed.
	 */
	@Test(expected = TopLevelError.class)
	public void testAddHistoryWithException () {
		final MessageDigest hashAlgorithm = mock(MessageDigest.class);
		worldHistory = new WorldHistory(hashAlgorithm);
		doThrow(IOException.class).when(hashAlgorithm).update(any(byte[].class));
		worldHistory.addHistory(world);
	}
}
