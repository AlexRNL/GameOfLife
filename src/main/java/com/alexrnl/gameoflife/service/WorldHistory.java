package com.alexrnl.gameoflife.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.DatatypeConverter;

import com.alexrnl.commons.error.ExceptionUtils;
import com.alexrnl.commons.error.TopLevelError;
import com.alexrnl.gameoflife.world.World;

/**
 * Service class that maintain an history of the world.<br />
 * To improve performances, only a hash of the world is internally stored.
 * @author barfety_a
 */
public class WorldHistory {
	/** Logger */
	private static final Logger	LG					= Logger.getLogger(WorldHistory.class.getName());
	
	/** Number which indicate that the world has not been previously generated */
	public static final int		NO_PREVIOUS_OCCURENCE	= -1;
	
	/** The algorithm to use for building the hashes. */
	private final MessageDigest	hashAlgorithm;
	/** The history of the world */
	private final List<String>	history;
	
	/**
	 * Constructor #1.<br />
	 * @param hashAlgorithm
	 *        the algorithm for the hash.
	 */
	public WorldHistory (final MessageDigest hashAlgorithm) {
		super();
		this.hashAlgorithm = hashAlgorithm;
		this.history = new ArrayList<>();
	}
	
	/**
	 * Add a new world to the history.
	 * @param world
	 *        the world to add.
	 * @return the generation which had the same world, {@link #NO_PREVIOUS_OCCURENCE} if this
	 *         generation is unique.
	 */
	public int addHistory (final World world) {
		final String hash = computeHash(world);
		
		if (LG.isLoggable(Level.INFO)) {
			LG.info("Hash for world is: " + hash);
		}
		
		if (history.contains(hash)) {
			return history.indexOf(hash) + 1;
		}
		history.add(hash);
		return NO_PREVIOUS_OCCURENCE;
	}
	
	/**
	 * Computes the hash of the world.
	 * @param world
	 *        the world to use.
	 * @return the hash, as a string.
	 */
	private String computeHash (final World world) {
		hashAlgorithm.reset();
		
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try (final ObjectOutputStream out = new ObjectOutputStream(byteArrayOutputStream)) {
			out.writeObject(world);
			hashAlgorithm.update(byteArrayOutputStream.toByteArray());
		} catch (final IOException e) {
			// This should not have happened, the ByteArrayOutputStream does not throw this exception
			LG.severe("Could not compute hash of world: " + ExceptionUtils.display(e));
			throw new TopLevelError("Hash computation failed via serialization", e);
		}
		
		return DatatypeConverter.printHexBinary(hashAlgorithm.digest());
	}
}
