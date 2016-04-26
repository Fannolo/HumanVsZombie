package it.uniroma2.lmp.humansvszombies.playground;

import it.uniroma2.lmp.humansvszombies.actors.Human;
import it.uniroma2.lmp.humansvszombies.actors.Zombie;
import it.uniroma2.lmp.humansvszombies.gui.GameView;
import it.uniroma2.lmp.humansvszombies.actors.Actor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Gioco HumansVsZombie, basato su turni, all'interno di un campo da gioco.
 *
 */
public class Game
{


    // Lista degli attori
    private List<Human> humans;
    private List<Zombie> zombies;

    // Campo da gioco corrente
    private Field currentField;
    // Campo da gioco utilizzato per il turno successivo del gioco
    private Field updatedField;
    // Turno corrente
    private int turn;
    // GUI del gioco
    private GameView view;
    // Gestione della creazione della popolazione
    private ActorGenerator actorGenerator;


    public Game()
    {
        this(50, 50);
    }
    

    public Game(int depth, int width)
    {

        humans = new ArrayList();
        zombies = new ArrayList();
        currentField = new Field(depth, width);
        updatedField = new Field(depth, width);

        view = new GameView(depth, width);
        
        actorGenerator = new ActorGenerator();
        
        view.setColors(actorGenerator.getColors());
        
        reset();
    }
    

    
    /**
     * Esegui il gioco per un certo numero di turni
     * @param turnsNumber numero di turni.
     */
    public void playGame(int turnsNumber)
    {
        for(int step = 1; step <= turnsNumber && view.isViable(currentField); step++) {
            playOneTurn();
        }
    }
    
    /**
     * Gioca un turno
     */
    public void playOneTurn()
    {
        turn++;

        // Esegui le azioni di tutti i giocatori
        for(Human human : humans) {
            human.act(currentField, updatedField);
        }
        for(Zombie zombie : zombies) {
            zombie.act(currentField, updatedField);
        }

        // Aggiorna il campo da gioco per il prossimo turno
        Field temp = currentField;
        currentField = updatedField;
        updatedField = temp;
        updatedField.clear();

        // Aggiorna la visualizzazione del campo da gioco
        view.showStatus(turn, currentField);
    }
        
    /**
     * Reset del gioco
     */
    public void reset()
    {
        turn = 0;
        humans.clear();
        zombies.clear();
        currentField.clear();
        updatedField.clear();
        actorGenerator.populate(currentField, humans,zombies);

        // Aggiorna la visualizzazione del campo da gioco
        view.showStatus(turn, currentField);
    }
    
   
}
