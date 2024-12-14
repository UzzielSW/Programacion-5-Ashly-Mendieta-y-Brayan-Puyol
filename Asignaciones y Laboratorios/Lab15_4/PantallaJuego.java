
/** *
 * @Nota: 100
 * @Material: Programación V
 * @autor: Brayan Puyol, Ashly Mendieta
 * @date: 10/12/2024
 * @version: 1.0
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

public class PantallaJuego extends JPanel {
  private JuegoPrincipal juegoPrincipal;
  private Timer timer;
  private String nombreJugador;
  GameOver juego;
  JLabel jlPuntos;

  public PantallaJuego(JuegoPrincipal juegoPrincipal) {
    this.juegoPrincipal = juegoPrincipal;

    // Configurar diseño
    setLayout(new BorderLayout());
    setBackground(Color.WHITE);
  }

  public void iniciarJuego(String nombreJugador) {
    this.nombreJugador = nombreJugador;

    // Limpiar panel anterior
    removeAll();

    // Añadir elementos de juego
    juego = new GameOver();
    juego.setPreferredSize(new Dimension(700, 500));

    add(juego, BorderLayout.CENTER);

    JPanel jPanelN = new JPanel();
    jPanelN.setLayout(new GridLayout(1, 0));

    JLabel jlName = new JLabel("Jugador: " + nombreJugador, JLabel.CENTER);
    jlPuntos = new JLabel("Puntos: 0", JLabel.CENTER);

    jPanelN.add(jlName);
    jPanelN.add(jlPuntos);
    jPanelN.setBorder(BorderFactory.createTitledBorder("Record"));

    add(jPanelN, BorderLayout.NORTH);
    juegoPrincipal.pack();

    // Iniciar temporizador de juego
    timer = new Timer();
    timer.schedule(new TimerTask() {
      int tiempoRestante = 10; // segundos de juego

      @Override
      public void run() {
        while (juego.state) {
          if (tiempoRestante > 0) {
            // lógica de juego
            juego.Move();

            try {
              Thread.sleep(juego.velocidad);
            } catch (InterruptedException ie) {
            }

            juegoPrincipal.pack();
            jlPuntos.setText("Puntos: " + juego.getPuntos());
            tiempoRestante--;
          } else {
            // Finalizar juego
            juego.state = false;
            SwingUtilities.invokeLater(() -> mostrarResultados());
            cancel();
          }
        }
      }
    }, 0, 1000); // Ejecutar cada segundo
  }

  public static int Rand(int min, int max) {
    return (int) (Math.random() * (max - min + 1)) + min;
  }

  private void mostrarResultados() {
    // Detener cualquier proceso de juego
    if (timer != null) {
      timer.cancel();
    }

    // Mostrar diálogo de resultados
    int opcion = JOptionPane.showOptionDialog(
        this,
        "Jugador: " + nombreJugador + "\nPuntuación: " + juego.getPuntos(),
        "Resultados del Juego",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.INFORMATION_MESSAGE,
        null,
        new String[] { "Reintentar", "Menú Principal" },
        "Reintentar");

    // Manejar opciones
    if (opcion == JOptionPane.YES_OPTION) {
      // Reintentar
      iniciarJuego(nombreJugador);
    } else {
      // Volver al menú principal
      juegoPrincipal.mostrarMenuInicio();
    }
  }

  public class GameOver extends JPanel {
    int tamx;
    int tamy;
    int aux1;
    int aux2;

    private JButton btnObj;
    int punto = 0;
    final int velocidad = 1000;
    boolean state = true;

    public GameOver() {
      setLayout(null);
      setBackground(Color.WHITE);
      btnObj = new JButton();
      btnObj.setBorderPainted(false);
      btnObj.addMouseListener(new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
          punto += 10;
          Move();
        }
      });

      add(btnObj);
      setSize(700, 500);
      setVisible(true);
    }

    public void Move() {
      aux1 = Rand(0, 500);
      aux2 = Rand(0, 400);
      tamx = Rand(40, 80);
      tamy = Rand(40, 80);

      btnObj.setBackground(new Color(Rand(0, 255), Rand(0, 255), Rand(0, 255)));
    }

    public void paint(Graphics g) {
      super.paint(g);
      btnObj.setBounds(aux1, aux2, tamx, tamy);
      repaint();
    }

    public String getPuntos() {
      return "" + punto;
    }
  }
}
