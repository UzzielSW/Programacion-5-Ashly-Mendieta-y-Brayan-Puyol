
// TODO: Pendiente - Implementacion grafica de la pila
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class PilaGraficaGUI extends JFrame {

  private JPanel panelPila;
  private JTextField inputField;
  private JButton pushButton, popButton;
  private Stack<Integer> pila;
  private int capacidad;

  public PilaGraficaGUI(int capacidad) {
    this.capacidad = capacidad;
    pila = new Stack<>();

    setTitle("Visualizador Gráfico de Pila");
    setSize(300, 500);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    panelPila = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        dibujarPila(g);
      }
    };

    add(panelPila, BorderLayout.CENTER);

    JPanel controlPanel = new JPanel();
    inputField = new JTextField(10);
    pushButton = new JButton("Push");
    popButton = new JButton("Pop");

    controlPanel.add(inputField);
    controlPanel.add(pushButton);
    controlPanel.add(popButton);
    add(controlPanel, BorderLayout.SOUTH);

    pushButton.addActionListener(e -> push());
    popButton.addActionListener(e -> pop());
  }

  private void push() {
    if (pila.size() < capacidad) {
      try {
        int valor = Integer.parseInt(inputField.getText());
        pila.push(valor);
        inputField.setText("");
        panelPila.repaint();
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Por favor, ingrese un número válido.");
      }
    } else {
      JOptionPane.showMessageDialog(this, "La pila está llena.");
    }
  }

  private void pop() {
    if (!pila.isEmpty()) {
      pila.pop();
      panelPila.repaint();
    } else {
      JOptionPane.showMessageDialog(this, "La pila está vacía.");
    }
  }

  private void dibujarPila(Graphics g) {
    int width = panelPila.getWidth();
    int height = panelPila.getHeight();
    int elementHeight = height / capacidad;

    // Dibujar el contorno de la pila
    g.setColor(Color.BLACK);
    g.drawRect(10, 18, width - 20, height - 20);

    Font font = new Font("Arial", Font.BOLD, height / 20);
    g.setFont(font);

    // Dibujar los elementos
    for (int i = 0; i < pila.size(); i++) {
      int y = height - (i + 1) * elementHeight;
      g.setColor(Color.DARK_GRAY);
      g.fillRect(14, y - 4, width - 27, elementHeight - 1);

      String texto = pila.get(i).toString();
      FontMetrics fm = g.getFontMetrics();

      int textWidth = fm.stringWidth(texto);
      int textHeight = fm.getHeight();

      int textX = width / 2 - textWidth / 2;
      int textY = y + elementHeight / 2 + textHeight / 5;

      // Dibujar un borde alrededor del texto para mayor legibilidad
      g.setColor(Color.BLACK);
      g.drawString(texto, textX - 1, textY - 1);
      g.drawString(texto, textX - 1, textY + 1);
      g.drawString(texto, textX + 1, textY - 1);
      g.drawString(texto, textX + 1, textY + 1);

      // Dibujar el texto principal
      g.setColor(Color.red);
      g.drawString(texto, textX, textY);
    }
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      PilaGraficaGUI gui = new PilaGraficaGUI(10);
      gui.setVisible(true);
    });
  }
}
