package com.alexrnl.gameoflife.world;

import java.util.Objects;

/**
 * A single cell of the game of life.
 * @author barfety_a
 */
public class Cell {
	/** The state of the cell */
	private State	state;

	/**
	 * Default constructor.
	 */
	public Cell (State state) {
		super();
		this.state = Objects.requireNonNull(state);
	}
	
	/**
	 * Check if the cell is alive.
	 * @return <code>true</code> if the cell is alive.
	 */
	public boolean isAlive () {
		return State.ALIVE.equals(state);
	}
	
	/**
	 * Check if the cell is dead.
	 * @return <code>true</code> if the cell is dead.
	 */
	public boolean isDead () {
		return State.DEAD.equals(state);
	}
	
	/**
	 * Provoke the death of the cell.
	 */
	public void die () {
		state = State.DEAD;
	}
	
	/**
	 * Bring a cell to life.
	 */
	public void live () {
		state = State.ALIVE;
	}
}
