
/** *
 * @Nota: 100
 * @Material: Programación V
 * @autor: Brayan Puyol, Ashly Mendieta
 * @date: 24/09/2024
 * @version: 1.0
 */
public class PrintNumbers implements Runnable {

    public boolean keepGoing;

    public PrintNumbers() {
        keepGoing = true;
    }

    public void stopPrinting() {
        keepGoing = false;
    }

    public void run() {
        int counter = 1;
        while (keepGoing) {
            System.out.println(counter++);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }
}
