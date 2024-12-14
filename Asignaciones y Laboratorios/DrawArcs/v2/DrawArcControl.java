
/**
 * @Nota: 100
 * @Material: Programación V
 * @date: 12/11/2024
 * @author: Brayan Puyol, Ashly Mendieta
 * @version: socket
 */
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicSliderUI;

public final class DrawArcControl extends JPanel {

	public static void main(String[] args) {
		DrawArcControl jpanel = new DrawArcControl("DrawArcControl Test");
		JFrame jf = new JFrame();
		jf.getContentPane().add(jpanel, BorderLayout.CENTER);
		jf.pack();
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setLocationRelativeTo(null);
		jf.setVisible(true);
	}

	JPanel speedPanel;
	JCheckBox jcReverse;
	CustomSlider customSlider;
	PowerRainbowButton jtbPower;

	public boolean isOn = false;

	public DrawArcControl(String title) {
		setLayout(new BorderLayout());

		TitledBorder border = new TitledBorder(title);
		border.setTitleColor(Color.blue);
		border.setBorder(new LineBorder(Color.black, 1));
		setBorder(border);
		initComponents();
		setPreferredSize(new Dimension(415, 120));
	}

	public void initComponents() {
		jtbPower = new PowerRainbowButton(25);

		speedPanel = new JPanel();
		speedPanel.setBorder(new LineBorder(Color.black, 1));
		speedPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.fill = GridBagConstraints.NONE; // No se expande
		gbc.gridx = 0; // Columna
		gbc.gridy = 0; // Fila
		gbc.weightx = 1.0; // Expandir el botón horizontalmente
		gbc.weighty = 1.0; // Expandir el botón verticalmente
		gbc.anchor = GridBagConstraints.CENTER;

		JLabel jlPower = new JLabel("On/Off");

		jcReverse = new JCheckBox("Reverse");
		jcReverse.setFocusPainted(false);
		jcReverse.setFocusable(false);

		customSlider = new CustomSlider(0, 100, 50);

		speedPanel.add(jtbPower, gbc);
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		speedPanel.setBorder(new LineBorder(Color.black, 1));
		speedPanel.add(jlPower, gbc);
		gbc.gridx = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		speedPanel.add(customSlider, gbc);
		gbc.gridx = 3;
		speedPanel.add(jcReverse, gbc);

		add(speedPanel, BorderLayout.NORTH);
	}

	public void Power() {
		isOn = !isOn;
	}

	class PowerRainbowButton extends JToggleButton {

		private float hue = 0;
		private final int size;
		private boolean isRainbowActive = false;
		private final Timer rainbowTimer;

		public PowerRainbowButton(int size) {
			this.size = size;
			setPreferredSize(new Dimension(size, size));
			setContentAreaFilled(false);
			setBorderPainted(false);
			setFocusPainted(false);

			rainbowTimer = new Timer(50, e -> {
				if (isRainbowActive) {
					hue += 0.01f;
					if (hue > 1) {
						hue = 0;
					}
					repaint();
				}
			});

			rainbowTimer.start();

			addActionListener(e -> {
				isRainbowActive = isSelected();
				if (!isRainbowActive) {
					hue = 0;
				}
				repaint();
			});
		}

		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g.create();
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			// Dibujar el círculo exterior
			g2d.setColor(isRainbowActive ? Color.getHSBColor(hue, 1, 1) : Color.GRAY);
			g2d.fillOval(0, 0, size - 1, size - 1);

			// Dibujar el símbolo de encendido
			g2d.setColor(Color.black);
			g2d.setStroke(new BasicStroke(size / 10f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			int centerX = size / 2;
			int centerY = size / 2;
			int radius = size / 4;
			g2d.drawArc(centerX - radius, centerY - radius, radius * 2, radius * 2, -60, 300);
			g2d.drawLine(centerX, size / 5, centerX, centerY);

			g2d.dispose();
			super.paintComponent(g);
		}
	}

	class CustomSlider extends JPanel {
		public JSlider slider;
		private JProgressBar progressBar;

