package fr.mickaelbaron.polldle.entity;

import java.util.ArrayList;
import java.util.List;

import fr.mickaelbaron.polldle.model.Polldle;
import fr.mickaelbaron.polldle.model.PolldleOption;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
public class PolldleEntity {

	/**
	 * The unique identifier. It's used also to give the relative path.
	 */
	private String pathUrl;

	/**
	 * The poll title.
	 */
	private String question;

	/**
	 * The poll options.
	 */
	private List<PolldleOptionEntity> polldleOptions;

	/**
	 * A generated key used for identifying the owner.
	 */
	private String key;

	/**
	 * Except the admin, the user can view the result.
	 */
	private boolean isDisplayResult;

	/**
	 * Can you choose several poll options ?
	 */
	private boolean isMultipleAnswers;

	/**
	 * Email of the owner.
	 */
	private String email;

	/**
	 * The QR code after the creation.
	 */
	private boolean isGenerateQRCode;

	public PolldleEntity() {
		this.polldleOptions = new ArrayList<PolldleOptionEntity>();
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public List<PolldleOptionEntity> getPolldleOptions() {
		return polldleOptions;
	}

	public void setPolldleOptions(List<PolldleOptionEntity> pollOptions) {
		this.polldleOptions = pollOptions;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + (isDisplayResult ? 1231 : 1237);
		result = prime * result + (isGenerateQRCode ? 1231 : 1237);
		result = prime * result + (isMultipleAnswers ? 1231 : 1237);
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((pathUrl == null) ? 0 : pathUrl.hashCode());
		result = prime * result + ((polldleOptions == null) ? 0 : polldleOptions.hashCode());
		result = prime * result + ((question == null) ? 0 : question.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PolldleEntity other = (PolldleEntity) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (isDisplayResult != other.isDisplayResult)
			return false;
		if (isGenerateQRCode != other.isGenerateQRCode)
			return false;
		if (isMultipleAnswers != other.isMultipleAnswers)
			return false;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (pathUrl == null) {
			if (other.pathUrl != null)
				return false;
		} else if (!pathUrl.equals(other.pathUrl))
			return false;
		if (polldleOptions == null) {
			if (other.polldleOptions != null)
				return false;
		} else if (!polldleOptions.equals(other.polldleOptions))
			return false;
		if (question == null) {
			if (other.question != null)
				return false;
		} else if (!question.equals(other.question))
			return false;
		return true;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public boolean isDisplayResult() {
		return isDisplayResult;
	}

	public void setDisplayResult(boolean isDisplayResult) {
		this.isDisplayResult = isDisplayResult;
	}

	public boolean isMultipleAnswers() {
		return isMultipleAnswers;
	}

	public void setMultipleAnswers(boolean isMultipleAnswers) {
		this.isMultipleAnswers = isMultipleAnswers;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isGenerateQRCode() {
		return isGenerateQRCode;
	}

	public void setGenerateQRCode(boolean isGenerateQRCode) {
		this.isGenerateQRCode = isGenerateQRCode;
	}

	public String getPathUrl() {
		return pathUrl;
	}

	public void setPathUrl(String pathUrl) {
		this.pathUrl = pathUrl;
	}

	public static PolldleEntity createDB(Polldle poll) {
		PolldleEntity newPollDB = new PolldleEntity();
		newPollDB.setKey(poll.getKey());
		newPollDB.setPathUrl(poll.getPathUrl());
		if (poll.getPolldleOptions() == null) {
			return null;
		}
		List<PolldleOptionEntity> newPollOptionsDB = new ArrayList<PolldleOptionEntity>();
		for (PolldleOption currentPollOption : poll.getPolldleOptions()) {
			newPollOptionsDB.add(PolldleOptionEntity.createDB(currentPollOption));
		}
		newPollDB.setPolldleOptions(newPollOptionsDB);
		newPollDB.setQuestion(poll.getQuestion());

		return newPollDB;
	}

	public static Polldle create(PolldleEntity pollDB) {
		return PolldleEntity.create(pollDB, false);
	}

	public static Polldle create(PolldleEntity pollDB, boolean withoutId) {
		Polldle newPoll = new Polldle();
		newPoll.setKey(withoutId ? null : pollDB.getKey());
		newPoll.setPathUrl(pollDB.getPathUrl());
		newPoll.setQuestion(pollDB.getQuestion());
		List<PolldleOption> newPollOptions = new ArrayList<PolldleOption>();
		if (pollDB.getPolldleOptions() == null) {
			return null;
		}
		for (PolldleOptionEntity currentPollOptionDB : pollDB.getPolldleOptions()) {
			newPollOptions.add(PolldleOptionEntity.create(currentPollOptionDB));
		}
		newPoll.setPollOptions(newPollOptions);
		return newPoll;
	}
}