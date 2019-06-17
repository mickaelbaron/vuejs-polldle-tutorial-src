package fr.mickaelbaron.polldle.model;

import java.util.List;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
public class PolldleResult {

	/**
	 * The poll title.
	 */
	private String question;

	/**
	 * To describe the relative path.
	 */
	private String pathUrl;

	/**
	 * The result.
	 */
	private List<PolldleOptionResult> results;

	public List<PolldleOptionResult> getResults() {
		return results;
	}

	public void setResults(List<PolldleOptionResult> results) {
		this.results = results;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getPathUrl() {
		return pathUrl;
	}

	public void setPathUrl(String pathUrl) {
		this.pathUrl = pathUrl;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pathUrl == null) ? 0 : pathUrl.hashCode());
		result = prime * result + ((question == null) ? 0 : question.hashCode());
		result = prime * result + ((results == null) ? 0 : results.hashCode());
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
		PolldleResult other = (PolldleResult) obj;
		if (pathUrl == null) {
			if (other.pathUrl != null)
				return false;
		} else if (!pathUrl.equals(other.pathUrl))
			return false;
		if (question == null) {
			if (other.question != null)
				return false;
		} else if (!question.equals(other.question))
			return false;
		if (results == null) {
			if (other.results != null)
				return false;
		} else if (!results.equals(other.results))
			return false;
		return true;
	}
}
