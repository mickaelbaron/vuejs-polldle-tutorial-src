package fr.mickaelbaron.polldle.dao.inmemory;

import java.util.Optional;

import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import fr.mickaelbaron.polldle.AbstractCDIUnitTest;
import fr.mickaelbaron.polldle.BeanFactory;
import fr.mickaelbaron.polldle.dao.IPolldleDAO;
import fr.mickaelbaron.polldle.entity.PolldleEntity;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
@RunWith(CdiRunner.class)
@AdditionalClasses(PolldleDAOInMemory.class)
public class PolldleDAOInMemoryTest extends AbstractCDIUnitTest {

	@Inject
	private IPolldleDAO currentPollDAO;

	@Test
	public void createPolldleTest() {
		// given
		PolldleEntity newPoll = BeanFactory.genericPollDBFactory();

		// when
		PolldleEntity createPoll = currentPollDAO.createPolldle(newPoll);

		// then
		Assert.assertNotNull(createPoll.getPathUrl());
		Assert.assertFalse(createPoll.getPathUrl().isEmpty());
		Assert.assertTrue(createPoll.getPolldleOptions().get(0).getId() != 0);
		Assert.assertTrue(createPoll.getPolldleOptions().get(1).getId() != 0);
	}

	@Test
	public void getPolldleByPathURLSuccessTest() {
		// given
		PolldleEntity newPoll = BeanFactory.genericPollDBFactory();
		final PolldleEntity createPoll = currentPollDAO.createPolldle(newPoll);
		final String pathUrl = createPoll.getPathUrl();

		// when
		final Optional<PolldleEntity> pollByPathURL = currentPollDAO.getPolldleByPathURL(pathUrl);

		// then
		Assert.assertTrue(pollByPathURL.isPresent());
	}

	@Test
	public void getPolldleByPathURLFailureTest() {
		// given
		PolldleEntity newPoll = BeanFactory.genericPollDBFactory();
		currentPollDAO.createPolldle(newPoll);

		// when
		final Optional<PolldleEntity> pollByPathURL = currentPollDAO.getPolldleByPathURL("blabla");

		// then
		Assert.assertFalse(pollByPathURL.isPresent());
	}
}
