package fr.mickaelbaron.polldle.dao.inmemory;

import java.util.Map;

import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import fr.mickaelbaron.polldle.AbstractCDIUnitTest;
import fr.mickaelbaron.polldle.BeanFactory;
import fr.mickaelbaron.polldle.dao.IPolldleResultDAO;
import fr.mickaelbaron.polldle.dao.IPolldleVoteDAO;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
@RunWith(CdiRunner.class)
@AdditionalClasses({ PolldleResultDAOInMemory.class, PolldleVoteDAOInMemory.class })
public class PolldleResultDAOInMemoryTest extends AbstractCDIUnitTest {

	@Inject
	private IPolldleResultDAO currentPollResult;

	@Inject
	private IPolldleVoteDAO currentPollVoteDAO;

	@Test
	public void getPolldleResultByPathURLTest() {
		// given
		currentPollVoteDAO.createVote("1", BeanFactory.genericPollVoteDBFactory((byte) 1));
		currentPollVoteDAO.createVote("1", BeanFactory.genericPollVoteDBFactory((byte) 2));
		currentPollVoteDAO.createVote("1", BeanFactory.genericPollVoteDBFactory((byte) 1));
		currentPollVoteDAO.createVote("1", BeanFactory.genericPollVoteDBFactory((byte) 1));

		// when
		final Map<Byte, Integer> pollResultByPathURL = currentPollResult.getPolldleResultByPathURL("1");

		// then
		Assert.assertNotNull(pollResultByPathURL);
		Assert.assertEquals(3, (int) pollResultByPathURL.get((byte) 1));
		Assert.assertEquals(1, (int) pollResultByPathURL.get((byte) 2));
		Assert.assertEquals(2, pollResultByPathURL.size());
	}
}
