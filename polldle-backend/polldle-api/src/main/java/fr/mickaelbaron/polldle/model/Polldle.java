package fr.mickaelbaron.polldle.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
public class Polldle {

	/**
	 * The poll title.
	 */
	private String question;

	/**
	 * The poll options.
	 */
	private List<PolldleOption> polldleOptions;

	/**
	 * A generated key used for identifying the owner.
	 */
	private String key;

	/**
	 * To describe the relative path.
	 */
	private String pathUrl;

	public Polldle() {
		this.polldleOptions = new ArrayList<PolldleOption>();
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public List<PolldleOption> getPolldleOptions() {
		return polldleOptions;
	}

	public void setPollOptions(List<PolldleOption> polldleOptions) {
		this.polldleOptions = polldleOptions;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		Polldle other = (Polldle) obj;
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

	public String getPathUrl() {
		return pathUrl;
	}

	public void setPathUrl(String pathUrl) {
		this.pathUrl = pathUrl;
	}
}
