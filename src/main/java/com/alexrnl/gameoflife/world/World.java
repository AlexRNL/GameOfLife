package com.alexrnl.gameoflife.world;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

/**
 * Class representing the world with the cells.<br />
 * @author barfety_a
 */
public class World implements Serializable, Cloneable, Iterable<Entry<Coordinates, Cell>> {
	/** The serial version UID */
	private static final long		serialVersionUID	= 1L;
	
	/** The width of the world */
	private final int				width;
	/** The height of the world */
	private final int				height;
	/** The grid of cells */
	private Map<Coordinates, Cell>	cells;
	
	/**
	 * Constructor #1.<br />
	 * Initialize a world with the dimension specified, and dead cells.
	 * @param width
	 *        the width of the world (must be >1).
	 * @param height
	 *        the height of the world (must be >1).
	 */
	public World (final int width, final int height) {
		super();
		if (width < 1) {
			throw new IllegalArgumentException("Cannot build world with width=" + width + ", minimum value is 1");
		}
		if (height < 1) {
			throw new IllegalArgumentException("Cannot build world with height=" + height + ", minimum value is 1");
		}
		this.width = width;
		this.height = height;
		final Map<Coordinates, Cell> cellsMap = new HashMap<>(width*height, 1.0f);
		for (int indexWidth = 1; indexWidth <= width; indexWidth++) {
			for (int indexHeight = 1; indexHeight <= height; indexHeight++) {
				cellsMap.put(new Coordinates(indexWidth, indexHeight), new Cell(State.DEAD));
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
	 *        the position on the x axis of the grid (from 1 to {@link #getWidth()} - inclusive).
	 * @param y
	 *        the position on the y axis of the grid (from 1 to {@link #getHeight()} - inclusive).
	 * @return the cell at this position.
	 */
	public Cell getCellAt (final int x, final int y) {
		final Coordinates key = new Coordinates(x, y);
		if (!cells.containsKey(key)) {
			throw new NoSuchElementException("No cell at " + key);
		}
		return cells.get(key);
	}
	
	@Override
	public World clone () throws CloneNotSupportedException {
		final World clone = (World) super.clone();
		final Map<Coordinates, Cell> cloneCells = new HashMap<>();
		for (final Entry<Coordinates, Cell> entry : cells.entrySet()) {
			cloneCells.put(entry.getKey(), entry.getValue().clone());
		}
		clone.cells = Collections.unmodifiableMap(cloneCells);
		return clone;
	}
	
	@Override
	public Iterator<Entry<Coordinates, Cell>> iterator () {
		return cells.entrySet().iterator();
	}
	
}
