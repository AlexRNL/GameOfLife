package com.alexrnl.gameoflife.service;

import com.alexrnl.gameoflife.world.World;

/**
 * Interface for listeners on a world in the game of life.
 * @author barfety_a
 */
public interface WorldListener {
	
	/**
	 * Called when a new generation has been generated.
	 * @param world
	 *        the world generated.
	 */
	void newGeneration (World world);
	
	/**
	 * Called when a world that was previously generated is generated again.
	 * @param world
	 *        the world generated.
	 * @param previousOccurence
	 *        the number of the generation is has occurred before.
	 */
	void loopGeneration (World world, int previousOccurence);
}
