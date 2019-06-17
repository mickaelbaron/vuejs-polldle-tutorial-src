package fr.mickaelbaron.polldle.dao;

import java.util.Map;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
public interface IPolldleResultDAO {

	/**
	 * @param pathURL
	 * @return
	 */
	Map<Byte, Integer> getPolldleResultByPathURL(String pathURL);
}
