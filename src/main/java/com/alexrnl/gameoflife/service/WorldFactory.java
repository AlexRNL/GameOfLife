package com.alexrnl.gameoflife.service;

import java.util.Map.Entry;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.alexrnl.gameoflife.world.Cell;
import com.alexrnl.gameoflife.world.Coordinates;
import com.alexrnl.gameoflife.world.World;

/**
 * Class which generates a world.<br />
 * @author Alex
 */
public class WorldFactory {
	/** Logger */
	private static final Logger	LG	= Logger.getLogger(WorldFactory.class.getName());
	
	/** The random generator to use. **/
	private final Random		randomGenerator;
	
	/**
	 * Constructor #1.<br />
	 * @param randomGenerator
	 *        the random generator to use for generating the worlds.
	 */
	public WorldFactory (final Random randomGenerator) {
		super();
		if (randomGenerator == null) {
			throw new IllegalArgumentException("Cannot instantiate world generator with null random");
		}
		this.randomGenerator = randomGenerator;
	}
	
	/**
	 * Constructor #2.<br />
	 * Default constructor.
	 */
	public WorldFactory () {
		this(new Random());
	}
	
	/**
	 * Generates a new world of the dimensions specified, using the alive ratio.
	 * @param width
	 *        the width of the world.
	 * @param height
	 *        the height of the world.
	 * @param aliveRatio
	 *        the ratio of living cells that should be used with the random generator.
	 * @return the world generated.
	 */
	public World generate (final int width, final int height, final double aliveRatio) {
		final World world = new World(width, height);
		if (aliveRatio <= 0.0) {
			// No alive cells
			return world;
		}
		
		int livingCells = 0;
		for (final Entry<Coordinates, Cell> entry : world) {
			if (randomGenerator.nextDouble() < aliveRatio) {
				entry.getValue().live();
				livingCells++;
			}
		}
		
		if (LG.isLoggable(Level.INFO)) {
			LG.info("Created world of size (" + width + ", " + height + ") with " + livingCells
					+ " living cells (alive ratio is " + aliveRatio + ".");
		}
		
		return world;
	}
}