		public CustomSlider(int min, int max, int value) {
			TitledBorder border = new TitledBorder("Custom Speed");
			border.setTitleColor(Color.decode("#c65f09"));
			border.setBorder(new LineBorder(Color.black, 1));
			setBorder(border);
			setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();

			slider = new JSlider(min, max, value) {
				@Override
				public void updateUI() {
					setUI(new CustomSliderUI(this));
				}
			};

			slider.setPaintTicks(true);
			slider.setMajorTickSpacing(50);
			slider.setMinorTickSpacing(10);
			slider.setPaintLabels(true);
			slider.setFocusable(false);

			progressBar = new JProgressBar(min, max) {

				@Override
				protected void paintComponent(Graphics g) {
					Graphics2D g2 = (Graphics2D) g.create();
					g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

					// Color de fondo
					g2.setColor(Color.BLACK);
					g2.fillRect(0, 0, getWidth(), getHeight());

					// Color de la barra de progreso
					g2.setColor(new Color(255, 140, 0)); // Naranja como el slider
					int width = (int) ((getWidth() - 4) * getPercentComplete());
					g2.fillRect(2, 2, width, getHeight() - 4);

					// Configurar la fuente más pequeña
					Font originalFont = g2.getFont();
					Font smallerFont = originalFont.deriveFont(10f); // Ajusta este valor según necesites
					g2.setFont(smallerFont);

					// Dibujar el texto del porcentaje
					String text = getString();
					FontMetrics fm = g2.getFontMetrics();
					int textWidth = fm.stringWidth(text);
					int textHeight = fm.getHeight();
					g2.setColor(Color.decode("#1d1d1d"));
					g2.drawString(text, (getWidth() - textWidth) / 2, (getHeight() + textHeight) / 2 - fm.getDescent());

					g2.dispose();
				}
			};

			progressBar.setPreferredSize(new Dimension(progressBar.getPreferredSize().width, 15)); // Altura reducida
			progressBar.setBorderPainted(false);
			progressBar.setStringPainted(true);
			progressBar.setForeground(new Color(255, 140, 0)); // Color naranja
			progressBar.setValue(slider.getValue());

			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.weightx = 1.0;
			gbc.insets = new Insets(0, 0, 5, 0);
			add(slider, gbc);

			gbc.gridy = 0;
			add(progressBar, gbc);

			slider.addChangeListener(e -> {
				progressBar.setValue(slider.getValue());
			});
		}

		public int getSliderValue() {
			return slider.getValue();
		}
	}

	class CustomSliderUI extends BasicSliderUI {

		public CustomSliderUI(JSlider slider) {
			super(slider);
		}

		@Override
		public void paintTicks(Graphics g) {
			super.paintTicks(g);
			g.setColor(Color.WHITE);
		}

		@Override
		public void paintTrack(Graphics g) {
			super.paintTrack(g);
			Rectangle trackBounds = trackRect;
			int cy = (trackBounds.height / 2) - 2;
			int w = trackBounds.width;

			g.setColor(new Color(255, 140, 0)); // Color naranja para la barra
			g.fillRect(trackRect.x, trackRect.y + cy, w, 4);
		}

		@Override
		public void paintThumb(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(new Color(255, 140, 0)); // Color naranja para el thumb
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			Rectangle thumbBounds = thumbRect;
			int w = thumbBounds.width;
			int h = thumbBounds.height;

			g2d.setColor(new Color(255, 140, 0)); // Color naranja para el thumb
			g2d.fillRoundRect(thumbBounds.x, thumbBounds.y, w - 1, h - 1, 10, 10);

			g2d.setColor(Color.WHITE);
			int triangleSize = 8;
			int[] xPoints = { thumbBounds.x + w / 2, thumbBounds.x + w / 2 - triangleSize / 2,
					thumbBounds.x + w / 2 + triangleSize / 2 };
			int[] yPoints = { thumbBounds.y + h / 2 - triangleSize / 2, thumbBounds.y + h / 2 + triangleSize / 2,
					thumbBounds.y + h / 2 + triangleSize / 2 };
			g2d.fillPolygon(xPoints, yPoints, 3);
		}
	}
}
