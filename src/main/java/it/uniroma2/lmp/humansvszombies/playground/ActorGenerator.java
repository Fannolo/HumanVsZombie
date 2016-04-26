package it.uniroma2.lmp.humansvszombies.playground;

import it.uniroma2.lmp.humansvszombies.actors.HuberZombie;
import it.uniroma2.lmp.humansvszombies.actors.Human;
import it.uniroma2.lmp.humansvszombies.actors.Munitions;
import it.uniroma2.lmp.humansvszombies.actors.Zombie;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * Questa classe si occupa di popolare il campo con gli attori del sistema
 *
 */
public class ActorGenerator {

	private Map colorMap;

	public ActorGenerator() {
		colorMap = new HashMap();
		colorMap.put(Human.class, Color.BLUE);
		colorMap.put(Zombie.class, Color.ORANGE);
		colorMap.put(HuberZombie.class, Color.RED);
		colorMap.put(Munitions.class, Color.BLACK);
	}

	/**
	 * Popola il campo con gli attori.
	 * 
	 * @param field
	 *            Il campo che deve essere popolato.
	 */
	public void populate(Field field, List humans, List zombies, List huberZombies,List munitions) {
		Random rand = new Random();
		field.clear();
		for (int row = 0; row < field.getDepth(); row++) {
			for (int col = 0; col < field.getWidth(); col++) {

				if (rand.nextDouble() <= 0.25) {
					HuberZombie huberZombie = new HuberZombie();
					huberZombie.setLocation(row, col);
					huberZombies.add(huberZombie);
					field.place(huberZombie);
				} else if (rand.nextDouble() <= 0.5) {
					Zombie zombie = new Zombie();
					zombie.setLocation(row, col);
					zombies.add(zombie);
					field.place(zombie);

				} else if (rand.nextDouble() <= 1) {
					Human human = new Human();
					human.setLocation(row, col);
					humans.add(human);
					field.place(human);
				} else if (rand.nextDouble() <= 0){
					Munitions munition = new Munitions();
					munition.setLocation(row,col);
					munitions.add(munition);
					field.place(munition);
				}
			}
		}
	}

	public Map getColors() {
		return colorMap;
	}

}
