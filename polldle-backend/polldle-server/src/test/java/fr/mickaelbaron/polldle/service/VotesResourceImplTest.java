package fr.mickaelbaron.polldle.service;

import java.util.Arrays;
import java.util.Optional;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Response;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import fr.mickaelbaron.polldle.AbstractCDIUnitTest;
import fr.mickaelbaron.polldle.BeanFactory;
import fr.mickaelbaron.polldle.api.ApiCookies;
import fr.mickaelbaron.polldle.dao.IPolldleDAO;
import fr.mickaelbaron.polldle.dao.IPolldleResultDAO;
import fr.mickaelbaron.polldle.dao.IPolldleVoteDAO;
import fr.mickaelbaron.polldle.entity.PolldleEntity;
import fr.mickaelbaron.polldle.model.PolldleResult;
import fr.mickaelbaron.polldle.model.PolldleVote;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
@RunWith(CdiRunner.class)
@AdditionalClasses(VotesResourceImpl.class)
public class VotesResourceImplTest extends AbstractCDIUnitTest {

	@Inject
	private VotesResourceImpl currentVotesResource;

	@Produces
	@Mock
	IPolldleDAO pollDAO;

	@Produces
	@Mock
	IPolldleVoteDAO voteDAO;

	@Produces
	@Mock
	IPolldleResultDAO resultDAO;

	@Test
	public void createVoteTest() {
		// given
		PolldleVote newPollVote = new PolldleVote();
		newPollVote.setPathUrl("1");
		newPollVote.setPolldleOptionResponses(Arrays.asList((byte) 1));

		PolldleEntity genericPollDBFactory = BeanFactory.genericPollDBWithIDFactory();
		Mockito.when(pollDAO.getPolldleByPathURL("1")).thenReturn(Optional.of(genericPollDBFactory));

		Mockito.doNothing().when(voteDAO).createVote(Mockito.eq("1"), Mockito.any());

		// when
		Response createVote = currentVotesResource.createVote(null, newPollVote);

		// then
		Mockito.verify(voteDAO, Mockito.times(1)).createVote(Mockito.eq("1"), Mockito.any());
		Mockito.verify(pollDAO, Mockito.times(1)).getPolldleByPathURL(Mockito.eq("1"));
		Assert.assertNotNull(createVote);
		Assert.assertEquals("1", createVote.getCookies().get(ApiCookies.POLLDLE_VOTE).getValue());
	}

	@Test
	public void createVoteSillVotedTest() {
		// given
		PolldleVote newPollVote = new PolldleVote();
		newPollVote.setPathUrl("1");
		newPollVote.setPolldleOptionResponses(Arrays.asList((byte) 1));

		Cookie newCookie = new Cookie(ApiCookies.POLLDLE_VOTE, "1");

		PolldleEntity genericPollDBFactory = BeanFactory.genericPollDBWithIDFactory();
		Mockito.when(pollDAO.getPolldleByPathURL("1")).thenReturn(Optional.of(genericPollDBFactory));

		Mockito.doNothing().when(voteDAO).createVote(Mockito.eq("1"), Mockito.any());

		// when
		try {
			currentVotesResource.createVote(newCookie, newPollVote);

			Assert.fail("Must throw a WebApplicationException.");
		} catch (WebApplicationException e) {
			// then
			Assert.assertEquals("Still voted.", e.getMessage());
			Assert.assertEquals(204, e.getResponse().getStatus());
		}
	}

	@Test
	public void createVoteSillVotedWithOtherVotesTest() {
		// given
		PolldleVote newPollVote = new PolldleVote();
		newPollVote.setPathUrl("1");
		newPollVote.setPolldleOptionResponses(Arrays.asList((byte) 1));

		Cookie newCookie = new Cookie(ApiCookies.POLLDLE_VOTE, "1/2/3/4/5");

		PolldleEntity genericPollDBFactory = BeanFactory.genericPollDBWithIDFactory();
		Mockito.when(pollDAO.getPolldleByPathURL("1")).thenReturn(Optional.of(genericPollDBFactory));

		Mockito.doNothing().when(voteDAO).createVote(Mockito.eq("1"), Mockito.any());

		// when
		try {
			currentVotesResource.createVote(newCookie, newPollVote);

			Assert.fail("Must throw a WebApplicationException.");
		} catch (WebApplicationException e) {
			// then
			Assert.assertEquals("Still voted.", e.getMessage());
			Assert.assertEquals(204, e.getResponse().getStatus());
		}
	}

	@Test
	public void createVoteWithOtherVotesTest() {
		// given
		PolldleVote newPollVote = new PolldleVote();
		newPollVote.setPathUrl("1");
		newPollVote.setPolldleOptionResponses(Arrays.asList((byte) 1));

		Cookie newCookie = new Cookie(ApiCookies.POLLDLE_VOTE, "2/3/4/5");

		PolldleEntity genericPollDBFactory = BeanFactory.genericPollDBWithIDFactory();
		Mockito.when(pollDAO.getPolldleByPathURL("1")).thenReturn(Optional.of(genericPollDBFactory));

		Mockito.doNothing().when(voteDAO).createVote(Mockito.eq("1"), Mockito.any());

		// when
		Response createVote = currentVotesResource.createVote(newCookie, newPollVote);
		Mockito.verify(voteDAO, Mockito.times(1)).createVote(Mockito.eq("1"), Mockito.any());
		Mockito.verify(pollDAO, Mockito.times(1)).getPolldleByPathURL(Mockito.eq("1"));
		Assert.assertNotNull(createVote);
		Assert.assertEquals("2/3/4/5/1", createVote.getCookies().get(ApiCookies.POLLDLE_VOTE).getValue());
	}

