package com.alexrnl.gameoflife;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.alexrnl.gameoflife.world.WorldTests;

/**
 * Test suite for the game of life application.
 * @author barfety_a
 */
@RunWith(Suite.class)
@SuiteClasses({ WorldTests.class })
public class AllTests {
	
}
