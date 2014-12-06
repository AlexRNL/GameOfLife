package com.alexrnl.gameoflife;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.alexrnl.commons.arguments.Arguments;
import com.alexrnl.commons.arguments.Param;
import com.alexrnl.commons.error.ExceptionUtils;
import com.alexrnl.gameoflife.service.GameOfLifeController;
import com.alexrnl.gameoflife.service.WorldFactory;
import com.alexrnl.gameoflife.service.WorldListener;
import com.alexrnl.gameoflife.world.Cell;
import com.alexrnl.gameoflife.world.Coordinates;
import com.alexrnl.gameoflife.world.World;

/**
 * Class which launches the game of life.
 * @author barfety_a
 */
public class App {
	/** Logger */
	private static final Logger	LG					= Logger.getLogger(App.class.getName());
	
	/** The name of the program */
	private static final String	PROGRAM_NAME		= "GameOfLife";
	/** The default value for the alive ratio cell */
	private static final double	DEFAULT_ALIVE_RATIO	= 0.3;
	
	/** The random generator to use */
	private final Random		random;
	/** Flag indicating to leave the generation */
	private boolean				leave;
	
	// Command line parameters
	/** The width of the world */
	@Param(names = { "-x" }, description = "the width of the world", required = true)
	private int					width;
	/** The height of the world */
	@Param(names = { "-y" }, description = "the height of the world", required = true)
	private int					height;
	/** The ratio of alive cells in the initial state */
	@Param(names = { "-r" }, description = "the ratio of alive cell in the initial state")
	private final double				aliveRatio;
	
	/**
	 * Constructor for the application.
	 * @param arguments
	 *        the arguments of the application.
	 * @throws IllegalArgumentException
	 *         if the arguments provided are invalid.
	 */
	public App (final List<String> arguments) throws IllegalArgumentException {
		super();
		random = new Random();
		leave = false;
		
		aliveRatio = DEFAULT_ALIVE_RATIO;
		new Arguments(PROGRAM_NAME, this).parse(arguments);
		
		// Validate arguments
		if (width < 0) {
			throw new IllegalArgumentException("Null or negative width is not allowed");
		}
		if (height < 0) {
			throw new IllegalArgumentException("Null or negative height is not allowed");
		}
		if (aliveRatio < 0 || aliveRatio > 1.0) {
			throw new IllegalArgumentException("Ratio of alive cells must be between 0 and 1");
		}
	}
	
	/**
	 * Launch the application.
	 */
	public void launch () {
		if (LG.isLoggable(Level.INFO)) {
			LG.info("Launching " + PROGRAM_NAME);
		}
		
		final GameOfLifeController controller;
		try {
			controller = new GameOfLifeController(new WorldFactory(random).generate(width, height, aliveRatio));
		} catch (final NoSuchAlgorithmException e) {
			LG.severe("Alorithm for hashes do not exists: " + ExceptionUtils.display(e));
			return;
		}
		controller.addWorldListener(new WorldListener() {
			@Override
			public void newGeneration (final World world) {
				final SortedMap<Coordinates, Cell> sortedWorld = new TreeMap<>(new Comparator<Coordinates>() {
					@Override
					public int compare (final Coordinates o1, final Coordinates o2) {
						final int ordinateComparison = o1.getY().compareTo(o2.getY());
						if (ordinateComparison != 0) {
							return ordinateComparison;
						}
						return o1.getX().compareTo(o2.getX());
					}
				});
				for (final Entry<Coordinates, Cell> entry : world) {
					sortedWorld.put(entry.getKey(), entry.getValue());
				}
				
				final StringBuilder worldString = new StringBuilder();
				int lastRow = 1;
				for (final Entry<Coordinates, Cell> entry : sortedWorld.entrySet()) {
					if (lastRow != entry.getKey().getY()) {
						worldString.append(System.lineSeparator());
					}
					lastRow = entry.getKey().getY();
					worldString.append(entry.getValue().isDead() ? ' ' : 'x');
				}
				System.out.println(worldString.toString());
			}
			
			@Override
			public void loopGeneration (final World world, final int previousOccurence) {
				newGeneration(world);
				System.out.println("Loop detected, previous occurence in generation " + previousOccurence);
				leave = true;
			}
		});
		
		while (!leave) {
			controller.grow();
			try {
				Thread.sleep(50);
			} catch (final InterruptedException e) {}
		}
		
		if (LG.isLoggable(Level.INFO)) {
			LG.info("Exiting " + PROGRAM_NAME);
		}
	}
	
	/**
	 * Entry point.
	 * @param args
	 *        the argument from the command line.
	 */
	public static void main (final String[] args) {
		try {
			new App(Arrays.asList(args)).launch();
		} catch (final IllegalArgumentException e) {
			System.err.println("Could not start " + PROGRAM_NAME + ": " + ExceptionUtils.display(e));
			e.printStackTrace();
		}
	}
	
}