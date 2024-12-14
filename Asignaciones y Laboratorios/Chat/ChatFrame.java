
/** *
 * @Nota:100
 * @Material: Programación V
 * @autor: Brayan Puyol, Ashly Mendieta
 * @date: 10/12/2024
 * @version: 1.0
 */
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.*;
import javax.swing.text.DefaultCaret;

public class ChatFrame extends JFrame {

  protected JEditorPane output;
  protected JScrollPane scroll;
  protected JTextField input;
  public JPanel SPanel;

  public ChatFrame(String title) {
    super(title);

    setLayout(new BorderLayout());
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(500, 400);
    setLocationRelativeTo(null);
    setResizable(false);

    output = new JEditorPane();
    output.setPreferredSize(new Dimension(500, 360));
    output.setEditable(false);
    output.setFocusable(false);

    DefaultCaret caret = (DefaultCaret) output.getCaret();
    caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
    scroll = new JScrollPane(output, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    input = new JTextField();
    input.setPreferredSize(new Dimension(400, 40));
    SPanel = new JPanel();
    SPanel.add(input);

    add("Center", scroll);
    add("South", SPanel);
    input.setBorder(BorderFactory.createCompoundBorder(
        input.getBorder(),
        BorderFactory.createEmptyBorder(2, 2, 2, 2)));

    pack();
    setVisible(true);
    input.requestFocus();
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new ChatFrame("Chat Test"));
  }
}
