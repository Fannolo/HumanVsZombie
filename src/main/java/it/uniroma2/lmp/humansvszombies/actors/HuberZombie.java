package it.uniroma2.lmp.humansvszombies.actors;

import java.util.Iterator;

import it.uniroma2.lmp.humansvszombies.playground.Field;
import it.uniroma2.lmp.humansvszombies.playground.Location;
@SuppressWarnings("rawtypes")
public class HuberZombie extends Zombie {

	void findHumansToInfect(Field field, Location location, Field updatedField) {
		Iterator adjacentLocations = field.adjacentLocations(location);
		while (adjacentLocations.hasNext()) {
			Location where = (Location) adjacentLocations.next();
			Object actor = field.getObjectAt(where);
			if (actor instanceof Human) {
				Human human = (Human) actor;
				human.infectedHuman(updatedField);
			}
		}
	}
}
