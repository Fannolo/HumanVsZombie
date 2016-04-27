package it.uniroma2.lmp.humansvszombies.utils;

import it.uniroma2.lmp.humansvszombies.actors.HuberZombie;
import it.uniroma2.lmp.humansvszombies.actors.Human;
import it.uniroma2.lmp.humansvszombies.actors.Munition;
import it.uniroma2.lmp.humansvszombies.actors.Zombie;
import it.uniroma2.lmp.humansvszombies.playground.Field;
import it.uniroma2.lmp.humansvszombies.utils.Counter;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Questa classe colleziona le statistiche del gico
 *
 */
public class FieldStats {
	// Mappa per il contatore degli attori
	private HashMap counters;
	// Flag che controlla l'aggiornamento dei contatori
	private boolean countsValid;

	public FieldStats() {

		counters = new HashMap();
		countsValid = true;
	}

	public String getActorDetails(Field field) {
		StringBuffer buffer = new StringBuffer();
		if (!countsValid) {
			generateCounts(field);
		}
		Iterator keys = counters.keySet().iterator();
		while (keys.hasNext()) {
			Counter info = (Counter) counters.get(keys.next());
			buffer.append(info.getName());
			buffer.append(": ");
			buffer.append(info.getCount());
			buffer.append(' ');
		}
		return buffer.toString();
	}

	public void reset() {
		countsValid = false;
		Iterator keys = counters.keySet().iterator();
		while (keys.hasNext()) {
			Counter cnt = (Counter) counters.get(keys.next());
			cnt.reset();
		}
	}

	public void incrementCount(Class actorClass) {
		Counter cnt = (Counter) counters.get(actorClass);
		if (cnt == null) {
			cnt = new Counter(actorClass.getSimpleName());
			counters.put(actorClass, cnt);
		}
		cnt.increment();
	}

	public void countFinished() {
		countsValid = true;
	}

	/**
	 * Determina se il gioco deve andare avanti
	 * 
	 * @return true se ci sono giocatori attivi.
	 */
	public boolean isViable(Field field) {
		int nonZero = 0;
		if (!countsValid) {
			generateCounts(field);
		}
		Iterator keys = counters.keySet().iterator();
		while (keys.hasNext()) {
			Counter info = (Counter) counters.get(keys.next());
			if (info.getCount() > 0) {
				nonZero++;
			}
		}
		return nonZero > 3 || isViableWithMunitionHuberZombie();
	}

	/**
	 * isViable adattato per tutti gli altri attori del gioco (huberZombie e
	 * munizioni)
	 * 
	 * @return true se la partita deve continaure
	 */

	private boolean isViableWithMunitionHuberZombie() {
		Counter infoMunition = (Counter) counters.get(Munition.class);
		Counter infoHuman = (Counter) counters.get(Human.class);
		Counter infoZombie = (Counter) counters.get(Zombie.class);
		Counter infoHuberZombie = (Counter) counters.get(HuberZombie.class);
		if (infoMunition.getCount() == 0 && infoHuman.getCount() != 0 && infoZombie.getCount() != 0
				&& infoHuberZombie.getCount() != 0) {
			return true;
		} else if (infoMunition.getCount() != 0 && infoHuman.getCount() != 0 && infoZombie.getCount() != 0
				&& infoHuberZombie.getCount() == 0) {
			return true;
		} else if (infoMunition.getCount() != 0 && infoHuman.getCount() != 0 && infoZombie.getCount() == 0
				&& infoHuberZombie.getCount() != 0) {
			return true;
		} else if (infoMunition.getCount() == 0 && infoHuman.getCount() != 0 && infoZombie.getCount() != 0
				&& infoHuberZombie.getCount() == 0) {
			return true;
		} else if (infoMunition.getCount() == 0 && infoHuman.getCount() != 0 && infoZombie.getCount() == 0
				&& infoHuberZombie.getCount() != 0) {
			return true;
		}
		return false;
	}

	private void generateCounts(Field field) {
		reset();
		for (int row = 0; row < field.getDepth(); row++) {
			for (int col = 0; col < field.getWidth(); col++) {
				Object actor = field.getObjectAt(row, col);
				if (actor != null) {
					incrementCount(actor.getClass());
				}
			}
		}
		countsValid = true;
	}
}
