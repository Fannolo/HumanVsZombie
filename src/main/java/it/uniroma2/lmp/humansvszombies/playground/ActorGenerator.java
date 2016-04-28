package it.uniroma2.lmp.humansvszombies.playground;

import it.uniroma2.lmp.humansvszombies.actors.HuberZombie;
import it.uniroma2.lmp.humansvszombies.actors.Human;
import it.uniroma2.lmp.humansvszombies.actors.Munition;
import it.uniroma2.lmp.humansvszombies.actors.Zombie;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
@SuppressWarnings({"rawtypes", "unchecked"})
/**
 *
 * Questa classe si occupa di popolare il campo con gli attori del sistema
 *
 */
public class ActorGenerator {

	private Map colorMap;

	public ActorGenerator() {
		colorMap = new HashMap();
		// colore per gli umani
		colorMap.put(Human.class, Color.BLUE);
		// colore per gli zombie
		colorMap.put(Zombie.class, Color.ORANGE);
		// colore scelto per gli HuberZombie
		colorMap.put(HuberZombie.class, Color.RED);
		// colore scelto per le munizioni
		colorMap.put(Munition.class, Color.BLACK);
	}

	/**
	 * Popola il campo con gli attori.
	 * 
	 * @param field
	 *            Il campo che deve essere popolato.
	 */
	public void populate(Field field, List humans, List zombies, List huberZombies, List munitions) {
		Random rand = new Random();
		field.clear();
		for (int row = 0; row < field.getDepth(); row++) {
			for (int col = 0; col < field.getWidth(); col++) {
				if (rand.nextDouble() <= 0.1) {
					Munition munition = new Munition();
					munition.setLocation(row, col);
					munitions.add(munition);
					field.place(munition);
				} else if (rand.nextDouble() <= 0.2) {
					HuberZombie huberZombie = new HuberZombie();
					huberZombie.setLocation(row, col);
					huberZombies.add(huberZombie);
					field.place(huberZombie);
				} else if (rand.nextDouble() <= 0.4) {
					Zombie zombie = new Zombie();
					zombie.setLocation(row, col);
					zombies.add(zombie);
					field.place(zombie);
				} else if (rand.nextDouble() <= 0.65) {
					Human human = new Human();
					human.setLocation(row, col);
					humans.add(human);
					field.place(human);
				}
			}
		}
	}

	public Map getColors() {
		return colorMap;
	}

}
