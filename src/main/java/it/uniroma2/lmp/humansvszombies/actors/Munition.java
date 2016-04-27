package it.uniroma2.lmp.humansvszombies.actors;

import it.uniroma2.lmp.humansvszombies.playground.Field;
import it.uniroma2.lmp.humansvszombies.playground.Location;

public class Munition {

	private boolean unpicked = true;
	private Location location;

	public void act(Field currentField, Field updatedField) {

		if (unpicked) {
			updatedField.place(this);
		}
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public void setLocation(int row, int col) {
		this.location = new Location(row, col);
	}

	public void setPicked() {
		unpicked = false;
	}

	public boolean isUnpicked() {
		return unpicked;
	}

	public void setUnpicked(boolean unpicked) {
		this.unpicked = unpicked;
	}
}
