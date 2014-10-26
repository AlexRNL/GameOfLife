package com.alexrnl.gameoflife;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.alexrnl.commons.arguments.Arguments;
import com.alexrnl.commons.arguments.Param;
import com.alexrnl.commons.error.ExceptionUtils;

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
	
	/** The width of the world */
	@Param(names = { "-w" }, description = "the width of the world", required = true)
	private int					width;
	/** The height of the world */
	@Param(names = { "-h" }, description = "the height of the world", required = true)
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
			System.err
					.println("Could not start " + PROGRAM_NAME + ": " + ExceptionUtils.display(e));
			e.printStackTrace();
		}
	}
	
}