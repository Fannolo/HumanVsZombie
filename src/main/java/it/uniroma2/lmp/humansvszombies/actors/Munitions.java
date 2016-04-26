package it.uniroma2.lmp.humansvszombies.actors;

import it.uniroma2.lmp.humansvszombies.playground.Field;
import it.uniroma2.lmp.humansvszombies.playground.Location;

public class Munitions {
	private boolean picked = true;

	private Location location;

	public void act(Field currentField, Field updatedField) {
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

	public boolean isAlive() {
		return picked;
	}

	public void setAlive(boolean picked) {
		this.picked = picked;
	}
}
