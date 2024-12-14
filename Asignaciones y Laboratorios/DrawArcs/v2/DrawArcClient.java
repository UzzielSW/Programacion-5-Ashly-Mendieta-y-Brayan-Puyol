
/**
 * @Nota: 100
 * @Material: Programación V
 * @date: 12/11/2024
 * @author: Brayan Puyol, Ashly Mendieta
 * @version: socket
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultCaret;

public class DrawArcClient extends JFrame implements ActionListener {

	public static void main(String[] args) {
		DrawArcClient frame = new DrawArcClient("Socket - DrawArcs - Client");

		SwingUtilities.invokeLater(() -> {
			frame.setVisible(true);
		});

		new Thread(frame::conect).start();
	}

	public DrawArcControl[] Arcs;
	public JPanel jPanel, jPanelN, jPanelC, jPanelS;
	public JButton jbRunAll, jbStopAll, jbResumeAll, jbSuspendAll;
	public JTextArea jTALogs;
	private Socket socket;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;

	public DrawArcClient(String title) {
		super(title);

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e) {
			System.out.println("No se pudo configurar la apariencia");
		}
		super.setTitle(title);
		setLayout(new FlowLayout());
		setResizable(false);

		// init components
		Arcs = new DrawArcControl[4];
		for (int i = 0; i < 4; i++) {
			Arcs[i] = new DrawArcControl("DrawArc " + (i + 1));
			int finalI = i;

			Arcs[i].jtbPower.addActionListener(e -> {
				try {
					if (Arcs[finalI].isOn) {
						Arcs[finalI].isOn = false;
						outputStream.writeObject(new Data("stop", finalI, Arcs[finalI].customSlider.slider.getValue()));
						jTALogs.append("Accion stop[" + finalI + "] enviada\n");
					} else {
						Arcs[finalI].isOn = true;
						outputStream.writeObject(new Data("run", finalI, Arcs[finalI].customSlider.slider.getValue()));
						jTALogs.append("Accion run[" + finalI + "] enviada\n");
					}

					outputStream.flush();

				} catch (IOException ex) {
					jTALogs.append("Error al enviar la accion run\n");
				}
			});

			Arcs[i].jcReverse.addActionListener(e -> {
				try {
					outputStream.writeObject(new Data("reverse", finalI));
					outputStream.flush();
					jTALogs.append("Accion reverse[" + finalI + "] enviada\n");
				} catch (IOException ex) {
					jTALogs.append("Error al enviar la accion reverse\n");
				}
			});

			Arcs[i].customSlider.slider.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					try {
						outputStream.writeObject(new Data("setSpeed", finalI, Arcs[finalI].customSlider.slider.getValue()));
						outputStream.flush();
						jTALogs.append("Accion setSpeed[" + finalI + "] enviada\n");
					} catch (IOException ex) {
						jTALogs.append("Error al enviar la accion setSpeed\n");
					}
				}
			});
		}

		jbRunAll = new JButton("Run All [r]");
		jbStopAll = new JButton("Stop All [s]");
		jbSuspendAll = new JButton("Suspend All [p]");
		jbResumeAll = new JButton("Resume All [e]");

		jbRunAll.setFocusable(false);
		jbStopAll.setFocusable(false);
		jbSuspendAll.setFocusable(false);
		jbResumeAll.setFocusable(false);

		jbRunAll.registerKeyboardAction(this, KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK),
				JComponent.WHEN_IN_FOCUSED_WINDOW);
		jbStopAll.registerKeyboardAction(this, KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK),
				JComponent.WHEN_IN_FOCUSED_WINDOW);
		jbSuspendAll.registerKeyboardAction(this, KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK),
				JComponent.WHEN_IN_FOCUSED_WINDOW);
		jbResumeAll.registerKeyboardAction(this, KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK),
				JComponent.WHEN_IN_FOCUSED_WINDOW);

		double inc = 0.12; // tamaño de los iconos

		ImageIcon iconRun = new ImageIcon("play.png");
		jbRunAll.setIcon(new ImageIcon(iconRun.getImage().getScaledInstance((int) (iconRun.getIconWidth() * inc),
				(int) (iconRun.getIconHeight() * inc), Image.SCALE_SMOOTH)));

		ImageIcon iconStop = new ImageIcon("stop.png");
		jbStopAll.setIcon(new ImageIcon(iconStop.getImage().getScaledInstance((int) (iconStop.getIconWidth() * inc),
				(int) (iconStop.getIconHeight() * inc), Image.SCALE_SMOOTH)));

		ImageIcon iconSuspend = new ImageIcon("suspend.png");
		jbSuspendAll
				.setIcon(new ImageIcon(iconSuspend.getImage().getScaledInstance((int) (iconSuspend.getIconWidth() * inc),
						(int) (iconSuspend.getIconHeight() * inc), Image.SCALE_SMOOTH)));

		ImageIcon iconResume = new ImageIcon("resume.png");
		jbResumeAll.setIcon(new ImageIcon(iconResume.getImage().getScaledInstance((int) (iconResume.getIconWidth() * inc),
				(int) (iconResume.getIconHeight() * inc), Image.SCALE_SMOOTH)));

		jbRunAll.addActionListener(this);
		jbStopAll.addActionListener(this);
		jbSuspendAll.addActionListener(this);
		jbResumeAll.addActionListener(this);

		jTALogs = new JTextArea();
		Font font = new Font("Arial", Font.PLAIN, 14);
		jTALogs.setFont(font);
		jTALogs.setEditable(false);
		jTALogs.setFocusable(false);
		DefaultCaret caret = (DefaultCaret) jTALogs.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		JScrollPane jScroll = new JScrollPane(jTALogs, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jScroll.setViewportView(jTALogs);
		jScroll.setPreferredSize(new Dimension(850, 75));

		jPanel = new JPanel(new BorderLayout());

		jPanelN = new JPanel(new GridLayout(2, 2, 10, 10));
		jPanelN.setBorder(new LineBorder(Color.black, 5));

		jPanelC = new JPanel();

		jPanelS = new JPanel();
		TitledBorder border = new TitledBorder("Logs");
		jPanelS.setBorder(border);

		for (int i = 0; i < 4; i++) {
			jPanelN.add(Arcs[i]);
		}

		jPanelC.add(jbRunAll);
		jPanelC.add(jbStopAll);
		jPanelC.add(jbSuspendAll);
		jPanelC.add(jbResumeAll);
		jPanelS.add(jScroll);

		jPanel.add(jPanelN, BorderLayout.NORTH);
		jPanel.add(jPanelC, BorderLayout.CENTER);
		jPanel.add(jPanelS, BorderLayout.SOUTH);

		add(jPanel);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					if (socket != null) {
						outputStream.close();
						inputStream.close();
						socket.close();
					}
				} catch (IOException ex) {
				} finally {
					System.exit(0);
				}
			}
		});

		pack();
		setLocationRelativeTo(null);
	}

	public void conect() {
		try {
			socket = new Socket("localhost", 8000);
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			inputStream = new ObjectInputStream(socket.getInputStream());
			jTALogs.append("Conectado al servidor\n");
		} catch (IOException ex) {
			jTALogs.append(ex.toString() + '\n');
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jbRunAll) {
			for (int i = 0; i < 4; i++) {
				if (!Arcs[i].isOn)
					Arcs[i].jtbPower.doClick();
			}
		} else if (e.getSource() == jbStopAll) {
			for (int i = 0; i < 4; i++) {
				if (Arcs[i].isOn) {
					Arcs[i].jtbPower.doClick();
				}
			}
		} else if (e.getSource() == jbSuspendAll) {
			try {
				outputStream.writeObject(new Data("suspendAll", -1));
				outputStream.flush();
				jTALogs.append("Accion suspendAll enviada\n");
			} catch (IOException ex) {
				jTALogs.append("Error al enviar la accion suspendAll\n");
			}
		} else if (e.getSource() == jbResumeAll) {
			try {
				outputStream.writeObject(new Data("resumeAll", -1));
				outputStream.flush();
				jTALogs.append("Accion resumeAll enviada\n");
			} catch (IOException ex) {
				jTALogs.append("Error al enviar la accion resumeAll\n");
			}
		}
	}
}
