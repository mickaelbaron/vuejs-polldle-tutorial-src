package fr.mickaelbaron.polldle.dao.inmemory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import fr.mickaelbaron.polldle.dao.IPolldleResultDAO;
import fr.mickaelbaron.polldle.entity.PolldleVoteEntity;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
public class PolldleResultDAOInMemory implements IPolldleResultDAO {

	@Inject
	private InMemoryFactory refSession;

	@Override
	public Map<Byte, Integer> getPolldleResultByPathURL(String pathURL) {
		Map<Byte, Integer> newResult = new HashMap<Byte, Integer>();

		final List<PolldleVoteEntity> pollVoteDBByPathUrl = refSession.getPollVoteDBByPathUrl(pathURL);

		if (pollVoteDBByPathUrl != null) {
			for (PolldleVoteEntity pollVoteDB : pollVoteDBByPathUrl) {
				final List<Byte> pollOptions = pollVoteDB.getPolldleOptions();
				for (Byte currentPollOption : pollOptions) {
					Integer integer = newResult.get(currentPollOption);
					if (integer == null) {
						newResult.put(currentPollOption, 1);
					} else {
						integer = integer + 1;
						newResult.put(currentPollOption, integer);
					}
				}
			}
		} else {
			return newResult;
		}

		return newResult;
	}
}
