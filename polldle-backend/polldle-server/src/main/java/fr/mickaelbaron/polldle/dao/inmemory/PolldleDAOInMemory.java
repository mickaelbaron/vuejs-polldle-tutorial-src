package fr.mickaelbaron.polldle.dao.inmemory;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import fr.mickaelbaron.polldle.dao.IPolldleDAO;
import fr.mickaelbaron.polldle.entity.PolldleEntity;
import fr.mickaelbaron.polldle.entity.PolldleOptionEntity;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
public class PolldleDAOInMemory implements IPolldleDAO {

	@Inject
	InMemoryFactory refSession;

	public PolldleEntity createPolldle(PolldleEntity newPoll) {
		byte pollOptionCounter = 1;

		refSession.incrementPollCounter();
		newPoll.setPathUrl(Long.toString(refSession.getPollCounter()));
		this.refSession.getPoll().add(newPoll);

		final List<PolldleOptionEntity> pollOptions = newPoll.getPolldleOptions();
		for (PolldleOptionEntity pollOptionDB : pollOptions) {
			pollOptionDB.setId(pollOptionCounter);
			pollOptionCounter++;
		}

		return newPoll;
	}

	public Optional<PolldleEntity> getPolldleByPathURL(final String pathURL) {
		return this.refSession.getPoll().stream().filter(t -> (t.getPathUrl().equals(pathURL))).findFirst();
	}
}
