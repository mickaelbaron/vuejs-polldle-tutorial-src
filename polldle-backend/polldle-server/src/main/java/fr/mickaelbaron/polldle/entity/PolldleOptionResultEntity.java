package fr.mickaelbaron.polldle.entity;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
public class PolldleOptionResultEntity {

	/**
	 * Id of PollOption.
	 */
	private Byte polldleOptionId;

	/**
	 * The counter for PollOption.
	 */
	private Integer counter;

	public Byte getPolldleOptionId() {
		return polldleOptionId;
	}

	public void setPolldleOptionId(Byte pollOptionId) {
		this.polldleOptionId = pollOptionId;
	}

	public Integer getCounter() {
		return counter;
	}

	public void setCounter(Integer counter) {
		this.counter = counter;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((counter == null) ? 0 : counter.hashCode());
		result = prime * result + ((polldleOptionId == null) ? 0 : polldleOptionId.hashCode());
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
		PolldleOptionResultEntity other = (PolldleOptionResultEntity) obj;
		if (counter == null) {
			if (other.counter != null)
				return false;
		} else if (!counter.equals(other.counter))
			return false;
		if (polldleOptionId == null) {
			if (other.polldleOptionId != null)
				return false;
		} else if (!polldleOptionId.equals(other.polldleOptionId))
			return false;
		return true;
	}
}
