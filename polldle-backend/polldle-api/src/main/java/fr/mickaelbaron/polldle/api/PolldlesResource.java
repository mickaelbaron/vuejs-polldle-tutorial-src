package fr.mickaelbaron.polldle.api;

import static fr.mickaelbaron.polldle.api.ApiParameters.PATH_URL;
import static fr.mickaelbaron.polldle.api.ApiPaths.POLLDLES;
import static fr.mickaelbaron.polldle.api.ApiPaths.VOTES;

import fr.mickaelbaron.polldle.model.Polldle;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
@Path(POLLDLES)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface PolldlesResource {

	/**
	 * Create a new Poll.
	 * 
	 * @param newPoll
	 */
	@POST
	Polldle createPolldle(Polldle newPoll);

	/**
	 * Retrieve a poll to prepare to vote.
	 * 
	 * @param pathURL
	 * @return
	 */
	@GET
	@Path("{" + PATH_URL + "}")
	Polldle getPolldleByPathURL(@PathParam(PATH_URL) String pathURL);

	/**
	 * @param pathUrl
	 * @return
	 */
	@Path("{" + PATH_URL + "}" + VOTES)
	VotesResource getVotesResource(@PathParam(PATH_URL) String pathUrl);
}
