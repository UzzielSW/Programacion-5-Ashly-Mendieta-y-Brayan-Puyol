
/**
 * @Nota: 100
 * @Material: Programación V
 * @date: 12/11/2024
 * @author: Brayan Puyol, Ashly Mendieta
 * @version: socket
 */
import java.io.Serializable;

public class Data implements Serializable {
	String accion;
	int n, speed;

	public Data(String accion, int n) {
		this.accion = accion;
		this.n = n;
	}

	public Data(String accion, int n, int speed) {
		this.accion = accion;
		this.n = n;
		this.speed = speed;
	}
}
