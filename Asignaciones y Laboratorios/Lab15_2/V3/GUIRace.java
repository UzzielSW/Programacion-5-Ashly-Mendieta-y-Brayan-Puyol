
/**
 * @Nota: 100
 * @Material: Programación V
 * @autor: Brayan Puyol, Ashly Mendieta
 * @date: 24/09/2024
 * @version: 3.0
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

public class GUIRace extends JFrame implements ActionListener {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			GUIRace frame = new GUIRace();
			frame.setVisible(true);
		});
	}

	private final JPanel jPanel, jPanelC, jPanelR, jPanelL;
	private final JMenuBar jMenuBar;
	private final JMenu jmAcciones;
	private final JMenuItem jmiRun;
	public JLabel[] jlCars, jlPosMeta, jlPosCar; // label car, label trofeo, label posicion car
	public String[] cars, trofeos;
	public int[] posCars;
	private final int N;
	private final JLabel jltitle1, jltitle2;

	public GUIRace() {
		// config frame
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e) {
			System.out.println("No se pudo configurar la apariencia");
		}

		setTitle("Lab 2 - RaceCar");
		setSize(930, 400);
		setLocationRelativeTo(null);
		setLayout(new FlowLayout(FlowLayout.CENTER));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		// init components
		cars = new String[] { "car-red.png", "car-fav.png", "car-yellow.png", "car-green.png" };
		trofeos = new String[] { "award1.png", "award2.png", "award3.png", "award4.png" };
		posCars = new int[] { 35, 98, 161, 224 }; // posicion en x de los carros
		N = cars.length;

		jlPosMeta = new JLabel[N];
		jlCars = new JLabel[N];
		jlPosCar = new JLabel[N];

		// Menu
		jmiRun = new JMenuItem("Run");
		jmiRun.setForeground(Color.decode("#a3b8cc"));
		jmiRun.setBackground(Color.decode("#383838"));
		jmiRun.setAccelerator(KeyStroke.getKeyStroke("control R"));
		jmiRun.addActionListener(this);

		jMenuBar = new JMenuBar();
		jMenuBar.setBackground(Color.decode("#383838"));
		jmAcciones = new JMenu("Actions");
		jmAcciones.setForeground(Color.decode("#d3e2e4"));
		jmAcciones.setBackground(Color.decode("#383838"));
		jmAcciones.setBorder(null);

		jmAcciones.add(jmiRun);
		jMenuBar.add(jmAcciones);
		jMenuBar.setLayout(new FlowLayout(FlowLayout.CENTER));
		setJMenuBar(jMenuBar);

		// Componentes Paneles
		double inc = 0.7; // escalado del tamaño de los iconos

		for (int i = 0; i < N; i++) {
			// components center
			jlPosCar[i] = new JLabel("", JLabel.CENTER);
			jlPosCar[i].setPreferredSize(new Dimension(170, 58));
			jlPosCar[i].setBorder(new LineBorder(Color.decode("#b8cfe5"), 1));

			// components right
			jlPosMeta[i] = new JLabel("", JLabel.CENTER);
			jlPosMeta[i].setPreferredSize(new Dimension(60, 58));
			jlPosMeta[i].setBorder(new LineBorder(Color.decode("#b8cfe5"), 1));

			// components left
			jlCars[i] = new JLabel();
			// jlCars[i].setBorder(new LineBorder(Color.RED, 1));
			ImageIcon imgCar = new ImageIcon(cars[i]);
			jlCars[i].setIcon(new ImageIcon(imgCar.getImage().getScaledInstance((int) (imgCar.getIconWidth() * inc),
					(int) (imgCar.getIconHeight() * inc), Image.SCALE_SMOOTH)));
			jlCars[i].setBounds(0, posCars[i], 73, 58);
		}

		// PANELES
		// Panel Principal
		jPanel = new JPanel();
		jPanel.setLayout(new BorderLayout());

		// Panel Left
		jPanelL = new PanelRace("pista.jpg", cars);
		jPanelL.setLayout(null);
		jPanelL.setPreferredSize(new Dimension(660, 316));

		// Panel Center
		jPanelC = new JPanel(new FlowLayout(FlowLayout.CENTER));
		jPanelC.setPreferredSize(new Dimension(180, 316));
		jPanelC.setBackground(Color.WHITE);

		inc = 0.3;
		jltitle1 = new JLabel("Posición", JLabel.CENTER);
		ImageIcon iconTitle1 = new ImageIcon("sprint.png");
		jltitle1.setIcon(new ImageIcon(iconTitle1.getImage().getScaledInstance((int) (iconTitle1.getIconWidth() * inc),
				(int) (iconTitle1.getIconHeight() * inc), Image.SCALE_SMOOTH)));
		jltitle1.setPreferredSize(new Dimension(180, 25));

		jPanelC.add(jltitle1);

		// Panel Right
		jPanelR = new JPanel(new FlowLayout(FlowLayout.CENTER));
		jPanelR.setPreferredSize(new Dimension(60, 316));
		jPanelR.setBackground(Color.WHITE);

		jltitle2 = new JLabel("", JLabel.CENTER);
		ImageIcon iconTitle2 = new ImageIcon("Rank.png");
		jltitle2.setIcon(new ImageIcon(iconTitle2.getImage().getScaledInstance((int) (iconTitle2.getIconWidth() * inc),
				(int) (iconTitle2.getIconHeight() * inc), Image.SCALE_SMOOTH)));
		jltitle2.setPreferredSize(new Dimension(60, 25));

		jPanelR.add(jltitle2);

		// componentes paneles
		for (int i = 0; i < N; i++) {
			// panel center
			jPanelC.add(jlPosCar[i]);

			// panel right
			jPanelR.add(jlPosMeta[i]);

			// panel left
			jPanelL.add(jlCars[i]);
		}

		jPanel.add(jPanelL, BorderLayout.WEST);
		jPanel.add(jPanelR, BorderLayout.EAST);
		jPanel.add(jPanelC, BorderLayout.CENTER);
		add(jPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jmiRun) {
			for (int i = 0; i < N; i++) {
				jlCars[i].setBounds(0, jlCars[i].getY(), jlCars[i].getWidth(), jlCars[i].getHeight());
				jlPosCar[i].setText("");
				jlPosMeta[i].setIcon(null);
			}

			jmiRun.setEnabled(false);
			Thread t = new RaceCar();
			t.start();
		}
	}

	class RaceCar extends Thread {

		@Override
		public void run() {
			Thread[] cars = new Thread[5];

			for (int i = 0; i < N; i++) {
				cars[i] = new Car(i);
			}

			for (int i = 0; i < N; i++) {
				cars[i].start();
			}
		}
	}

	class Car extends Thread {

		private final int finish;
		private final int n;
		private static int rank = 0;

		public Car(int i) {
			this.finish = 587;
			super.setName("Car " + i);
			this.n = i;
		}

		@Override
		public void run() {

			for (double i = 0; i <= finish; i++) {
				int time = (int) (Math.random() * 50);

				jlPosCar[n].setText(this.getName() + " , " + String.format("%.2f", (i / finish) * 100) + "%");
				jlCars[n].setBounds(jlCars[n].getX() + 1, jlCars[n].getY(), jlCars[n].getWidth(), jlCars[n].getHeight());

				try {
					Thread.sleep(time);
				} catch (InterruptedException err) {
				}
			}

			rank++;

			double inc = 0.4;
			ImageIcon imagen = new ImageIcon(trofeos[rank - 1]);

			jlPosMeta[n].setIcon(new ImageIcon(imagen.getImage().getScaledInstance((int) (imagen.getIconWidth() * inc),
					(int) (imagen.getIconHeight() * inc), Image.SCALE_SMOOTH)));
			jlPosCar[n].setText(jlPosCar[n].getText() + ", " + "finished! " + rank);

			if (rank == 4) {
				jmiRun.setEnabled(true);
				rank = 0;
			}
		}
	}
}

// Dibujar la imagen de fondo
class PanelRace extends JPanel {

	private final Image backgroundImage;

	public PanelRace(String srcBackground, String[] c) {
		backgroundImage = new ImageIcon(srcBackground).getImage();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (backgroundImage != null) {
			g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
		}
	}
}
