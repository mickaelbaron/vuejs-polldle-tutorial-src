package fr.mickaelbaron.polldle.service;

import java.util.Optional;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import fr.mickaelbaron.polldle.AbstractCDIUnitTest;
import fr.mickaelbaron.polldle.BeanFactory;
import fr.mickaelbaron.polldle.api.PolldlesResource;
import fr.mickaelbaron.polldle.dao.IPolldleDAO;
import fr.mickaelbaron.polldle.entity.PolldleEntity;
import fr.mickaelbaron.polldle.model.Polldle;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
@RunWith(CdiRunner.class)
@AdditionalClasses(PolldlesResourceImpl.class)
public class PolldlesResourceImplTest extends AbstractCDIUnitTest {

	@Inject
	private PolldlesResource currentAdminResource;

	@Produces
	@Mock
	IPolldleDAO pollDAO;

	@Test
	public void getPolldleByPathURLTest() {
		// given
		final PolldleEntity genericPollDBFactory = BeanFactory.genericPollDBFactory();
		Mockito.when(pollDAO.getPolldleByPathURL("1")).thenReturn(Optional.of(genericPollDBFactory));

		// when
		final Polldle pollByPathURL = currentAdminResource.getPolldleByPathURL("1");

		// then
		Assert.assertNotNull(pollByPathURL);
		Assert.assertNull(pollByPathURL.getKey());
	}

	@Test
	public void getPolldleByPathURLWithoutParameterTest() {
		// given

		// when
		try {
			currentAdminResource.getPolldleByPathURL(null);
		} catch (WebApplicationException e) {
			// then
			Assert.assertEquals("Path URL is missing.", e.getMessage());
		}
	}

	@Test
	public void createPollTest() {
		// given
		Polldle newPoll = BeanFactory.genericPollFactory();
		final PolldleEntity genericPollDBFactory = BeanFactory.genericPollDBFactory();
		genericPollDBFactory.setPathUrl("1");
		Mockito.when(pollDAO.createPolldle(Mockito.any())).thenReturn(genericPollDBFactory);

		// when
		final Polldle createPoll = currentAdminResource.createPolldle(newPoll);

		// then
		Mockito.verify(pollDAO, Mockito.times(1)).createPolldle(Mockito.any());
		Assert.assertTrue(createPoll.getPathUrl() != null);
		Assert.assertEquals(createPoll.getPathUrl(), "1");
	}

	@Test
	public void createPollWithDAOProblemTest() {
		// given
		Polldle newPoll = BeanFactory.genericPollFactory();
		Mockito.when(pollDAO.createPolldle(Mockito.any())).thenReturn(null);

		// when
		try {
			currentAdminResource.createPolldle(newPoll);
			// then
			Assert.fail("Must throw a WebApplicationException");
		} catch (WebApplicationException e) {
			Assert.assertEquals("HTTP 500 Internal Server Error", e.getMessage());
		}

		// then
		Mockito.verify(pollDAO, Mockito.times(1)).createPolldle(Mockito.any());
	}

	@Test
	public void createPollWithoutQuestionContentTest() {
		// given
		Polldle newPoll = BeanFactory.genericPollFactory();
		newPoll.setQuestion(null);

		// when
		try {
			currentAdminResource.createPolldle(newPoll);
			// then
			Assert.fail("Must throw a WebApplicationException");
		} catch (WebApplicationException e) {
			Assert.assertEquals("Question is missing.", e.getMessage());
		}
	}

	@Test
	public void createPollWithoutConfigurationContentTest() {
		// given
		Polldle newPoll = BeanFactory.genericPollFactory();
		final PolldleEntity genericPollDBFactory = BeanFactory.genericPollDBFactory();
		genericPollDBFactory.setPathUrl("1");
		Mockito.when(pollDAO.createPolldle(Mockito.any())).thenReturn(genericPollDBFactory);

		// when
		final Polldle createPoll = currentAdminResource.createPolldle(newPoll);

		// then
		Mockito.verify(pollDAO, Mockito.times(1)).createPolldle(Mockito.any());
		Assert.assertTrue(createPoll.getPathUrl() != null);
		Assert.assertEquals(createPoll.getPathUrl(), "1");
	}

	@Test
	public void createPollWithoutPollOptionsContentTest() {
		// given
		Polldle newPoll = BeanFactory.genericPollFactory();
		newPoll.setPollOptions(null);

		// when
		try {
			currentAdminResource.createPolldle(newPoll);
			// then
			Assert.fail("Must throw a WebApplicationException");
		} catch (WebApplicationException e) {
			Assert.assertEquals("Polldle option can not be null and must contain at least two elements.",
					e.getMessage());
		}
	}
}
