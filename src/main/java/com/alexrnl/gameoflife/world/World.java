package com.alexrnl.gameoflife.world;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.alexrnl.commons.utils.object.ImmutablePair;

/**
 * Class representing the world with the cells.
 * @author barfety_a
 */
public class World {
	/** The width of the world */
	private final int											width;
	/** The height of the world */
	private final int											height;
	/** The grid of cells */
	private final Map<ImmutablePair<Integer, Integer>, Cell>	cells;
	
	/**
	 * Constructor #1.<br />
	 * Initialize a world with the dimension specified, and dead cells.
	 * @param width
	 *        the width of the world.
	 * @param height
	 *        the height of the world.
	 */
	public World (final int width, final int height) {
		super();
		this.width = width;
		this.height = height;
		final Map<ImmutablePair<Integer, Integer>, Cell> cellsMap = new HashMap<>(width*height, 1.0f);
		for (int indexWidth = 1; indexWidth <= width; indexWidth++) {
			for (int indexHeight = 1; indexHeight <= height; indexHeight++) {
				cellsMap.put(new ImmutablePair<>(indexWidth, indexHeight), new Cell(State.DEAD));
			}
		}
		cells = Collections.unmodifiableMap(cellsMap);
	}
	
	/**
	 * Return the attribute width.
	 * @return the attribute width.
	 */
	public int getWidth () {
		return width;
	}
	
	/**
	 * Return the attribute height.
	 * @return the attribute height.
	 */
	public int getHeight () {
		return height;
	}
	
	/**
	 * Return the cell at the position specified.
	 * @param x
	 *        the position on the x axis of the grid (from 0 to {@link #getWidth()}).
	 * @param y
	 *        the position on the y axis of the grid (from 0 to {@link #getHeight()}).
	 * @return the cell at this position.
	 */
	public Cell getCellAt (final int x, final int y) {
		return cells.get(new ImmutablePair<>(x, y));
	}
}
