package fr.mickaelbaron.polldle.entity;

import fr.mickaelbaron.polldle.model.PolldleOption;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
public class PolldleOptionEntity {

	/**
	 * Unique identifier.
	 */
	private Byte id;

	/**
	 * Read name.
	 */
	private String name;

	/**
	 * To know if this option has been destroyed or not.
	 */
	private boolean isDestroyed;

	public Byte getId() {
		return id;
	}

	public void setId(Byte id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (isDestroyed ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		PolldleOptionEntity other = (PolldleOptionEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isDestroyed != other.isDestroyed)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public boolean isDestroyed() {
		return isDestroyed;
	}

	public void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}

	public static PolldleOptionEntity createDB(PolldleOption currentPollOption) {
		PolldleOptionEntity newPollOptionDB = new PolldleOptionEntity();
		newPollOptionDB.setDestroyed(currentPollOption.isDestroyed());
		newPollOptionDB.setId(currentPollOption.getId());
		newPollOptionDB.setName(currentPollOption.getName());
		return newPollOptionDB;
	}

	public static PolldleOption create(PolldleOptionEntity currentPollOptionDB) {
		PolldleOption newPollOption = new PolldleOption();
		newPollOption.setDestroyed(currentPollOptionDB.isDestroyed());
		newPollOption.setId(currentPollOptionDB.getId());
		newPollOption.setName(currentPollOptionDB.getName());
		return newPollOption;
	}
}
