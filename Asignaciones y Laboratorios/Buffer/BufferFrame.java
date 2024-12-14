
/** *
 * @Nota: 100
 * @Material: Programación V
 * @autor: Brayan Puyol, Ashly Mendieta
 * @date: 10/10/2024
 * @version: 2.0
 */
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

public class BufferFrame extends JFrame implements ActionListener {

    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> {
            BufferFrame bf = new BufferFrame();
        });
    }

    JLabel jlTitle, jlProductor, jlConsumidor, jlBuffer;
    JButton jbSuspend, jbResume, jbStop, jbRun;
    JTextField jtfProductor, jtfConsumidor, jtfBuffer;
    JPanel jPanel, jPanelC, jPanelS;

    Buffer buffer;
    Productor tProductor;
    Consumidor tConsumidor;

    // Constructor de la clase   
    public BufferFrame() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            System.out.println("No se pudo configurar la apariencia");
        }

        setTitle("Lab - Buffer");
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);

        initComponents();
    }

    public void initComponents() {
        jlTitle = new JLabel("- Productor/Consumidor con Buffer -", JLabel.CENTER);
        jlProductor = new JLabel("Productor: ", JLabel.RIGHT);
        jlConsumidor = new JLabel("Consumidor: ", JLabel.RIGHT);
        jlBuffer = new JLabel("Buffer: ", JLabel.RIGHT);

        jbRun = new JButton("Run(r)");
        jbStop = new JButton("Stop(s)");
        jbSuspend = new JButton("Suspender(w)");
        jbResume = new JButton("Reanudar(e)");

        double inc = 0.9; //tamaño de los iconos

        ImageIcon iconRun = new ImageIcon("run.png");
        jbRun.setIcon(new ImageIcon(iconRun.getImage().getScaledInstance((int) (iconRun.getIconWidth() * inc), (int) (iconRun.getIconHeight() * inc), Image.SCALE_SMOOTH)));

        ImageIcon iconStop = new ImageIcon("stop.png");
        jbStop.setIcon(new ImageIcon(iconStop.getImage().getScaledInstance((int) (iconStop.getIconWidth() * inc), (int) (iconStop.getIconHeight() * inc), Image.SCALE_SMOOTH)));

        ImageIcon iconSuspend = new ImageIcon("suspend.png");
        jbSuspend.setIcon(new ImageIcon(iconSuspend.getImage().getScaledInstance((int) (iconSuspend.getIconWidth() * inc), (int) (iconSuspend.getIconHeight() * inc), Image.SCALE_SMOOTH)));

        ImageIcon iconResume = new ImageIcon("resume.png");
        jbResume.setIcon(new ImageIcon(iconResume.getImage().getScaledInstance((int) (iconResume.getIconWidth() * inc), (int) (iconResume.getIconHeight() * inc), Image.SCALE_SMOOTH)));

        jbStop.setEnabled(false);
        jbSuspend.setEnabled(false);
        jbResume.setEnabled(false);

        jbRun.setFocusable(false);
        jbStop.setFocusable(false);
        jbSuspend.setFocusable(false);
        jbResume.setFocusable(false);

        jbRun.setMnemonic('r');
        jbStop.setMnemonic('s');
        jbSuspend.setMnemonic('w');
        jbResume.setMnemonic('e');

        jbRun.addActionListener(this);
        jbStop.addActionListener(this);
        jbSuspend.addActionListener(this);
        jbResume.addActionListener(this);

        jtfProductor = new JTextField(25);
        jtfConsumidor = new JTextField(25);
        jtfBuffer = new JTextField(25);

        jtfProductor.setEditable(false);
        jtfConsumidor.setEditable(false);
        jtfBuffer.setEditable(false);

        jtfProductor.setFocusable(false);
        jtfConsumidor.setFocusable(false);
        jtfBuffer.setFocusable(false);

        jtfProductor.setHorizontalAlignment(JTextField.CENTER);
        jtfConsumidor.setHorizontalAlignment(JTextField.CENTER);
        jtfBuffer.setHorizontalAlignment(JTextField.CENTER);

        // Paneles
        jPanel = new JPanel(new BorderLayout());
        jPanelC = new JPanel(new GridLayout(3, 2, 5, 5));
        jPanelC.setBorder(new EmptyBorder(15, 5, 10, 5));
        jPanelS = new JPanel();

        //situamos los componentes en los paneles                    
        jPanelC.setLayout(new GridLayout(3, 2, 5, 5));
        jPanelC.add(jlProductor);
        jPanelC.add(jtfProductor);
        jPanelC.add(jlBuffer);
        jPanelC.add(jtfBuffer);
        jPanelC.add(jlConsumidor);
        jPanelC.add(jtfConsumidor);

        jPanelS.add(jbRun);
        jPanelS.add(jbStop);
        jPanelS.add(jbSuspend);
        jPanelS.add(jbResume);

        jPanel.add(jlTitle, BorderLayout.NORTH);
        jPanel.add(jPanelC, BorderLayout.CENTER);
        jPanel.add(jPanelS, BorderLayout.SOUTH);

        add(jPanel);
        pack();
    }

    private void Start() {
        if (tProductor == null && tConsumidor == null) {
            jtfProductor.setText("");
            jtfConsumidor.setText("");
            jtfBuffer.setText("");

            jbRun.setEnabled(false);
            jbStop.setEnabled(true);
            jbSuspend.setEnabled(true);
            jbResume.setEnabled(false);

            Thread tmp = new Thread(() -> {
                buffer = new Buffer();
                tProductor = new Productor(buffer, 1);
                tConsumidor = new Consumidor(buffer, 1);
                tProductor.start();
                tConsumidor.start();
            });

            tmp.start();
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == jbRun) {
            Start();
        } else if (ae.getSource() == jbStop) {
            tProductor.Stop();
            tConsumidor.Stop();

            buffer = null;
            tProductor = null;
            tConsumidor = null;
        } else if (ae.getSource() == jbSuspend) {
            tProductor.suspender();
            tConsumidor.suspender();

            jbSuspend.setEnabled(false);
            jbResume.setEnabled(true);
        } else if (ae.getSource() == jbResume) {
            tProductor.reanudar();
            tConsumidor.reanudar();

            jbResume.setEnabled(false);
            jbSuspend.setEnabled(true);
        }
    }

    class Buffer {

        private final int capacidad = 10;
        private final int pila[] = new int[capacidad];
        private int puntero = -1;
        private boolean estaLleno = false;
        private boolean estaVacio = true;

        public synchronized int lee() {
            while (estaVacio) {
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }

            int num = pila[puntero];
            pila[puntero] = 0;
            puntero--;

            if (puntero < 0) {
                estaVacio = true;
            }

            estaLleno = false;

            jtfBuffer.setText(Arrays.toString(pila));
            notify();

            return num;
        }

        public synchronized void escribe(int num) {
            while (estaLleno) {
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }

            puntero++;
            pila[puntero] = num;

            if (puntero == capacidad - 1) {
                estaLleno = true;
            }

            estaVacio = false;

            jtfBuffer.setText(Arrays.toString(pila));
            notify();
        }
    }

    class Productor extends Thread {

        private Buffer buffer;
        private final int num;
        private boolean suspendido = false;

        public Productor(Buffer b, int n) {
            buffer = b;
            this.num = n;
        }

        public void suspender() {
            suspendido = true;
        }

        public synchronized void reanudar() {
            suspendido = false;
            notify();
        }

        public void Stop() {
            if (suspendido) {
                reanudar();
            }

            buffer = null;
            System.gc();
        }

        @Override
        public void run() {
            for (int i = 1; i <= 25; i++) {
                try {
                    synchronized (this) {
                        while (suspendido) {
                            wait();
                        }
                    }
                } catch (InterruptedException e) {
                }

                if (buffer != null) {
                    buffer.escribe(i);
                    jtfProductor.setText(" - Productor " + this.num + " pone: " + i);
                } else {
                    Stop();
                    break;
                }

                try {
                    sleep((int) (Math.random() * 700));
                } catch (InterruptedException e) {
                }
            }

            jtfProductor.setText(" - Productor " + this.num + " termino.");
        }
    }

    class Consumidor extends Thread {

        private Buffer buffer;
        private final int num;
        private boolean suspendido = false;

        public Consumidor(Buffer b, int n) {
            buffer = b;
            this.num = n;
        }

        public void suspender() {
            suspendido = true;
        }

        public synchronized void reanudar() {
            suspendido = false;
            notify();
        }

        public void Stop() {
            if (suspendido) {
                reanudar();
            }

            buffer = null;
            System.gc();
        }

        @Override
        public void run() {
            for (int i = 1; i <= 25; i++) {
                //si suspendemos el hilo...
                try {
                    sleep(100);
                    synchronized (this) {
                        while (suspendido) {
                            wait();
                        }
                    }
                } catch (InterruptedException e) {
                }

                if (buffer != null) {
                    int val = buffer.lee();
                    jtfConsumidor.setText(" - Consumidor " + this.num + " toma: " + val);
                } else {
                    Stop();
                    break;
                }

                try {
                    sleep((int) (Math.random() * 1000));
                } catch (InterruptedException e) {
                }
            }

            jtfConsumidor.setText(" - Consumidor " + this.num + " termino.");
            jbStop.setEnabled(false);
            jbSuspend.setEnabled(false);
            jbResume.setEnabled(false);
            jbRun.setEnabled(true);
            tProductor = null;
            tConsumidor = null;
            System.gc();
        }
    }

}
