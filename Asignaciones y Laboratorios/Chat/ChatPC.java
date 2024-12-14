
/** *
 * @Nota:100
 * @Material: Programación V
 * @autor: Brayan Puyol, Ashly Mendieta
 * @date: 10/12/2024
 * @version: 1.0
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChatPC {
  private static JTextArea textArea2;
  private static JButton jbtnSend;
  static ChatFrame V1, V3;

  private static final Object lock = new Object(); // Objeto de bloqueo para sincronización

  public static void main(String[] args) {
    // VENTANA 1
    V1 = new ChatFrame("Productor");
    V1.setLocation(33, 83);
    jbtnSend = new JButton("Enviar");
    jbtnSend.setMnemonic('e');
    jbtnSend.setPreferredSize(new Dimension(100, 40));
    V1.SPanel.add(jbtnSend);

    jbtnSend.addActionListener(e -> {
      jbtnSend.setEnabled(false);
      String[] lines = V1.output.getText().split("\n");
      new LineThread(lines).start();
    });

    V1.input.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          V1.output.setText(V1.output.getText() + V1.input.getText() + "\n");
          V1.input.setText("");
        }
      }
    });

    V1.pack();

    JFrame ventana2 = new JFrame("Intermediario");
    ventana2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    ventana2.setLayout(new FlowLayout());
    ventana2.setLocation(608, 86);

    textArea2 = new JTextArea(2, 46);
    textArea2.setEditable(false);
    ventana2.add(textArea2);
    ventana2.pack();
    ventana2.setVisible(true);

    // VENTANA 3
    V3 = new ChatFrame("Consumidor");
    V3.setLocation(606, 182);
    JButton jbtnClear = new JButton("Limpiar");
    jbtnClear.setMnemonic('l');
    jbtnClear.setPreferredSize(new Dimension(100, 40));
    V3.SPanel.add(jbtnClear);

    V3.SPanel.remove(V3.input);

    jbtnClear.addActionListener(e -> V3.output.setText(""));

    V3.pack();
    V1.input.requestFocusInWindow();
  }

  private static class LineThread extends Thread {
    private final String[] lines;

    public LineThread(String[] lines) {
      this.lines = lines;
    }

    @Override
    public void run() {
      synchronized (lock) {
        for (String line : lines) {
          try {
            SwingUtilities.invokeLater(() -> textArea2.setText(line));

            lock.wait(3000); // Espera de 3 segundos

            SwingUtilities.invokeLater(() -> V3.output.setText(V3.output.getText() + line + "\n"));

            SwingUtilities.invokeLater(() -> V1.output.setText(V1.output.getText().replaceFirst(line, "").trim()));

            lock.notify(); // Notifica para que la siguiente línea pueda procesarse
          } catch (InterruptedException e) {
          }
        }

        SwingUtilities.invokeLater(new Runnable() {
          @Override
          public void run() {
            textArea2.setText("");
            jbtnSend.setEnabled(true);
          }
        });
      }
    }
  }
}
