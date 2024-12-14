
/** *
 * @Nota: 100
 * @Material: Programación V
 * @autor: Brayan Puyol, Ashly Mendieta
 * @date: 24/09/2024
 * @version: 2.0
 */
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;

public class Print extends JFrame implements ActionListener {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Print app = new Print();
        });
    }

    private JTextArea JTArea;
    private JButton btnRun, btnClear, btnStop, btnSuspend, btnResume;
    private JTextField JTField;
    private PrintNumbers Proceso;

    public Print() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            System.out.println("No se pudo configurar la apariencia");
        }

        initComponents();
        configJFrame();
    }

    private void initComponents() {
        // Paneles
        JPanel Jpanel = new JPanel(new BorderLayout());
        EmptyBorder padding = new EmptyBorder(5, 5, 5, 5);
        Jpanel.setBorder(padding);

        JPanel jpanelS = new JPanel();

        // Componentes de GUI
        JTArea = new JTextArea("", 6, 30);
        JTArea.setEditable(false);
        DefaultCaret caret = (DefaultCaret) JTArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        JScrollPane JScroll = new JScrollPane(JTArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JScroll.setViewportView(JTArea);

        JTField = new JTextField(10);
        JTField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    Iniciar();
                }
            }
        });

        JLabel label = new JLabel("Segundos:");

        double inc = 1; // tamaño de los iconos

        btnRun = new JButton("Play (p)");
        ImageIcon iconRun = new ImageIcon("play.png");
        btnRun.setIcon(new ImageIcon(iconRun.getImage().getScaledInstance((int) (iconRun.getIconWidth() * inc),
                (int) (iconRun.getIconHeight() * inc), Image.SCALE_SMOOTH)));
        btnRun.setMnemonic('p');
        btnRun.addActionListener(this);

        btnSuspend = new JButton("Suspend (s)");
        ImageIcon iconSuspend = new ImageIcon("suspend.png");
        btnSuspend.setIcon(
                new ImageIcon(iconSuspend.getImage().getScaledInstance((int) (iconSuspend.getIconWidth() * inc),
                        (int) (iconSuspend.getIconHeight() * inc), Image.SCALE_SMOOTH)));
        btnSuspend.setMnemonic('s');
        btnSuspend.addActionListener(this);
        btnSuspend.setEnabled(false);

        btnResume = new JButton("Resume (r)");
        ImageIcon iconResume = new ImageIcon("resume.png");
        btnResume.setIcon(new ImageIcon(iconResume.getImage().getScaledInstance((int) (iconResume.getIconWidth() * inc),
                (int) (iconResume.getIconHeight() * inc), Image.SCALE_SMOOTH)));
        btnResume.setMnemonic('r');
        btnResume.addActionListener(this);
        btnResume.setEnabled(false);

        btnStop = new JButton("Stop (d)");
        ImageIcon iconStop = new ImageIcon("stop.png");
        btnStop.setIcon(new ImageIcon(iconStop.getImage().getScaledInstance((int) (iconStop.getIconWidth() * inc),
                (int) (iconStop.getIconHeight() * inc), Image.SCALE_SMOOTH)));
        btnStop.setMnemonic('d');
        btnStop.addActionListener(this);
        btnStop.setEnabled(false);

        btnClear = new JButton();
        ImageIcon iconClear = new ImageIcon("clear.png");
        btnClear.setIcon(new ImageIcon(iconClear.getImage().getScaledInstance((int) (iconClear.getIconWidth() * inc),
                (int) (iconClear.getIconHeight() * inc), Image.SCALE_SMOOTH)));
        btnClear.setText("Clear (c)");
        btnClear.setMnemonic('c');
        btnClear.addActionListener(this);
        btnClear.setEnabled(false);

        // Layouts
        Jpanel.add(JScroll, BorderLayout.CENTER);

        jpanelS.add(label);
        jpanelS.add(JTField);
        jpanelS.add(btnRun);
        jpanelS.add(btnSuspend);
        jpanelS.add(btnResume);
        jpanelS.add(btnStop);
        jpanelS.add(btnClear);
        Jpanel.add(jpanelS, BorderLayout.SOUTH);
        add(Jpanel);
    }

    private void configJFrame() {
        setTitle("Lab 1 - Print Numbers");
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        JTField.requestFocusInWindow();
    }

    private void Iniciar() {
        JTArea.setText("");

        Thread tmp = new Thread(() -> {
            int time = 0;
            String text = JTField.getText();

            try {
                time = Integer.parseInt(text);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Ingrese un numero");
                JTField.setText("");
            }

            if (time <= 0) {
                JOptionPane.showMessageDialog(this, "Ingrese un valor mayor que cero");
                JTField.setText("");
            } else {
                btnRun.setEnabled(false);
                btnClear.setEnabled(false);
                btnSuspend.setEnabled(true);
                btnStop.setEnabled(true);
                Proceso = new PrintNumbers(this, time);
                Proceso.start();
            }
        });

        tmp.start();
    }

    private void Clear() {
        JTField.setText("");
        JTArea.setText("");
        JTField.requestFocusInWindow();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnRun) {
            Iniciar();
        } else if (e.getSource() == btnStop) {
            Proceso.stopPrinting();
            try {
                Proceso.join();
            } catch (InterruptedException ignored) {
            }
            System.gc();
        } else if (e.getSource() == btnSuspend) {
            Proceso.suspendPrinting();
            btnSuspend.setEnabled(false);
            btnResume.setEnabled(true);
            btnStop.setEnabled(true);
        } else if (e.getSource() == btnResume) {
            Proceso.resumePrinting();
            btnResume.setEnabled(false);
            btnSuspend.setEnabled(true);
            btnStop.setEnabled(true);
        } else if (e.getSource() == btnClear) {
            Clear();
            btnClear.setEnabled(false);
        }
    }

    // GETTERS
    public JTextArea getArea() {
        return JTArea;
    }

    public JButton getBtnRun() {
        return btnRun;
    }

    public JButton getBtnClear() {
        return btnClear;
    }

    public JButton getBtnStop() {
        return btnStop;
    }

    public JButton getBtnSuspend() {
        return btnSuspend;
    }

    public JButton getBtnResume() {
        return btnResume;
    }
}
