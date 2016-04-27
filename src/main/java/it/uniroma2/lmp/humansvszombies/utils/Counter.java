package it.uniroma2.lmp.humansvszombies.utils;

/**
 *
 * Fornisce un contatore degli attori del gioco.
 *
 */
public class Counter {
	// Nome della classe che rappresenta un attore
	private String name;
	// Numero di pedine
	private int count;

	public Counter(String name) {
		this.name = name;
		count = 0;
	}

	public String getName() {
		return name;
	}

	public int getCount() {
		return count;
	}

	public void increment() {
		count++;
	}

	public void reset() {
		count = 0;
	}
}
