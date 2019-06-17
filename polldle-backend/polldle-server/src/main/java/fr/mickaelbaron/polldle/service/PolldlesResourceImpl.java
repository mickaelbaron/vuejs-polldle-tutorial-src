package fr.mickaelbaron.polldle.service;

import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response.Status;

import fr.mickaelbaron.polldle.api.PolldlesResource;
import fr.mickaelbaron.polldle.api.VotesResource;
import fr.mickaelbaron.polldle.dao.IPolldleDAO;
import fr.mickaelbaron.polldle.entity.PolldleEntity;
import fr.mickaelbaron.polldle.model.Polldle;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
public class PolldlesResourceImpl implements PolldlesResource {

	@Inject
	private IPolldleDAO pollDAO;

	@Context
	private ResourceContext resourceContext;

	@Override
	public Polldle createPolldle(Polldle newPoll) {
		// Check the content.
		if (newPoll.getQuestion() == null || newPoll.getQuestion().isEmpty()) {
			throw new WebApplicationException("Question is missing.", Status.BAD_REQUEST);
		}

		if (newPoll.getPolldleOptions() == null || newPoll.getPolldleOptions().size() < 2) {
			throw new WebApplicationException("Polldle option can not be null and must contain at least two elements.",
					Status.BAD_REQUEST);
		}

		final PolldleEntity createDB = PolldleEntity.createDB(newPoll);
		if (createDB == null) {
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}

		final PolldleEntity createPolldle = pollDAO.createPolldle(createDB);
		if (createPolldle == null) {
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}
		final Polldle create = PolldleEntity.create(createPolldle);
		if (create == null) {
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}
		return create;
	}

	@Override
	public Polldle getPolldleByPathURL(String pathURL) {
		if (pathURL == null) {
			throw new WebApplicationException("Path URL is missing.", Status.BAD_REQUEST);
		}

		// Get Poll from pathURL value.
		final Optional<PolldleEntity> pollByPathURL = pollDAO.getPolldleByPathURL(pathURL);

		if (!pollByPathURL.isPresent()) {
			throw new WebApplicationException("No Polldle with this path URL.", Status.BAD_REQUEST);
		}

		// Transform PollDB to Poll without private data.
		final Polldle create = PolldleEntity.create(pollByPathURL.get(), true);

		return create;
	}

	@Override
	public VotesResource getVotesResource(String pathUrl) {
		VotesResourceImpl resource = resourceContext.getResource(VotesResourceImpl.class);
		resource.setPathURL(pathUrl);
		return resource;
	}
}
