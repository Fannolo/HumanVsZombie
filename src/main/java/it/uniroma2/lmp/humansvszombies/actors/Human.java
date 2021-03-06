package it.uniroma2.lmp.humansvszombies.actors;

import it.uniroma2.lmp.humansvszombies.playground.Field;
import it.uniroma2.lmp.humansvszombies.playground.Location;

import java.util.Iterator;

@SuppressWarnings("rawtypes")
/**
 * Human è la classe che rappresenta gli umani
 *
 */
public class Human {

	// Location dell'umano
	private Location location;
	// Parametro che rappresenta se l'umano è infettato o no
	private boolean infected;

	// numero di munizioni di un umano all'inizio
	private int munitions = 5;

	public void act(Field currentField, Field updatedField) {

		// Se l'umano è infettato non può fare nulla
		if (!infected) {

			if (munitions < 5) {
				// raccogli le munizioni
				findMunitionsToPick(currentField, getLocation());
			}

			if (munitions > 0) {
				// uccidi gli zombie adiacenti alla posizione dell'umano
				findZombieToKill(currentField, getLocation());
			}

			// Sposta l'umano alla ricerca degli zombie o di munizioni
			Location newLocation = updatedField.freeAdjacentLocation(getLocation());

			if (newLocation != null) {
				setLocation(newLocation);
				updatedField.place(this);
			}

		}

	}

	/**
	 * Metodo per la ricerca delle munizioni
	 * 
	 * @param field
	 *            Campo da gioco
	 * @param location
	 *            Location dell'umano
	 */

	public void findMunitionsToPick(Field field, Location location) {
		Iterator adjacentLocations = field.adjacentLocations(location);
		while (adjacentLocations.hasNext()) {
			Location where = (Location) adjacentLocations.next();
			Object actor = field.getObjectAt(where);
			if (actor instanceof Munition) {
				Munition munition = (Munition) actor;
				munition.setPicked();
				munitions++;
				break;
			}
		}
	}

	/**
	 * Metodo per la ricerca degli zombie da uccidere
	 * 
	 * @param field
	 *            campo da gioco
	 * @param location
	 *            location dell'umano
	 */
	private void findZombieToKill(Field field, Location location) {

		Iterator adjacentLocations = field.adjacentLocations(location);
		while (adjacentLocations.hasNext()) {
			Location where = (Location) adjacentLocations.next();
			Object actor = field.getObjectAt(where);
			if (actor instanceof Zombie) {
				Zombie zombie = (Zombie) actor;
				zombie.setDead();
				munitions--;
				break;
			} else if (actor instanceof HuberZombie) {
				HuberZombie huberZombie = (HuberZombie) actor;
				huberZombie.setDead();
				munitions--;
				break;

			}
		}

	}

	/**
	 * Metodo per infettare un umano. Posiziona una zombie al posto dell'umano
	 * 
	 * @param field
	 *            campo da gioco
	 */
	public void infectedHuman(Field field) {
		infected = true;
		Zombie zombie = new Zombie();
		zombie.setLocation(getLocation());
		field.place(zombie);
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

	public boolean isInfected() {
		return infected;
	}

	public void setInfected(boolean infected) {
		this.infected = infected;
	}
}
