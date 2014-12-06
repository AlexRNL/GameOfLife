package com.alexrnl.gameoflife.service;

import java.util.Map.Entry;
import java.util.logging.Logger;

import com.alexrnl.commons.error.ExceptionUtils;
import com.alexrnl.commons.error.TopLevelError;
import com.alexrnl.gameoflife.world.Cell;
import com.alexrnl.gameoflife.world.Coordinates;
import com.alexrnl.gameoflife.world.World;

/**
 * Class which grows a {@link World}.<br />
 * @author barfety_a
 */
public class WorldGrower {
	/** Logger */
	private static final Logger	LG									= Logger.getLogger(WorldGrower.class.getName());
	
	/** The default threshold under which a cell is dying of under population */
	private static final int	DEFAULT_UNDER_POPULATION_THRESHOLD	= 2;
	/** The default threshold over which a cell is dying of over population */
	private static final int	DEFAULT_OVER_POPULATION_THRESHOLD	= 3;
	
	/** The threshold under which a cell is dying of under population */
	private final int			underPopulationThreshold;
	/** The threshold over which a cell is dying of over population */
	private final int			overPopulationThreshold;
	
	/**
	 * Constructor #1.<br />
	 * @param underPopulationThreshold
	 *        the threshold under which a cell is dying of under population.
	 * @param overPopulationThreshold
	 *        the threshold over which a cell is dying of over population (a cell is coming to life
	 *        is the number of living neighbours is <i>exactly</i> this value.
	 * @throws IllegalArgumentException
	 *         if the under population threshold is over the over population
	 *         threshold.
	 */
	public WorldGrower (final int underPopulationThreshold, final int overPopulationThreshold) throws IllegalArgumentException {
		super();
		if (underPopulationThreshold > overPopulationThreshold) {
			throw new IllegalArgumentException("Bad threshold for over/under population (over-population: "
					+ overPopulationThreshold + "; under-population: " + underPopulationThreshold);
		}
		
		this.underPopulationThreshold = underPopulationThreshold;
		this.overPopulationThreshold = overPopulationThreshold;
	}

	/**
	 * Constructor #2.<br />
	 * Default constructor.
	 */
	public WorldGrower () {
		this(DEFAULT_UNDER_POPULATION_THRESHOLD, DEFAULT_OVER_POPULATION_THRESHOLD);
	}
	
	/**
	 * Compute the next generation of the world.
	 * @param world
	 *        the world to use.
	 */
	public void computeNextGeneration (final World world) {
		final World reference;
		try {
			reference = world.clone();
		} catch (final CloneNotSupportedException e) {
			// This should not have happened, the clone method does not throw this exception for the World class
			LG.severe("Could not create clone of world: " + ExceptionUtils.display(e));
			throw new TopLevelError("Clone failed for World class", e);
		}
		
		for (final Entry<Coordinates, Cell> entry : world) {
			final int livingNeighbours = getNumberOfLivingNeighbours(reference, entry.getKey());
			final Cell cell = entry.getValue();
			if (livingNeighbours < underPopulationThreshold
					|| overPopulationThreshold < livingNeighbours) {
				// Over and under population cases
				cell.die();
			} else if (livingNeighbours == overPopulationThreshold) {
				// Dead cells are brought to life
				cell.live();
			} else {
				// Nothing happens: live cells keep on living and dead cells stay dead
			}
		}
	}
	
	/**
	 * Computes the number of living cells amongst the neighbours of the cell at the specified
	 * coordinates.
	 * @param world
	 *        the world in which the cells are.
	 * @param cellCoordinates
	 *        the coordinates of the cell.
	 * @return the number of living neighbours.
	 */
	protected int getNumberOfLivingNeighbours (final World world, final Coordinates cellCoordinates) {
		int numberOfLivingNeighbours = 0;
		final int centerLine = cellCoordinates.getX();
		final int leftLine = centerLine == 1 ? world.getWidth() : centerLine - 1;
		final int rightLine = centerLine == world.getWidth() ? 1 : centerLine + 1;
		final int middleLine = cellCoordinates.getY();
		final int upperLine = middleLine == 1 ? world.getHeight() : middleLine - 1;
		final int lowerLine = middleLine == world.getHeight() ? 1 : middleLine + 1;
		
		// Left line
		if (world.getCellAt(leftLine, upperLine).isAlive()) {
			numberOfLivingNeighbours++;
		}
		if (world.getCellAt(leftLine, middleLine).isAlive()) {
			numberOfLivingNeighbours++;
		}
		if (world.getCellAt(leftLine, lowerLine).isAlive()) {
			numberOfLivingNeighbours++;
		}
		// Center line
		if (world.getCellAt(centerLine, upperLine).isAlive()) {
			numberOfLivingNeighbours++;
		}
		if (world.getCellAt(centerLine, lowerLine).isAlive()) {
			numberOfLivingNeighbours++;
		}
		// Right line
		if (world.getCellAt(rightLine, upperLine).isAlive()) {
			numberOfLivingNeighbours++;
		}
		if (world.getCellAt(rightLine, middleLine).isAlive()) {
			numberOfLivingNeighbours++;
		}
		if (world.getCellAt(rightLine, lowerLine).isAlive()) {
			numberOfLivingNeighbours++;
		}

		return numberOfLivingNeighbours;
	}
}
