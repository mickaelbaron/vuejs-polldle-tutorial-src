package fr.mickaelbaron.polldle.dao.inmemory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import fr.mickaelbaron.polldle.BeanFactory;
import fr.mickaelbaron.polldle.entity.PolldleEntity;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
@ExtendWith(MockitoExtension.class)
public class PollVoteDAOInMemoryTest {

	@Mock
	InMemoryFactory refSession;

	@InjectMocks
	PolldleDAOInMemory dao;

	private Long counter;

	@BeforeEach
	void setUp() {
		counter = ThreadLocalRandom.current().nextLong();
	}

	@Test
	void testCreatePolldle() {
		// given
		PolldleEntity poll = BeanFactory.genericPollDBFactory();
		List<PolldleEntity> pollList = new ArrayList<>();

		when(refSession.getPollCounter()).thenReturn(counter);
		when(refSession.getPoll()).thenReturn(pollList);

		// when
		PolldleEntity result = dao.createPolldle(poll);

		// then
		verify(refSession).incrementPollCounter();
		verify(refSession).getPollCounter();
		verify(refSession).getPoll();

		assertEquals(Long.toString(counter), result.getPathUrl());
		assertEquals(2, result.getPolldleOptions().size());
		assertEquals((byte) 1, result.getPolldleOptions().get(0).getId());
		assertEquals((byte) 2, result.getPolldleOptions().get(1).getId());
		assertTrue(pollList.contains(result));
	}

	@Test
	public void getPolldleByPathURLSuccessTest() {
		// given
		PolldleEntity newPoll = BeanFactory.genericPollDBFactory();
		newPoll.setPathUrl(Long.toString(counter));

		when(refSession.getPoll()).thenReturn(List.of(newPoll));

		// when
		Optional<PolldleEntity> result = dao.getPolldleByPathURL(Long.toString(counter));

		// then
		verify(refSession).getPoll();
		assertTrue(result.isPresent());
		assertEquals(Long.toString(counter), result.get().getPathUrl());
	}

	@Test
	public void getPolldleByPathURLFailureTest() {
		// given
		PolldleEntity newPoll = BeanFactory.genericPollDBFactory();
		newPoll.setPathUrl(Long.toString(counter));

		when(refSession.getPoll()).thenReturn(List.of(newPoll));

		// when
		Optional<PolldleEntity> result = dao.getPolldleByPathURL("blabla");

		// then
		verify(refSession).getPoll();
		assertFalse(result.isPresent());
	}
}
