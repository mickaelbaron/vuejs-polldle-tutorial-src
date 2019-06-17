package fr.mickaelbaron.polldle;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import fr.mickaelbaron.polldle.entity.PolldleEntity;
import fr.mickaelbaron.polldle.entity.PolldleOptionEntity;
import fr.mickaelbaron.polldle.entity.PolldleVoteEntity;
import fr.mickaelbaron.polldle.model.Polldle;
import fr.mickaelbaron.polldle.model.PolldleOption;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
public class BeanFactory {

	public static PolldleVoteEntity genericPollVoteDBFactory(byte value) {
		PolldleVoteEntity newPollVoteDB = new PolldleVoteEntity();
		newPollVoteDB.setPolldleOptions(Arrays.asList(value));
		return newPollVoteDB;
	}

	public static Polldle genericPollFactory() {
		Polldle newPoll = new Polldle();
		newPoll.setQuestion("Are you Vegetarian ?");
		PolldleOption yes = new PolldleOption();
		yes.setName("Yes");
		PolldleOption no = new PolldleOption();
		no.setName("No");
		newPoll.getPolldleOptions().add(yes);
		newPoll.getPolldleOptions().add(no);

		return newPoll;
	}

	public static PolldleEntity genericPollDBFactory() {
		PolldleEntity newPoll = new PolldleEntity();
		newPoll.setQuestion("Are you Vegetarian ?");
		newPoll.setDisplayResult(false);
		newPoll.setEmail("baron.mickael@gmail.com");
		newPoll.setKey("thisismykey");
		newPoll.setMultipleAnswers(false);
		PolldleOptionEntity yes = new PolldleOptionEntity();
		yes.setName("Yes");
		PolldleOptionEntity no = new PolldleOptionEntity();
		no.setName("No");
		newPoll.getPolldleOptions().add(yes);
		newPoll.getPolldleOptions().add(no);

		return newPoll;
	}

	public static PolldleEntity genericPollDBWithIDFactory() {
		PolldleEntity newPoll = new PolldleEntity();
		newPoll.setQuestion("Are you Vegetarian ?");
		newPoll.setDisplayResult(false);
		newPoll.setEmail("baron.mickael@gmail.com");
		newPoll.setKey("thisismykey");
		newPoll.setMultipleAnswers(false);
		newPoll.setPathUrl("1");
		PolldleOptionEntity yes = new PolldleOptionEntity();
		yes.setName("Yes");
		yes.setId((byte) 1);
		PolldleOptionEntity no = new PolldleOptionEntity();
		no.setName("No");
		no.setId((byte) 2);
		newPoll.getPolldleOptions().add(yes);
		newPoll.getPolldleOptions().add(no);

		return newPoll;
	}

	public static Map<Byte, Integer> genericPollOptionResultWithTwoVotesDBFactory() {
		Map<Byte, Integer> collections = new HashMap<Byte, Integer>();
		collections.put((byte) 1, 5);
		collections.put((byte) 2, 10);

		return collections;
	}

	public static Map<Byte, Integer> genericPollOptionResultWithOneVoteDBFactory() {
		Map<Byte, Integer> collections = new HashMap<Byte, Integer>();
		collections.put((byte) 1, 5);

		return collections;
	}

	public static Map<Byte, Integer> genericPollOptionResultWithZeroVoteDBFactory() {
		return new HashMap<Byte, Integer>();
	}
}
