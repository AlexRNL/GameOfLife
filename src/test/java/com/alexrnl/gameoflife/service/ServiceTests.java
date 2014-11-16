package com.alexrnl.gameoflife.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite for the service package.
 * @author barfety_a
 */
@RunWith(Suite.class)
@SuiteClasses({ GameOfLifeControllerTest.class, WorldGrowerTest.class, WorldHistoryTest.class })
public class ServiceTests {
	
}
