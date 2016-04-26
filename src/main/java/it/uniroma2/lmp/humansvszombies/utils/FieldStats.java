package it.uniroma2.lmp.humansvszombies.utils;

import it.uniroma2.lmp.humansvszombies.playground.Field;
import it.uniroma2.lmp.humansvszombies.utils.Counter;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Questa classe colleziona le statistiche del gico
 *
 */
public class FieldStats
{
    // Mappa per il contatore degli attori
    private HashMap counters;
    // Falg che controlla l'aggiornamento dei contatori
    private boolean countsValid;


    public FieldStats()
    {

        counters = new HashMap();
        countsValid = true;
    }


    public String getActorDetails(Field field)
    {
        StringBuffer buffer = new StringBuffer();
        if(!countsValid) {
            generateCounts(field);
        }
        Iterator keys = counters.keySet().iterator();
        while(keys.hasNext()) {
            Counter info = (Counter) counters.get(keys.next());
            buffer.append(info.getName());
            buffer.append(": ");
            buffer.append(info.getCount());
            buffer.append(' ');
        }
        return buffer.toString();
    }
    

    public void reset()
    {
        countsValid = false;
        Iterator keys = counters.keySet().iterator();
        while(keys.hasNext()) {
            Counter cnt = (Counter) counters.get(keys.next());
            cnt.reset();
        }
    }


    public void incrementCount(Class actorClass)
    {
        Counter cnt = (Counter) counters.get(actorClass);
        if(cnt == null) {
            cnt = new Counter(actorClass.getSimpleName());
            counters.put(actorClass, cnt);
        }
        cnt.increment();
    }


    public void countFinished()
    {
        countsValid = true;
    }

    /**
     * Determina se il gioco deve andare avanti
     * @return true se ci sono giocatori attivi.
     */
    public boolean isViable(Field field)
    {
        int nonZero = 0;
        if(!countsValid) {
            generateCounts(field);
        }
        Iterator keys = counters.keySet().iterator();
        while(keys.hasNext()) {
            Counter info = (Counter) counters.get(keys.next());
            if(info.getCount() > 0) {
                nonZero++;
            }
        }
        return nonZero > 1;
    }
    

    private void generateCounts(Field field)
    {
        reset();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                Object actor = field.getObjectAt(row, col);
                if(actor != null) {
                    incrementCount(actor.getClass());
                }
            }
        }
        countsValid = true;
    }
}
