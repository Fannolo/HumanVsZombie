package it.uniroma2.lmp.humansvszombies;

import it.uniroma2.lmp.humansvszombies.playground.Game;

/**
 * Created by LorenzoNapoleoni on 06/04/16.
 */
public class App
{
    public static void main( String[] args )
    {
        Game game = new Game();
        game.playGame(50);
    }
}