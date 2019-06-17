package fr.mickaelbaron.polldle.dao;

import fr.mickaelbaron.polldle.entity.PolldleVoteEntity;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
public interface IPolldleVoteDAO {

	void createVote(String pathUrl, PolldleVoteEntity pollVoteDB);
}
