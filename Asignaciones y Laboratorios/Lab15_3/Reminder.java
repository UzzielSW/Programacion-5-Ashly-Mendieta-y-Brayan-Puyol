
/** *
 * @Nota: 100
 * @Material: Programación V
 * @autor: Brayan Puyol, Ashly Mendieta
 * @date: 24/09/2024
 * @version: 1.0
 */
import java.util.TimerTask;

public class Reminder extends TimerTask {

    String message;

    public Reminder(String message) {
        this.message = message;
    }

    public void run() {
        System.out.println(message);
    }
}
