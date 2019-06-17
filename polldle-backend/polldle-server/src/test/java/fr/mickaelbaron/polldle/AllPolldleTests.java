package fr.mickaelbaron.polldle;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import fr.mickaelbaron.polldle.dao.AllDAOTests;
import fr.mickaelbaron.polldle.service.AllServiceTests;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
@RunWith(Suite.class)
@SuiteClasses(value = { AllDAOTests.class, AllServiceTests.class })
public class AllPolldleTests {

}