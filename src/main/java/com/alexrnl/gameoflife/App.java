package com.alexrnl.gameoflife;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class which launches the game of life.
 * @author barfety_a
 */
public class App {
	/** Logger */
	private static final Logger	LG				= Logger.getLogger(App.class.getName());
	
	/** The name of the program */
	private static final String	PROGRAM_NAME	= "GameOfLife";
	
	/**
	 * Constructor for the application.
	 * @param arguments
	 *        the arguments of the application.
	 * @throws IllegalArgumentException
	 *         if the arguments provided are invalid.
	 */
	public App (List<String> arguments) throws IllegalArgumentException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Launch the application.
	 */
	public void launch () {
		if (LG.isLoggable(Level.INFO))
			LG.info("Launching " + PROGRAM_NAME);
		
		if (LG.isLoggable(Level.INFO))
			LG.info("Exiting " + PROGRAM_NAME);
	}
	
	/**
	 * Entry point.
	 * @param args
	 *        the argument from the command line.
	 */
	public static void main (String[] args) {
		new App(Arrays.asList(args)).launch();
	}
	
}