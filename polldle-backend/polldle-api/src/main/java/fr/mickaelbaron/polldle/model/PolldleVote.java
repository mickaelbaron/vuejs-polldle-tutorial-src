package fr.mickaelbaron.polldle.model;

import java.util.List;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
public class PolldleVote {

	/**
	 * The response.
	 */
	private List<Byte> polldleOptionResponses;

	/**
	 * Poll identifier.
	 */
	private String pathUrl;

	public List<Byte> getPolldleOptionResponses() {
		return polldleOptionResponses;
	}

	public void setPolldleOptionResponses(List<Byte> polldleOptionResponses) {
		this.polldleOptionResponses = polldleOptionResponses;
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
		result = prime * result + ((polldleOptionResponses == null) ? 0 : polldleOptionResponses.hashCode());
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
		PolldleVote other = (PolldleVote) obj;
		if (pathUrl == null) {
			if (other.pathUrl != null)
				return false;
		} else if (!pathUrl.equals(other.pathUrl))
			return false;
		if (polldleOptionResponses == null) {
			if (other.polldleOptionResponses != null)
				return false;
		} else if (!polldleOptionResponses.equals(other.polldleOptionResponses))
			return false;
		return true;
	}
}
