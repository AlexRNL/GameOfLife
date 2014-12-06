package com.alexrnl.gameoflife.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.alexrnl.commons.error.ExceptionUtils;
import com.alexrnl.commons.error.TopLevelError;
import com.alexrnl.gameoflife.world.World;

/**
 * Controller for a game of life.<br />
 * @author barfety_a
 */
public class GameOfLifeController {
	/** Logger */
	private static final Logger			LG					= Logger.getLogger(GameOfLifeController.class.getName());
	
	/** The name of the default algorithm for computing hashes */
	private static final String			DEFAULT_ALGORITHM	= "SHA-1";
	
	/** The listeners on the world generation */
	private final List<WorldListener>	worldListeners;
	/** The current generation of the world */
	private final World					currentGeneration;
	/** The history of the generation */
	private final WorldHistory			history;
	/** The class in charge of computing the next step */
	private final WorldGrower			grower;
	
	/**
	 * Constructor #1.<br />
	 * @param initialGeneration
	 *        the initial generation of the world.
	 * @throws NoSuchAlgorithmException
	 *         if the algorithm does not exist in the JVM.
	 */
	public GameOfLifeController (final World initialGeneration) throws NoSuchAlgorithmException {
		this(initialGeneration, DEFAULT_ALGORITHM);
	}

	/**
	 * Constructor #2.<br />
	 * @param initialGeneration
	 *        the initial generation of the world.
	 * @param algorithmName
	 *        the name of the algorithm for history hashes generation.
	 * @throws NoSuchAlgorithmException
	 *         if the algorithm does not exist in the JVM.
	 */
	public GameOfLifeController (final World initialGeneration, final String algorithmName) throws NoSuchAlgorithmException {
		super();
		worldListeners = new ArrayList<>();
		currentGeneration = initialGeneration;
		history = new WorldHistory(MessageDigest.getInstance(algorithmName));
		grower = new WorldGrower();
		
		// Put initial generation in history
		history.addHistory(currentGeneration);
	}
	
	/**
	 * Add a listener to notify on the evolution of the world.
	 * @param listener
	 *        the listener to notify, cannot be <code>null</code>.
	 */
	public void addWorldListener (final WorldListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException("Cannot add null listener to world listeners");
		}
		synchronized (worldListeners) {
			worldListeners.add(listener);
		}
	}
	
	/**
	 * Remove the listener from the world.
	 * @param listener
	 *        the listener to remove, cannot be <code>null</code>.
	 */
	public void removeWorldListener (final WorldListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException("Cannot remove null listener to world listeners");
		}
		synchronized (worldListeners) {
			worldListeners.remove(listener);
		}
	}
	
	/**
	 * Return a safe view of the world listeners.
	 * @return the world listeners.
	 */
	protected List<WorldListener> getWorldListeners () {
		synchronized (worldListeners) {
			return new ArrayList<>(worldListeners);
		}
	}
	
	/**
	 * Grow the world into the next generation.
	 */
	public void grow () {
		grower.computeNextGeneration(currentGeneration);
		final int previousOccurence = history.addHistory(currentGeneration);
		for (final WorldListener worldListener : getWorldListeners()) {
			try {
				if (previousOccurence == WorldHistory.NO_PREVIOUS_OCCURENCE) {
					worldListener.newGeneration(currentGeneration.clone());
				} else {
					worldListener.loopGeneration(currentGeneration.clone(), previousOccurence);
				}
			} catch (final CloneNotSupportedException e) {
				// This should not have happened, the world is cloneable
				LG.severe("Failed to clone world for listeners: " + ExceptionUtils.display(e));
				throw new TopLevelError("World clone failed", e);
			} catch (final Exception e) {
				LG.warning("Exception while calling listener " + worldListener + ": " + ExceptionUtils.display(e));
			}
		}
	}
}
