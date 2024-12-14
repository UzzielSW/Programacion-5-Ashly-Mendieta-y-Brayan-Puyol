
/**
 * @Nota: 100
 * @Material: Programación V
 * @autor: Brayan Puyol, Ashly Mendieta
 * @date: 24/09/2024
 * @version: 1.0
 */
public class RaceCar extends Thread {

	private final int finish;
	private final String name;

	public RaceCar(int finish, String name) {
		this.finish = finish;
		this.name = name;
	}

	@Override
	public void run() {
		for (int i = 1; i <= finish; i++) {
			int time = (int) (Math.random() * (5000 - 0 + 1000)) + 0;
			try {
				sleep(time);
			} catch (InterruptedException m) {
			}
			System.out.println(name + ": " + i);
		}
		System.out.println(name + " finished!");
	}
}
