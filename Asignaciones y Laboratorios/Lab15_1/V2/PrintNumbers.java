
/** *
 * @Nota: 100
 * @Material: Programación V
 * @autor: Brayan Puyol, Ashly Mendieta
 * @date: 24/09/2024
 * @version: 2.0
 */
public class PrintNumbers extends Thread {

    public boolean keepGoing, suspended;
    public Print gui;
    private final int time;

    public PrintNumbers(Print g, int t) {
        time = t;
        keepGoing = true;
        suspended = false;
        gui = g;
    }

    public synchronized void stopPrinting() {
        keepGoing = false;
        suspended = false;
        notify();
        gui.getArea().append("Deteniendo\n");
    }

    public synchronized void suspendPrinting() {
        suspended = true;
    }

    public synchronized void resumePrinting() {
        suspended = false;
        notify();
    }

    @Override
    public void run() {
        int counter = 1;

        while (keepGoing && counter <= time) {
            gui.getArea().append((counter++) + "\n");

            try {
                sleep(1000);
                synchronized (this) {
                    if (suspended) {
                        wait();
                    }
                }
            } catch (InterruptedException ignored) {
            }
        }

        gui.getArea().append("Proceso Terminado\n");
        gui.getBtnStop().setEnabled(false);
        gui.getBtnSuspend().setEnabled(false);
        gui.getBtnResume().setEnabled(false);
        gui.getBtnClear().setEnabled(true);
        gui.getBtnRun().setEnabled(true);
    }
}
