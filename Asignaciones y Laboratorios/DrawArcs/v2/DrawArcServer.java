
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultCaret;

public class DrawArcServer extends JFrame {

	public static void main(String[] args) {
		DrawArcServer frame = new DrawArcServer("Socket - DrawArcs - Server");

		SwingUtilities.invokeLater(() -> {
			frame.setVisible(true);
		});

		new Thread(() -> {
			frame.conect();
		}).start();
	}

	public DrawArc[] Arcs;
	public JPanel jPanel, jPanelC, jPanelS;
	public JTextArea jTALogs;
	private ServerSocket server;
	private Socket client;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;

	public DrawArcServer(String title) {
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
		Arcs = new DrawArc[4];
		for (int i = 0; i < 4; i++) {
			Arcs[i] = new DrawArc("DrawArc " + (i + 1));
		}

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
		jScroll.setPreferredSize(new Dimension(600, 75));

		jPanel = new JPanel(new BorderLayout());
		jPanelC = new JPanel(new GridLayout(2, 2, 10, 10));
		jPanelC.setBackground(Color.BLACK);
		jPanelC.setBorder(new LineBorder(Color.black, 5));
		jPanelS = new JPanel();
		TitledBorder border = new TitledBorder("Logs");
		jPanelS.setBorder(border);

		for (int i = 0; i < 4; i++) {
			jPanelC.add(Arcs[i]);
		}

		jPanelS.add(jScroll);

		jPanel.add(jPanelC, BorderLayout.CENTER);
		jPanel.add(jPanelS, BorderLayout.SOUTH);

		add(jPanel);
		pack();

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					if (client != null) {
						outputStream.close();
						inputStream.close();
						client.close();
					}

					if (server != null)
						server.close();

				} catch (IOException ex) {
				}
				System.exit(0);
			}
		});

		setVisible(true);
		setLocationRelativeTo(null);
	}

	public void conect() {
		try {
			server = new ServerSocket(8000);
			jTALogs.append("Servidor iniciado en el puerto 8000\n");
			Socket client = server.accept();
			inputStream = new ObjectInputStream(client.getInputStream());
			outputStream = new ObjectOutputStream(client.getOutputStream());
			jTALogs.append("Conexión establecida con el cliente\n");

			while (true) {
				jTALogs.append("Esperando a recibir datos del cliente...\n");
				Data data = (Data) inputStream.readObject();
				jTALogs.append("Datos recibidos del cliente: " + data.accion + ", " + data.n + "\n");
				Acciones(data);
				jTALogs.append("Acciones realizadas\n");
			}
		} catch (IOException e) {
			jTALogs.append("Cliente Desconectado\n");
			// jTALogs.append("Error al conectar con el cliente\n");
		} catch (ClassNotFoundException e) {
			jTALogs.append("error al hacer cast");
		}
	}

	private void Acciones(Data data) {
		if (data.accion.equals("run")) {
			if (!Arcs[data.n].isOn) {
				Arcs[data.n].Power();
				Arcs[data.n].arcSpeed(data.speed);
			}

			if (Arcs[data.n].isOn) {
				Arcs[data.n].timer.start();
			}
		} else if (data.accion.equals("stop")) {
			if (Arcs[data.n].isOn) {
				Arcs[data.n].Power();
			}
		} else if (data.accion.equals("suspendAll")) {
			for (int i = 0; i < 4; i++) {
				if (Arcs[i].isOn) {
					Arcs[i].timer.stop();
				}
			}
		} else if (data.accion.equals("resumeAll")) {
			for (int i = 0; i < 4; i++) {
				if (Arcs[i].isOn) {
					Arcs[i].timer.start();
				}
			}
		} else if (data.accion.equals("reverse")) {
			Arcs[data.n].reverse();
		} else if (data.accion.equals("setSpeed")) {
			Arcs[data.n].arcSpeed(data.speed);
		}
	}
}
