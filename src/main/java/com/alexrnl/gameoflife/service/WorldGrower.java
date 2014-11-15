package com.alexrnl.gameoflife.service;

import java.util.Map.Entry;
import java.util.logging.Logger;

import com.alexrnl.commons.error.ExceptionUtils;
import com.alexrnl.commons.error.TopLevelError;
import com.alexrnl.commons.utils.object.ImmutablePair;
import com.alexrnl.gameoflife.world.Cell;
import com.alexrnl.gameoflife.world.World;

/**
 * Class which grows a {@link World}.<br />
 * @author barfety_a
 */
public class WorldGrower {
	/** Logger */
	private static final Logger	LG	= Logger.getLogger(WorldGrower.class.getName());
	
	/**
	 * Constructor #1.<br />
	 * Default constructor.
	 */
	public WorldGrower () {
		super();
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
		
		for (final Entry<ImmutablePair<Integer,Integer>, Cell> entry : world) {
			switch (getNumberOfLivingNeighbours(reference, entry.getKey())) {
				case 2:
					// Nothing happens: live cells keep on living and dead cells stay dead
					break;
				case 3:
					// Dead cells are brought to live
					entry.getValue().live();
					break;
				default:
					// Over and under population cases
					entry.getValue().die();
					break;
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
	protected int getNumberOfLivingNeighbours (final World world, final ImmutablePair<Integer, Integer> cellCoordinates) {
		int numberOfLivingNeighbours = 0;
		final int centerLine = cellCoordinates.getLeft();
		final int leftLine = centerLine == 1 ? world.getWidth() : centerLine - 1;
		final int rightLine = centerLine == world.getWidth() ? 1 : centerLine + 1;
		final int middleLine = cellCoordinates.getRight();
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
