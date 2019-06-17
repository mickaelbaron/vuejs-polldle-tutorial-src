package fr.mickaelbaron.polldle.dao.inmemory;

import java.util.List;

import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import fr.mickaelbaron.polldle.AbstractCDIUnitTest;
import fr.mickaelbaron.polldle.BeanFactory;
import fr.mickaelbaron.polldle.dao.IPolldleVoteDAO;
import fr.mickaelbaron.polldle.entity.PolldleVoteEntity;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
@RunWith(CdiRunner.class)
@AdditionalClasses(PolldleVoteDAOInMemory.class)
public class PollVoteDAOInMemoryTest extends AbstractCDIUnitTest {

	@Inject
	private IPolldleVoteDAO currentPollVoteDAO;

	@Inject
	InMemoryFactory refSession;

	@Test
	public void createVoteTest() {
		// given
		// PollVoteDBFactory created after.

		// when
		currentPollVoteDAO.createVote("1", BeanFactory.genericPollVoteDBFactory((byte) 1));
		currentPollVoteDAO.createVote("1", BeanFactory.genericPollVoteDBFactory((byte) 2));
		currentPollVoteDAO.createVote("1", BeanFactory.genericPollVoteDBFactory((byte) 1));

		// then
		final List<PolldleVoteEntity> pollVoteDBByPathUrl = refSession.getPollVoteDBByPathUrl("1");
		Assert.assertEquals(3, pollVoteDBByPathUrl.size());
	}
}
