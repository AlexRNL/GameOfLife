package com.alexrnl.gameoflife.world;

import com.alexrnl.commons.utils.object.ImmutablePair;
import com.alexrnl.commons.utils.object.Pair;

/**
 * Class extending a {@link Pair} which represent coordinates in a {@link World}.
 * @author Alex
 */
public class Coordinates extends ImmutablePair<Integer, Integer> {
	/** Serial version UID */
	private static final long	serialVersionUID	= 1394117005261382575L;
	
	/**
	 * Constructor #1.<br />
	 * @param left
	 *        the left object.
	 * @param right
	 *        the right object.
	 */
	public Coordinates (final Integer left, final Integer right) {
		super(left, right);
	}
	
	/**
	 * The x position of the coordinates.
	 * @return the x value.
	 */
	public Integer getX () {
		return getLeft();
	}
	
	/**
	 * The y position of the coordinates.
	 * @return the y value.
	 */
	public Integer getY () {
		return getRight();
	}
}
