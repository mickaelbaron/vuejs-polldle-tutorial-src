package fr.mickaelbaron.polldle.api;

import static fr.mickaelbaron.polldle.api.ApiParameters.PATH_URL;
import static fr.mickaelbaron.polldle.api.ApiPaths.POLLDLES;
import static fr.mickaelbaron.polldle.api.ApiPaths.VOTES;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import fr.mickaelbaron.polldle.model.Polldle;

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
	 * @param customPathURL
	 * @return
	 */
	@GET
	Polldle getPolldleByPathURL(@QueryParam(PATH_URL) String customPathURL);

	/**
	 * @param pathUrl
	 * @return
	 */
	@Path("{" + PATH_URL + "}" + VOTES)
	VotesResource getVotesResource(@PathParam(PATH_URL) String pathUrl);
}
