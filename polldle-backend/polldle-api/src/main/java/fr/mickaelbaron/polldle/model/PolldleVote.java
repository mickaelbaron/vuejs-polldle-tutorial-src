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

	public List<Byte> getPolldleOptionResponses() {
		return polldleOptionResponses;
	}

	public void setPolldleOptionResponses(List<Byte> polldleOptionResponses) {
		this.polldleOptionResponses = polldleOptionResponses;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		if (polldleOptionResponses == null) {
			if (other.polldleOptionResponses != null)
				return false;
		} else if (!polldleOptionResponses.equals(other.polldleOptionResponses))
			return false;
		return true;
	}
}
