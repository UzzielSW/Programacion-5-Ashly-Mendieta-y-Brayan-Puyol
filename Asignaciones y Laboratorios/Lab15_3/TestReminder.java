
/** *
 * @Nota: 100
 * @Material: Programación V
 * @autor: Brayan Puyol, Ashly Mendieta
 * @date: 24/09/2024
 * @version: 1.0
 */
import java.util.Timer;

public class TestReminder {

    public static void main(String[] args) {
        Timer timer = new Timer();
        Reminder reminder1 = new Reminder("Mensaje 1");
        Reminder reminder2 = new Reminder("Mensaje 2");
        Reminder reminder3 = new Reminder("Mensaje 3");

        timer.schedule(reminder1, 0);
        timer.schedule(reminder2, 5000);
        timer.schedule(reminder3, 10000);
    }
}
