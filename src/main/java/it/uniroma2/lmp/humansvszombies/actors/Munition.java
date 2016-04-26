package it.uniroma2.lmp.humansvszombies.actors;

import it.uniroma2.lmp.humansvszombies.playground.Field;
import it.uniroma2.lmp.humansvszombies.playground.Location;

/*
 * Munition è la classe per le munizioni o anche forse per altri oggetti raccoglibili per gli umani
 */
public class Munition {

	private Location location;
	private boolean picked;
	
	

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public void setLocation(int row, int col) {
		this.location = new Location(row, col);
	}

	public boolean isPicked() {
		return picked;
	}

	public void setPicked(boolean picked) {
		this.picked = picked;
	}

}
