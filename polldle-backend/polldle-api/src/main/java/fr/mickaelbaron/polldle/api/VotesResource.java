package fr.mickaelbaron.polldle.api;

import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.sse.SseEventSink;

import fr.mickaelbaron.polldle.model.PolldleResult;
import fr.mickaelbaron.polldle.model.PolldleVote;

import static fr.mickaelbaron.polldle.api.ApiPaths.SSE;

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
