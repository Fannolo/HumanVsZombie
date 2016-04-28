package it.uniroma2.lmp.humansvszombies.gui;

import it.uniroma2.lmp.humansvszombies.playground.Field;
import it.uniroma2.lmp.humansvszombies.playground.Game;
import it.uniroma2.lmp.humansvszombies.utils.FieldStats;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 *
 * Rappresentazione grafica della griglia del campo. Questa view visualizza le
 * location come dei rettangoli colorati in base al contenuto della location.
 *
 */
public class GameView extends JFrame {
	// Colore per le caselle vuote
	private static final Color EMPTY_COLOR = Color.white;

	// Colore usato per gli oggetti non definiti
	private static final Color UNKNOWN_COLOR = Color.gray;

	private final String STEP_PREFIX = "Step: ";
	private final String POPULATION_PREFIX = "Pedine: ";
	private JLabel stepLabel, population;
	private FieldView fieldView;

	// Una mappa che memorizza i colori delle pedine del gioco
	private HashMap colors;
	// Campo per la statistica
	private FieldStats stats;
	private JTextField txtInsertNumberTurns;
	private JLabel lblNewLabel;

	/**
	 * Crea la visuale del campo da gioco
	 * 
	 * @param height
	 *            altezza del campo
	 * @param width
	 *            larghezza del campo
	 */
	public GameView(int height, int width) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setPreferredSize(new Dimension(450, 500));
		stats = new FieldStats();
		colors = new HashMap();

		setTitle("Humans vs Zombies");
		stepLabel = new JLabel(STEP_PREFIX, JLabel.CENTER);
		population = new JLabel(POPULATION_PREFIX, JLabel.CENTER);

		setLocation(100, 50);

		fieldView = new FieldView(height, width);

		Container contents = getContentPane();
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		contents.add(stepLabel);
		contents.add(fieldView, BorderLayout.CENTER);
		contents.add(population);

		txtInsertNumberTurns = new JTextField();
		txtInsertNumberTurns.setMaximumSize(new Dimension(300, 26));
		txtInsertNumberTurns.setText("Insert number turns");
		getContentPane().add(txtInsertNumberTurns);
		txtInsertNumberTurns.setColumns(10);

		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Game game = new Game(50, 50);
				try {
					int turns = Integer.parseInt(txtInsertNumberTurns.getText());
					game.playGame(turns);
				} catch (NumberFormatException e1) {
					lblNewLabel.setVisible(true);
				}
			}
		});
		
		lblNewLabel = new JLabel("You did not insert a number!");
		lblNewLabel.setVisible(false);
		getContentPane().add(lblNewLabel);
		getContentPane().add(btnStart);
		pack();
		setVisible(true);
	}

	public void setColor(Class actorClass, Color color) {
		colors.put(actorClass, color);
	}

	public void setColors(Map colorMap) {
		colors.putAll(colorMap);
	}

	private Color getColor(Class actorClass) {
		Color col = (Color) colors.get(actorClass);
		if (col == null) {
			return UNKNOWN_COLOR;
		} else {
			return col;
		}
	}

	/**
	 * Visulizza lo stato corrente del campo
	 * 
	 * @param turn
	 *            Turno di gioco
	 * @param field
	 *            Campo da gioco corrente.
	 */
	public void showStatus(int turn, Field field) {
		if (!isVisible())
			setVisible(true);

		stepLabel.setText(STEP_PREFIX + turn);

		stats.reset();
		fieldView.preparePaint();

		for (int row = 0; row < field.getDepth(); row++) {
			for (int col = 0; col < field.getWidth(); col++) {
				Object actor = field.getObjectAt(row, col);
				if (actor != null) {
					stats.incrementCount(actor.getClass());
					fieldView.drawMark(col, row, getColor(actor.getClass()));
				} else {
					fieldView.drawMark(col, row, EMPTY_COLOR);
				}
			}
		}
		stats.countFinished();

		population.setText(POPULATION_PREFIX + stats.getActorDetails(field));
		fieldView.repaint();
	}

	/**
	 * Determina se la simulazione deve continuare o no
	 * 
	 * @return true se ci sono ancora delle pedine attive
	 */
	public boolean isViable(Field field) {
		return stats.isViable(field);
	}

	/**
	 * Questa classe fornisce una rappresentazione grafica di una casella del
	 * campo da gioco.
	 */
	private class FieldView extends JPanel {
		private final int GRID_VIEW_SCALING_FACTOR = 6;

		private int gridWidth, gridHeight;
		private int xScale, yScale;
		Dimension size;
		private Graphics g;
		private Image fieldImage;

		public FieldView(int height, int width) {
			gridHeight = height;
			gridWidth = width;
			size = new Dimension(0, 0);
		}

		public Dimension getPreferredSize() {
			return new Dimension(gridWidth * GRID_VIEW_SCALING_FACTOR, gridHeight * GRID_VIEW_SCALING_FACTOR);
		}

		public void preparePaint() {
			if (!size.equals(getSize())) { // if the size has changed...
				size = getSize();
				fieldImage = fieldView.createImage(size.width, size.height);
				g = fieldImage.getGraphics();

				xScale = size.width / gridWidth;
				if (xScale < 1) {
					xScale = GRID_VIEW_SCALING_FACTOR;
				}
				yScale = size.height / gridHeight;
				if (yScale < 1) {
					yScale = GRID_VIEW_SCALING_FACTOR;
				}
			}
		}

		public void drawMark(int x, int y, Color color) {
			g.setColor(color);
			g.fillRect(x * xScale, y * yScale, xScale - 1, yScale - 1);
		}

		public void paintComponent(Graphics g) {
			if (fieldImage != null) {
				Dimension currentSize = getSize();
				if (size.equals(currentSize)) {
					g.drawImage(fieldImage, 0, 0, null);
				} else {
					// Rescale the previous image.
					g.drawImage(fieldImage, 0, 0, currentSize.width, currentSize.height, null);
				}
			}
		}
	}
}
