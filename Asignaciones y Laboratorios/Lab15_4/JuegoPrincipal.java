
/** *
 * @Nota:100
 * @Material: Programación V
 * @autor: Brayan Puyol, Ashly Mendieta
 * @date: 10/12/2024
 * @version: 1.0
 */
import javax.swing.*;
import java.awt.*;

public class JuegoPrincipal extends JFrame {
  private MenuInicio menuInicio;
  private PantallaJuego pantallaJuego;
  private JPanel contenedorPrincipal;

  public JuegoPrincipal() {
    // Configuración inicial del Frame
    setTitle("Lab 4 - Game");
    setSize(800, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    // Crear contenedor principal
    contenedorPrincipal = new JPanel(new CardLayout());

    // Crear menú de inicio
    menuInicio = new MenuInicio(this);
    pantallaJuego = new PantallaJuego(this);

    // Añadir paneles al contenedor
    contenedorPrincipal.add(menuInicio, "MenuInicio");
    contenedorPrincipal.add(pantallaJuego, "PantallaJuego");

    add(contenedorPrincipal);
  }

  public void mostrarPantallaJuego() {
    // Cambiar a la pantalla de juego
    CardLayout cl = (CardLayout) (contenedorPrincipal.getLayout());
    cl.show(contenedorPrincipal, "PantallaJuego");

    // Solicitar nombre del jugador
    String nombreJugador = JOptionPane.showInputDialog("Ingrese su nombre:");

    // Iniciar el juego
    pantallaJuego.iniciarJuego(nombreJugador);
  }

  public void mostrarMenuInicio() {
    // Volver al menú de inicio
    CardLayout cl = (CardLayout) (contenedorPrincipal.getLayout());
    cl.show(contenedorPrincipal, "MenuInicio");
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      JuegoPrincipal juego = new JuegoPrincipal();
      juego.setVisible(true);
    });
  }
}
