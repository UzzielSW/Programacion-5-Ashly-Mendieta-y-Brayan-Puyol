
/** *
 * @Nota: 100
 * @Material: Programación V
 * @autor: Brayan Puyol, Ashly Mendieta
 * @date: 24/09/2024
 * @version: 1.0
 */
public class Print {

    public static void main(String[] args) {
        PrintNumbers printNumbers = new PrintNumbers();

        Thread t1 = new Thread(printNumbers);

        t1.start();

        int time = Integer.parseInt("10000");

        try {
            Thread.sleep(time);
        } catch (InterruptedException m) {
        }

        printNumbers.stopPrinting();

        System.out.println("main() is ending");
    }
}
