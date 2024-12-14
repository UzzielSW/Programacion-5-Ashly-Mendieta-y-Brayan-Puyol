
/** *
 * @Nota: 100
 * @Material: Programación V
 * @autor: Brayan Puyol, Ashly Mendieta
 * @date: 10/10/2024
 * @version: 2.0
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

public class DrawArcs extends JFrame implements ActionListener {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DrawArcs frame = new DrawArcs("Lab - DrawArcs");
            frame.setVisible(true);
        });
    }

    public DrawArc[] Arcs;
    public JPanel jPanel, jPanelC, jPanelS;
    public JButton jbRunAll, jbStopAll, jbResumeAll, jbSuspendAll;

    public DrawArcs(String title) {
        super(title);

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            System.out.println("No se pudo configurar la apariencia");
        }
        super.setTitle(title);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // init components
        Arcs = new DrawArc[4];
        for (int i = 0; i < 4; i++) {
            Arcs[i] = new DrawArc();
        }

        jbRunAll = new JButton("Run All [r]");
        jbStopAll = new JButton("Stop All [s]");
        jbSuspendAll = new JButton("Suspend All [p]");
        jbResumeAll = new JButton("Resume All [e]");

        jbRunAll.setBackground(Color.decode("#121212"));
        jbStopAll.setBackground(Color.decode("#121212"));
        jbSuspendAll.setBackground(Color.decode("#121212"));
        jbResumeAll.setBackground(Color.decode("#121212"));

        jbRunAll.setForeground(Color.white);
        jbStopAll.setForeground(Color.white);
        jbSuspendAll.setForeground(Color.white);
        jbResumeAll.setForeground(Color.white);

        jbRunAll.setFocusable(false);
        jbStopAll.setFocusable(false);
        jbSuspendAll.setFocusable(false);
        jbResumeAll.setFocusable(false);

        jbRunAll.setBorderPainted(false);
        jbStopAll.setBorderPainted(false);
        jbSuspendAll.setBorderPainted(false);
        jbResumeAll.setBorderPainted(false);

        // jbRunAll.setContentAreaFilled(false);
        // jbStopAll.setContentAreaFilled(false);
        // jbSuspendAll.setContentAreaFilled(false);
        // jbResumeAll.setContentAreaFilled(false);

        jbRunAll.registerKeyboardAction(this, KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK), JComponent.WHEN_IN_FOCUSED_WINDOW);
        jbStopAll.registerKeyboardAction(this, KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK), JComponent.WHEN_IN_FOCUSED_WINDOW);
        jbSuspendAll.registerKeyboardAction(this, KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK), JComponent.WHEN_IN_FOCUSED_WINDOW);
        jbResumeAll.registerKeyboardAction(this, KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK), JComponent.WHEN_IN_FOCUSED_WINDOW);

        double inc = 0.12; //tamaño de los iconos

        ImageIcon iconRun = new ImageIcon("play.png");
        jbRunAll.setIcon(new ImageIcon(iconRun.getImage().getScaledInstance((int) (iconRun.getIconWidth() * inc), (int) (iconRun.getIconHeight() * inc), Image.SCALE_SMOOTH)));

        ImageIcon iconStop = new ImageIcon("stop.png");
        jbStopAll.setIcon(new ImageIcon(iconStop.getImage().getScaledInstance((int) (iconStop.getIconWidth() * inc), (int) (iconStop.getIconHeight() * inc), Image.SCALE_SMOOTH)));

        ImageIcon iconSuspend = new ImageIcon("suspend.png");
        jbSuspendAll.setIcon(new ImageIcon(iconSuspend.getImage().getScaledInstance((int) (iconSuspend.getIconWidth() * inc), (int) (iconSuspend.getIconHeight() * inc), Image.SCALE_SMOOTH)));

        ImageIcon iconResume = new ImageIcon("resume.png");
        jbResumeAll.setIcon(new ImageIcon(iconResume.getImage().getScaledInstance((int) (iconResume.getIconWidth() * inc), (int) (iconResume.getIconHeight() * inc), Image.SCALE_SMOOTH)));
        // btnRun.setMnemonic('p');

        jbRunAll.addActionListener(this);
        jbStopAll.addActionListener(this);
        jbSuspendAll.addActionListener(this);
        jbResumeAll.addActionListener(this);

        jPanel = new JPanel(new BorderLayout());
        jPanelC = new JPanel(new GridLayout(2, 2, 10, 10));
        jPanelC.setBackground(Color.BLACK);
        jPanelC.setBorder(new LineBorder(Color.black, 5));
        jPanelS = new JPanel();
        jPanelS.setBackground(Color.decode("#383838"));

        for (int i = 0; i < 4; i++) {
            jPanelC.add(Arcs[i]);
        }

        jPanelS.add(jbRunAll);
        jPanelS.add(jbStopAll);
        jPanelS.add(jbSuspendAll);
        jPanelS.add(jbResumeAll);

        jPanel.add(jPanelC, BorderLayout.CENTER);
        jPanel.add(jPanelS, BorderLayout.SOUTH);

        add(jPanel);
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jbRunAll) {
            for (int i = 0; i < 4; i++) {
                if (!Arcs[i].isOn) {
                    Arcs[i].jtbPower.doClick();
                } else if (Arcs[i].isOn) {
                    Arcs[i].timer.start();
                }
            }
        } else if (e.getSource() == jbStopAll) {
            for (int i = 0; i < 4; i++) {
                if (Arcs[i].isOn) {
                    Arcs[i].isOn = false;
                    Arcs[i].jtbPower.doClick();
                    Arcs[i].Power();
                }
            }
        } else if (e.getSource() == jbSuspendAll) {
            for (int i = 0; i < 4; i++) {
                if (Arcs[i].isOn) {
                    Arcs[i].timer.stop();
                }
            }
        } else if (e.getSource() == jbResumeAll) {
            for (int i = 0; i < 4; i++) {
                if (Arcs[i].isOn) {
                    Arcs[i].timer.start();
                }
            }
        }
    }
}
