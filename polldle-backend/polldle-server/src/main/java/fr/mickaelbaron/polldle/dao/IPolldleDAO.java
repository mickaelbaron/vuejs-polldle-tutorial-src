package fr.mickaelbaron.polldle.dao;

import java.util.Optional;

import fr.mickaelbaron.polldle.entity.PolldleEntity;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
public interface IPolldleDAO {

	/**
	 * @param newPoll
	 * @return
	 */
	PolldleEntity createPolldle(PolldleEntity newPoll);

	/**
	 * @param pathURL
	 * @return
	 */
	Optional<PolldleEntity> getPolldleByPathURL(String pathURL);
}
