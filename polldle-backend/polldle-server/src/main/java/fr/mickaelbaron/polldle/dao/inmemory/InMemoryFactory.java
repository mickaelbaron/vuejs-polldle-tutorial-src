package fr.mickaelbaron.polldle.dao.inmemory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

import fr.mickaelbaron.polldle.entity.PolldleEntity;
import fr.mickaelbaron.polldle.entity.PolldleVoteEntity;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
@Singleton
public class InMemoryFactory {

	private List<PolldleEntity> polls;

	private Long pollCounter;

	private Map<String, List<PolldleVoteEntity>> result;

	public InMemoryFactory() {
		polls = new ArrayList<PolldleEntity>();
		result = new HashMap<String, List<PolldleVoteEntity>>();

		pollCounter = 0L;
	}

	public List<PolldleEntity> getPoll() {
		return this.polls;
	}

	public Long getPollCounter() {
		return pollCounter;
	}

	public void incrementPollCounter() {
		pollCounter++;
	}

	public List<PolldleVoteEntity> getPollVoteDBByPathUrl(String key) {
		return result.get(key);
	}

	public void addPollVoteDB(String key, PolldleVoteEntity value) {
		if (this.result.get(key) == null) {
			List<PolldleVoteEntity> newPollVoteDB = new ArrayList<PolldleVoteEntity>();
			this.result.put(key, newPollVoteDB);
		}
		this.result.get(key).add(value);
	}
}
