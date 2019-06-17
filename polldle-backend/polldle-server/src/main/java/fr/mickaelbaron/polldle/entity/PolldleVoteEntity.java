package fr.mickaelbaron.polldle.entity;

import java.util.List;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
public class PolldleVoteEntity {

	private List<Byte> polldleOptions;

	public List<Byte> getPolldleOptions() {
		return polldleOptions;
	}

	public void setPolldleOptions(List<Byte> pollOptions) {
		this.polldleOptions = pollOptions;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((polldleOptions == null) ? 0 : polldleOptions.hashCode());
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
		PolldleVoteEntity other = (PolldleVoteEntity) obj;
		if (polldleOptions == null) {
			if (other.polldleOptions != null)
				return false;
		} else if (!polldleOptions.equals(other.polldleOptions))
			return false;
		return true;
	}
}
