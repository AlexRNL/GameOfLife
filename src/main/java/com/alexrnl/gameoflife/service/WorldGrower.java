package com.alexrnl.gameoflife.service;

import java.util.logging.Logger;

import com.alexrnl.commons.utils.object.ImmutablePair;
import com.alexrnl.gameoflife.world.World;

/**
 * TODO
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
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Computes the number of living cells amongst the neighbours of the cell at the specified
	 * coordinates.
	 * @param world
	 *        the world in which the cells are.
	 * @return the number of living neighbours.
	 */
	protected int getNumberOfLivingNeighbours (final World world, final ImmutablePair<Integer, Integer> cellCoordinates) {
		final int numberOfLivingNeighbours = 0;
		final int leftLine = cellCoordinates.getLeft() - 1;
		final int rightLine = cellCoordinates.getLeft() + 1;
		final int upperLine = cellCoordinates.getRight() - 1;
		final int lowerLine = cellCoordinates.getRight() + 1;
		
		return numberOfLivingNeighbours;
	}
}
