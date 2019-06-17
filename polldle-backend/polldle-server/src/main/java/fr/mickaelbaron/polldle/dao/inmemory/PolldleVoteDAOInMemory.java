package fr.mickaelbaron.polldle.dao.inmemory;

import javax.inject.Inject;

import fr.mickaelbaron.polldle.dao.IPolldleVoteDAO;
import fr.mickaelbaron.polldle.entity.PolldleVoteEntity;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
public class PolldleVoteDAOInMemory implements IPolldleVoteDAO {

	@Inject
	InMemoryFactory refSession;

	@Override
	public void createVote(String pathUrl, PolldleVoteEntity pollVoteDB) {
		refSession.addPollVoteDB(pathUrl, pollVoteDB);
	}
}
