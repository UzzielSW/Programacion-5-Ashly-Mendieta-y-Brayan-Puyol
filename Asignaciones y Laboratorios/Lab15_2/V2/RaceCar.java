
/** *
 * @Nota: 100
 * @Material: Programación V
 * @autor: Brayan Puyol, Ashly Mendieta
 * @date: 24/09/2024
 * @version: 2.0
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

public class RaceCar extends JFrame implements ActionListener {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			RaceCar GUIRaceCar = new RaceCar("RaceCar Bar");
			GUIRaceCar.setVisible(true);
		});
	}

	public JButton jbRun;
	public JProgressBar[] jpBar;
	public JLabel[] jlCars, jlPosMeta;
	public JPanel jPanel, jPanelL, jPanelR, jPanelC;
	public final int N = 5;

	public RaceCar(String title) {

		// config frame
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e) {
			System.out.println("No se pudo configurar la apariencia");
		}

		super.setTitle(title);
		setLocationRelativeTo(null);
		setSize(270, 230);
		setLayout(new FlowLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		// init components
		jlCars = new JLabel[N];
		jpBar = new JProgressBar[N];
		jlPosMeta = new JLabel[N];
		jbRun = new JButton("Run Race (r)");

		for (int i = 0; i < N; i++) {
			jlCars[i] = new JLabel("Cars " + (i + 1), JLabel.CENTER);
			jlCars[i].setPreferredSize(new Dimension(70, 20));
			jlCars[i].setBorder(new LineBorder(Color.RED, 1));

			jpBar[i] = new JProgressBar(0, 100);
			jpBar[i].setPreferredSize(new Dimension(100, 20));
			jpBar[i].setEnabled(false);

			jlPosMeta[i] = new JLabel("", JLabel.CENTER);
			jlPosMeta[i].setBorder(new LineBorder(Color.RED, 1));
			jlPosMeta[i].setPreferredSize(new Dimension(65, 20));
		}

		jpBar[0].setForeground(Color.decode("#ffd04b"));
		jpBar[1].setForeground(Color.decode("#ff2c16"));
		jpBar[2].setForeground(Color.decode("#00a646"));
		jpBar[3].setForeground(Color.decode("#4c3cac"));
		jpBar[4].setForeground(Color.decode("#121212"));

		// PANELES
		jPanel = new JPanel();
		jPanel.setLayout(new BorderLayout());

		jPanelL = new JPanel();
		jPanelL.setLayout(new FlowLayout(FlowLayout.CENTER));
		jPanelL.setPreferredSize(new Dimension(70, 160));
		jPanelL.setBorder(new LineBorder(Color.BLACK, 1));

		jPanelC = new JPanel();
		jPanelC.setLayout(new FlowLayout(FlowLayout.CENTER));
		jPanelC.setPreferredSize(new Dimension(110, 160));
		jPanelC.setBorder(new LineBorder(Color.BLACK, 1));

		jPanelR = new JPanel();
		jPanelR.setLayout(new FlowLayout(FlowLayout.CENTER));
		jPanelR.setPreferredSize(new Dimension(65, 160));
		jPanelR.setBorder(new LineBorder(Color.BLACK, 1));

		// agregar components paneles
		for (int i = 0; i < N; i++) {
			jPanelL.add(jlCars[i]);
			jPanelL.add(Box.createRigidArea(new Dimension(20, 2)));
			jPanelC.add(jpBar[i]);
			jPanelC.add(Box.createRigidArea(new Dimension(20, 2)));
			jPanelR.add(jlPosMeta[i]);
			jPanelR.add(Box.createRigidArea(new Dimension(20, 2)));
		}

		jbRun.addActionListener(this);
		jbRun.setMnemonic('r');
		jPanel.add(jbRun, BorderLayout.SOUTH);

		jPanel.add(jPanelL, BorderLayout.WEST);
		jPanel.add(jPanelC, BorderLayout.CENTER);
		jPanel.add(jPanelR, BorderLayout.EAST);

		add(jPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jbRun) {
			jbRun.setEnabled(false);
			Thread t = new ButtonDoAction();
			t.start();
		}
	}

	class ButtonDoAction extends Thread {

		@Override
		public void run() {

			Thread[] cars = new Thread[N];

			for (int i = 0; i < N; i++) {
				cars[i] = new Car("Car " + (i + 1), i);
			}

			for (int i = 0; i < N; i++) {
				cars[i].start();
			}
		}
	}

	class Car extends Thread {

		private final int finish = 100;
		private final int n;
		private static int Rank = 0;

		public Car(String name, int n) {
			super.setName(name);
			this.n = n;
		}

		@Override
		public void run() {
			jlPosMeta[n].setText("");
			for (int i = 0; i <= finish; i++) {
				int time = (int) (Math.random() * 100);

				jpBar[n].setValue(i);
				jpBar[n].repaint();
				jlCars[n].setText(getName() + ", " + i + "%");

				try {
					Thread.sleep(time);
				} catch (InterruptedException err) {
				}
			}

			Rank++;
			jlPosMeta[n].setText("finished! " + Rank);

			if (Rank == 5) {
				jbRun.setEnabled(true);
				Rank = 0;
			}
		}
	}
}
