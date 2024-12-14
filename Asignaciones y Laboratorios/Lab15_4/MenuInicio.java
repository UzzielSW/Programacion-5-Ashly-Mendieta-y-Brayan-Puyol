
/** *
 * @Nota: 100
 * @Material: Programación V
 * @autor: Brayan Puyol, Ashly Mendieta
 * @date: 10/12/2024
 * @version: 1.0
 */
import javax.swing.*;
import java.awt.*;

public class MenuInicio extends JPanel {
  private JuegoPrincipal juegoPrincipal;

  public MenuInicio(JuegoPrincipal juegoPrincipal) {
    this.juegoPrincipal = juegoPrincipal;

    // Configurar el diseño del menú
    setLayout(new BorderLayout());

    // Crear panel de botones
    JPanel panelBotones = new JPanel(new GridBagLayout());
    panelBotones.setBackground(Color.BLACK);

    // Botón de Iniciar Juego
    JButton botonIniciar = new JButton("Iniciar Juego [r]");
    botonIniciar.setMnemonic('r');
    botonIniciar.setPreferredSize(new Dimension(200, 50));
    botonIniciar.addActionListener(e -> juegoPrincipal.mostrarPantallaJuego());
    botonIniciar.setFocusable(false);

    // Botón de Salir
    JButton botonSalir = new JButton("Salir [s]");
    botonSalir.setMnemonic('s');
    botonSalir.setPreferredSize(new Dimension(200, 50));
    botonSalir.addActionListener(e -> System.exit(0));
    botonSalir.setFocusable(false);

    // Añadir botones al panel
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);

    gbc.gridx = 0;
    gbc.gridy = 0;
    panelBotones.add(botonIniciar, gbc);

    gbc.gridx = 0;
    gbc.gridy = 1;
    panelBotones.add(botonSalir, gbc);

    // Añadir título
    JLabel titulo = new JLabel("[ATRAPAME SI PUEDES]", SwingConstants.CENTER);
    titulo.setFont(new Font("Arial", Font.BOLD, 36));
    titulo.setForeground(Color.WHITE);

    // Configurar fondo
    setBackground(Color.BLACK);

    // Añadir componentes al panel
    add(titulo, BorderLayout.NORTH);
    add(panelBotones, BorderLayout.CENTER);
  }
}
