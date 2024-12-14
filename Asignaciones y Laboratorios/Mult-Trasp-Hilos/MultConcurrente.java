
/**
 * @Nota: 100
 * @Material: Programación V
 * @autor: Brayan Puyol, Ashly Mendieta
 * @date: 24/09/2024
 * @version: 1.0
 */
import java.util.Arrays;

public class MultConcurrente {

	public int N;
	public static int hi = 0;
	double[][] A, B, C;
	Thread[] t;

	public MultConcurrente(double[][] A, double[][] B, int N) {

		this.N = N;
		this.A = A;
		this.B = B;

		C = new double[N][N];
		t = new Thread[N * N];
	}

	public double[][] calcular() {
		for (int i = 0; i < N; i++) {

			for (int j = 0; j < N; j++) {
				ProductoInterno prodInt = new ProductoInterno(i, j);

				t[hi++] = new Thread(prodInt);
			}
		}

		iniciarHilos();

		return C;
	}

	public void iniciarHilos() {
		for (int i1 = 0; i1 < N * N; i1++) {
			t[i1].start();
			try {
				t[i1].join();
			} catch (InterruptedException e) {
			}
		}
	}

	public void desplegar(double[][] C) {
		for (int i1 = 0; i1 < N; i1++) {
			System.out.println(Arrays.toString(C[i1]));
		}

		System.out.println("\n");
	}

	// Inner class
	public class ProductoInterno implements Runnable {

		int i, j;

		public ProductoInterno(int i, int j) {
			this.i = i;
			this.j = j;
		}

		@Override
		public void run() {
			C[i][j] = 0.0;

			for (int k = 0; k < N; k++) {
				C[i][j] += A[i][k] * B[k][j];
			}
		}
	}
}
