
/**
 * @Nota: 100
 * @Material: Programación V
 * @autor: Brayan Puyol, Ashly Mendieta
 * @date: 24/09/2024
 * @version: 2.0
 */
import java.util.Arrays;

public class MTC {

	public int N;
	public static int hi = 0;
	double[][] A, B;
	Thread[] t;

	public MTC(double[][] A, int N) {
		this.N = N;
		this.A = A;

		B = new double[N][N];
		t = new Thread[N * N];
	}

	public double[][] calcular() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				Traspuesta calcTrasp = new Traspuesta(i, j);
				t[hi++] = new Thread(calcTrasp);
			}
		}

		iniciarHilos();

		return B;
	}

	public void iniciarHilos() {
		for (int i = 0; i < N * N; i++) {
			t[i].start();
			try {
				t[i].join();
			} catch (InterruptedException e) {
			}
		}
	}

	public void desplegar(double[][] T) {
		for (int i = 0; i < N; i++) {
			System.out.println(Arrays.toString(T[i]));
		}
		System.out.println("\n");
	}

	public class Traspuesta implements Runnable {

		int i, j;

		public Traspuesta(int i, int j) {
			this.i = i;
			this.j = j;
		}

		@Override
		public void run() {
			B[j][i] = A[i][j];
		}
	}

	public static double Rand(int min, int max) {
		return (double) (Math.random() * (max - min + 1)) + min;
	}

	public static void main(String[] args) {

		int N = 3;
		double[][] A = new double[N][N];
		double[][] T = new double[N][N];

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				A[i][j] = Rand(0, 10);
			}
		}

		MTC mult = new MTC(A, N);

		T = mult.calcular();
		System.out.println("\nMatriz A");
		mult.desplegar(A);
		System.out.println("Matriz Traspuesta de A: ");
		mult.desplegar(T);
	}
}
