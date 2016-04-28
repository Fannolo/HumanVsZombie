package it.uniroma2.lmp.humansvszombies.playground;

import it.uniroma2.lmp.humansvszombies.actors.HuberZombie;
import it.uniroma2.lmp.humansvszombies.actors.Human;
import it.uniroma2.lmp.humansvszombies.actors.Munition;
import it.uniroma2.lmp.humansvszombies.actors.Zombie;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
@SuppressWarnings({"rawtypes","unchecked"})
/**
 * Questa classe rappresenta il campo da gioco. Il campo da gioco è
 * rappesentatno da una griglia di posizioni. Ogni posizione può contenere un
 * solo attore.
 *
 */
public class Field {
	private static final Random rand = new Random();

	// Lunghezza e larghezza del campo.
	private int depth, width;
	// Storage degli attori
	private Object[][] field;

	public Field(int depth, int width) {
		this.depth = depth;
		this.width = width;
		field = new Object[depth][width];
	}

	/**
	 * Svuota il campo.
	 */
	public void clear() {
		for (int row = 0; row < depth; row++) {
			for (int col = 0; col < width; col++) {
				field[row][col] = null;
			}
		}
	}

	/**
	 * Posziona un attore nel campo
	 *
	 */
	public void place(Object actor) {
		if (actor instanceof Human) {
			Location location = ((Human) actor).getLocation();
			field[location.getRow()][location.getCol()] = actor;
		}
		if (actor instanceof Zombie) {
			Location location = ((Zombie) actor).getLocation();
			field[location.getRow()][location.getCol()] = actor;
		}
		if (actor instanceof HuberZombie) {
			Location location = ((HuberZombie) actor).getLocation();
			field[location.getRow()][location.getCol()] = actor;
		}
		if (actor instanceof Munition) {
			Location location = ((Munition) actor).getLocation();
			field[location.getRow()][location.getCol()] = actor;
		}
	}

	/**
	 * Restituisce l'attore nella posizione data in input
	 * 
	 * @param location
	 *            posizione nel campo
	 * @return L'attore nella posizione passata in input, se esiste
	 */
	public Object getObjectAt(Location location) {
		return getObjectAt(location.getRow(), location.getCol());
	}

	public Object getObjectAt(int row, int col) {
		return field[row][col];
	}

	/**
	 * Cerca una posizione libera adiacente alla posizione data in input
	 * 
	 * @param location
	 *            location da cui cercare le posizioni libere.
	 * @return una posizione valida
	 */
	public Location freeAdjacentLocation(Location location) {
		Iterator adjacent = adjacentLocations(location);
		while (adjacent.hasNext()) {
			Location next = (Location) adjacent.next();
			if (field[next.getRow()][next.getCol()] == null) {
				return next;
			}
		}
		if (field[location.getRow()][location.getCol()] == null) {
			return location;
		} else {
			return null;
		}
	}

	/**
	 * Cerca le posizioni adiacenti alla posizione data in input
	 * 
	 * @param location
	 *            location da cui cercare le posizioni libere.
	 * @return Iterator di posizioni
	 */
	public Iterator adjacentLocations(Location location) {
		int row = location.getRow();
		int col = location.getCol();
		LinkedList locations = new LinkedList();
		for (int roffset = -1; roffset <= 1; roffset++) {
			int nextRow = row + roffset;
			if (nextRow >= 0 && nextRow < depth) {
				for (int coffset = -1; coffset <= 1; coffset++) {
					int nextCol = col + coffset;
					if (nextCol >= 0 && nextCol < width && (roffset != 0 || coffset != 0)) {
						locations.add(new Location(nextRow, nextCol));
					}
				}
			}
		}
		Collections.shuffle(locations, rand);
		return locations.iterator();
	}

	public int getDepth() {
		return depth;
	}

	public int getWidth() {
		return width;
	}
}
