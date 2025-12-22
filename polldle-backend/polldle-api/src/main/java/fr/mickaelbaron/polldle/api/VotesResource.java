package fr.mickaelbaron.polldle.api;

import static fr.mickaelbaron.polldle.api.ApiPaths.SSE;

import fr.mickaelbaron.polldle.model.PolldleResult;
import fr.mickaelbaron.polldle.model.PolldleVote;
import jakarta.ws.rs.CookieParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Cookie;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.sse.SseEventSink;

public interface VotesResource {

	/**
	 * Give a response.
	 * 
	 * @param response
	 */
	@POST
	Response createVote(@CookieParam(ApiCookies.POLLDLE_VOTE) Cookie cookie, PolldleVote response);
	
	/**
	 * @return
	 */
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	PolldleResult getVotes();
	
	/**
	 * @param sse
	 * @param eventSink
	 */
	@GET
	@Path(SSE)
	@Produces(MediaType.SERVER_SENT_EVENTS)
	void getVotes(@Context SseEventSink eventSink);
}
