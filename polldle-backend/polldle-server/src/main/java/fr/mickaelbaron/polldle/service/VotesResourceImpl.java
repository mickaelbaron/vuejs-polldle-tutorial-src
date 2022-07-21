package fr.mickaelbaron.polldle.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseBroadcaster;
import javax.ws.rs.sse.SseEventSink;

import fr.mickaelbaron.polldle.api.ApiCookies;
import fr.mickaelbaron.polldle.api.NotYetImplementedException;
import fr.mickaelbaron.polldle.api.VotesResource;
import fr.mickaelbaron.polldle.dao.IPolldleDAO;
import fr.mickaelbaron.polldle.dao.IPolldleResultDAO;
import fr.mickaelbaron.polldle.dao.IPolldleVoteDAO;
import fr.mickaelbaron.polldle.entity.PolldleEntity;
import fr.mickaelbaron.polldle.entity.PolldleOptionEntity;
import fr.mickaelbaron.polldle.entity.PolldleVoteEntity;
import fr.mickaelbaron.polldle.model.PolldleOptionResult;
import fr.mickaelbaron.polldle.model.PolldleResult;
import fr.mickaelbaron.polldle.model.PolldleVote;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
public class VotesResourceImpl implements VotesResource {

	@Inject
	private IPolldleDAO pollDAO;

	@Inject
	private IPolldleVoteDAO voteDAO;

	@Inject
	private IPolldleResultDAO resultDAO;

	@Context
	private Sse sse;

	private String pathURL;

	public static Map<String, SseBroadcaster> broadcasters = new HashMap<>();

	private static final String SSE_NAME_EVENT = "update-polldleresult";

	protected void setPathURL(String pPathUrl) {
		this.pathURL = pPathUrl;
	}

	private PolldleResult getInternalPolldleResult() {
		// Get Poll from pathURL value.
		final Optional<PolldleEntity> pollByPathURL = pollDAO.getPolldleByPathURL(pathURL);

		if (!pollByPathURL.isPresent()) {
			throw new WebApplicationException("No Polldle with this path URL.", Status.BAD_REQUEST);
		}

		final Map<Byte, Integer> pollResultByPathURL = resultDAO.getPolldleResultByPathURL(pathURL);
		if (pollResultByPathURL == null) {
			throw new NotYetImplementedException();
		}

		// Complete by missing PolldleOptionResultDB => no vote. Transform also
		// PollOptionResultDB into PollOptionResult.
		List<PolldleOptionResult> pollOptionResults = new ArrayList<PolldleOptionResult>();
		for (PolldleOptionEntity current : pollByPathURL.get().getPolldleOptions()) {
			PolldleOptionResult newPolldleOptionResult = new PolldleOptionResult();

			if (!pollResultByPathURL.containsKey(current.getId())) {
				newPolldleOptionResult.setCounter(0);
				newPolldleOptionResult.setPolldleOptionId(current.getId());
				newPolldleOptionResult.setName(current.getName());
			} else {
				final Integer polldleOptionResultDB = pollResultByPathURL.get(current.getId());

				newPolldleOptionResult.setPolldleOptionId(current.getId());
				newPolldleOptionResult.setName(current.getName());
				newPolldleOptionResult.setCounter(polldleOptionResultDB);

			}
			pollOptionResults.add(newPolldleOptionResult);
		}

		// Create PollResult and return it.
		PolldleResult newPollResult = new PolldleResult();
		newPollResult.setResults(pollOptionResults);
		newPollResult.setPathUrl(pollByPathURL.get().getPathUrl());
		newPollResult.setQuestion(pollByPathURL.get().getQuestion());

		return newPollResult;
	}

	@Override
	public Response createVote(Cookie cookie, PolldleVote response) {
		if (response == null) {
			throw new WebApplicationException("Parameter is missing.", Status.BAD_REQUEST);
		}

		// Get Poll from pathURL value.
		final Optional<PolldleEntity> pollByPathURL = pollDAO.getPolldleByPathURL(pathURL);

		if (!pollByPathURL.isPresent()) {
			throw new WebApplicationException("No Polldle with this path URL.", Status.BAD_REQUEST);
		}

		// Transform to PollVoteDB.
		PolldleVoteEntity newPollVoteDB = new PolldleVoteEntity();
		newPollVoteDB.setPolldleOptions(response.getPolldleOptionResponses());

		// Check pollVoteOptions are included into the pollOptions of the Poll.
		response.getPolldleOptionResponses().stream().forEach(current -> {
			if (pollByPathURL.get().getPolldleOptions().stream().filter(item -> item.getId() == current).count() == 0) {
				throw new WebApplicationException("No Polldle option with this path URL.", Status.BAD_REQUEST);
			}
		});

		NewCookie newCookie = null;
		if (cookie != null && cookie.getValue() != null) {
			// Split by 'ApiCookies.POLLDLE_VOTE_SEPARATOR' and check if there exists the
			// current path.
			if (Arrays.stream(cookie.getValue().split(String.valueOf(ApiCookies.POLLDLE_VOTE_SEPARATOR)))
					.filter(item -> item.equals(pathURL)).count() != 0) {
				// Already Voted.
				throw new WebApplicationException("Still voted.", Status.NO_CONTENT);
			} else {
				newCookie = new NewCookie(ApiCookies.POLLDLE_VOTE,
						cookie.getValue() + ApiCookies.POLLDLE_VOTE_SEPARATOR + pathURL);
			}
		} else {
			newCookie = new NewCookie(ApiCookies.POLLDLE_VOTE, pathURL);
		}

		// Store the vote.
		voteDAO.createVote(pathURL, newPollVoteDB);

		// Notify.
		SseBroadcaster sseBroadcaster = VotesResourceImpl.broadcasters.get(pathURL);
		if (sseBroadcaster != null) {
			PolldleResult internalPolldleResult = this.getInternalPolldleResult();
			OutboundSseEvent build = sse.newEventBuilder().name(SSE_NAME_EVENT)
					.mediaType(MediaType.APPLICATION_JSON_TYPE).data(PolldleResult.class, internalPolldleResult)
					.build();
			sseBroadcaster.broadcast(build);
		}

		return Response.ok().cookie(newCookie).build();
	}

	@Override
	public PolldleResult getVotes() {
		return this.getInternalPolldleResult();
	}

	@Override
	public void getVotes(SseEventSink eventSink) {
		PolldleResult internalPolldleResult = getInternalPolldleResult();

		SseBroadcaster sseBroadcaster = broadcasters.get(this.pathURL);
		if (sseBroadcaster == null) {
			sseBroadcaster = sse.newBroadcaster();
			broadcasters.put(this.pathURL, sseBroadcaster);
		}
		sseBroadcaster.register(eventSink);
		OutboundSseEvent build = sse.newEventBuilder().name(SSE_NAME_EVENT).mediaType(MediaType.APPLICATION_JSON_TYPE)
				.data(PolldleResult.class, internalPolldleResult).build();
		eventSink.send(build);
	}
}