	@Test
	public void createVoteWithBadPolldleOptionIdTest() {
		// given
		final PolldleEntity genericPollDBFactory = BeanFactory.genericPollDBWithIDFactory();
		Mockito.when(pollDAO.getPolldleByPathURL("1")).thenReturn(Optional.of(genericPollDBFactory));

		PolldleVote newPollVote = new PolldleVote();
		newPollVote.setPathUrl("1");
		newPollVote.setPolldleOptionResponses(Arrays.asList((byte) 3));

		// when
		try {
			currentVotesResource.createVote(null, newPollVote);

			Assert.fail("Must throw a WebApplicationException.");
		} catch (WebApplicationException e) {
			// then
			Assert.assertEquals("No Polldle option with this path URL.", e.getMessage());
			Assert.assertEquals(400, e.getResponse().getStatus());
		}
	}

	@Test
	public void createVoteWithBadPolldleIdTest() {
		// given
		Mockito.when(pollDAO.getPolldleByPathURL("1")).thenReturn(Optional.empty());

		PolldleVote newPollVote = new PolldleVote();
		newPollVote.setPathUrl("1");
		newPollVote.setPolldleOptionResponses(Arrays.asList((byte) 3));

		// when
		try {
			currentVotesResource.createVote(null, newPollVote);

			Assert.fail("Must throw a WebApplicationException.");
		} catch (WebApplicationException e) {
			// then
			Assert.assertEquals("No Polldle with this path URL.", e.getMessage());
			Assert.assertEquals(400, e.getResponse().getStatus());
		}
	}

	@Test
	public void createVoteWithoutParameterTest() {
		// given

		// when
		try {
			currentVotesResource.createVote(null, null);

			Assert.fail("Must throw a WebApplicationException.");
		} catch (WebApplicationException e) {
			// then
			Assert.assertEquals("Parameter is missing.", e.getMessage());
			Assert.assertEquals(400, e.getResponse().getStatus());
		}
	}

	@Test
	public void getVotesTest() {
		// given
		final PolldleEntity genericPollDBFactory = BeanFactory.genericPollDBWithIDFactory();
		Mockito.when(pollDAO.getPolldleByPathURL("1")).thenReturn(Optional.of(genericPollDBFactory));

		Mockito.when(resultDAO.getPolldleResultByPathURL("1"))
				.thenReturn(BeanFactory.genericPollOptionResultWithTwoVotesDBFactory());
		currentVotesResource.setPathURL("1");

		// then
		final PolldleResult pollResult = currentVotesResource.getVotes();

		// when
		Assert.assertNotNull(pollResult);
		Mockito.verify(resultDAO, Mockito.times(1)).getPolldleResultByPathURL(Mockito.eq("1"));
		Mockito.verify(pollDAO, Mockito.times(1)).getPolldleByPathURL(Mockito.eq("1"));
		Assert.assertEquals((Integer) 5, pollResult.getResults().get(0).getCounter());
		Assert.assertEquals((Integer) 10, pollResult.getResults().get(1).getCounter());
	}

	@Test
	public void getVotesWithOneTest() {
		// given
		final PolldleEntity genericPollDBFactory = BeanFactory.genericPollDBWithIDFactory();
		Mockito.when(pollDAO.getPolldleByPathURL("1")).thenReturn(Optional.of(genericPollDBFactory));

		Mockito.when(resultDAO.getPolldleResultByPathURL("1"))
				.thenReturn(BeanFactory.genericPollOptionResultWithOneVoteDBFactory());
		currentVotesResource.setPathURL("1");

		// then
		final PolldleResult pollResult = currentVotesResource.getVotes();

		// when
		Assert.assertNotNull(pollResult);
		Mockito.verify(resultDAO, Mockito.times(1)).getPolldleResultByPathURL(Mockito.eq("1"));
		Mockito.verify(pollDAO, Mockito.times(1)).getPolldleByPathURL(Mockito.eq("1"));
		Assert.assertEquals((Integer) 5, pollResult.getResults().get(0).getCounter());
		Assert.assertEquals((Integer) 0, pollResult.getResults().get(1).getCounter());
	}

	@Test
	public void getVotesWithZeroTest() {
		// given
		final PolldleEntity genericPollDBFactory = BeanFactory.genericPollDBWithIDFactory();
		Mockito.when(pollDAO.getPolldleByPathURL("1")).thenReturn(Optional.of(genericPollDBFactory));

		Mockito.when(resultDAO.getPolldleResultByPathURL("1"))
				.thenReturn(BeanFactory.genericPollOptionResultWithZeroVoteDBFactory());
		currentVotesResource.setPathURL("1");

		// then
		final PolldleResult pollResult = currentVotesResource.getVotes();

		// when
		Assert.assertNotNull(pollResult);
		Mockito.verify(resultDAO, Mockito.times(1)).getPolldleResultByPathURL(Mockito.eq("1"));
		Mockito.verify(pollDAO, Mockito.times(1)).getPolldleByPathURL(Mockito.eq("1"));
		Assert.assertEquals((Integer) 0, pollResult.getResults().get(0).getCounter());
		Assert.assertEquals((Integer) 0, pollResult.getResults().get(1).getCounter());
	}
}
