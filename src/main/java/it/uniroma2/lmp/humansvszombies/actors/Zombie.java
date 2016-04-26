package it.uniroma2.lmp.humansvszombies.actors;

import it.uniroma2.lmp.humansvszombies.playground.Field;
import it.uniroma2.lmp.humansvszombies.playground.Location;

import java.util.Iterator;

/**
 * Classe che rappresenta gli zombie
 *
 *
 */
public class Zombie {

    private boolean alive = true;

    private Location location;

    public void act(Field currentField, Field updatedField) {

        if(isAlive()){
            //Cerca umano da infettare
            findHumansToInfect(currentField, getLocation(),updatedField);
            //Sposta lo zombie alla ricerca di altri umani
            Location newLocation = updatedField.freeAdjacentLocation(getLocation());

            if(newLocation != null) {
                setLocation(newLocation);
                updatedField.place(this);  // sets location
            }
        }

    }

    /**
     * Metodo per la ricerca degli umani da infettare
     * @param field campo da gioco
     * @param location location corrente dello zombie
     * @param updatedField campo da gioco per il prossimo turno
     */
    private void findHumansToInfect(Field field, Location location, Field updatedField) {

        Iterator adjacentLocations =  field.adjacentLocations(location);
        while(adjacentLocations.hasNext()) {
            Location where = (Location) adjacentLocations.next();
            Object actor = field.getObjectAt(where);
            if(actor instanceof Human) {
                Human human = (Human) actor;
                human.infectedHuman(updatedField);
                return;
            }
        }
    }


    public void setDead()
    {
        alive = false;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location)
    {
        this.location = location;
    }

    public void setLocation(int row, int col)
    {
        this.location = new Location(row, col);
    }


    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
