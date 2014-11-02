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
 * TODO
 * @author barfety_a
 */
public class GameOfLifeController {
	/** Logger */
	private final static Logger			LG	= Logger.getLogger(GameOfLifeController.class.getName());
	
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
	 */
	public GameOfLifeController (final World initialGeneration) throws NoSuchAlgorithmException {
		super();
		worldListeners = new ArrayList<>();
		currentGeneration = initialGeneration;
		history = new WorldHistory(MessageDigest.getInstance("SHA-1"));
		grower = new WorldGrower();
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
			}
		}
	}
}
