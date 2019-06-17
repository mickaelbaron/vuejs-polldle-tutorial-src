package fr.mickaelbaron.polldle.dao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import fr.mickaelbaron.polldle.dao.inmemory.PolldleDAOInMemoryTest;
import fr.mickaelbaron.polldle.dao.inmemory.PolldleResultDAOInMemoryTest;
import fr.mickaelbaron.polldle.dao.inmemory.PollVoteDAOInMemoryTest;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
@RunWith(Suite.class)
@SuiteClasses(value = { PolldleDAOInMemoryTest.class, PollVoteDAOInMemoryTest.class,
		PolldleResultDAOInMemoryTest.class })
public class AllDAOTests {

}