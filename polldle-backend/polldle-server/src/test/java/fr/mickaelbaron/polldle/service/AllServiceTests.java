package fr.mickaelbaron.polldle.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
@RunWith(Suite.class)
@SuiteClasses(value = { PolldlesResourceImplTest.class, VotesResourceImplTest.class })
public class AllServiceTests {

}