
/**
 * @Nota: 100
 * @Material: Programación V
 * @autor: Brayan Puyol, Ashly Mendieta
 * @date: 24/09/2024
 * @version: 1.0
 */
import java.util.Arrays;

public class MatrizTraspuestaConHilos {
	private static final int FILAS = 4;
	private static final int COLUMNAS = 3;

	public static int Rand(int min, int max) {
		return (int) (Math.random() * (max - min + 1)) + min;
	}

	public static void main(String[] args) {
		double[][] matriz = new double[FILAS][COLUMNAS];

		for (int i = 0; i < FILAS; i++) {
			for (int j = 0; j < COLUMNAS; j++) {
				matriz[i][j] = Rand(0, 10);
			}
		}

		double[][] traspuesta = new double[COLUMNAS][FILAS];
		Thread[][] hilos = new Thread[COLUMNAS][FILAS];

		for (int i = 0; i < COLUMNAS; i++) {
			for (int j = 0; j < FILAS; j++) {
				final int columna = i;
				final int fila = j;
				hilos[i][j] = new Thread(() -> traspuesta[columna][fila] = matriz[fila][columna]);
				hilos[i][j].start();
			}
		}

		for (Thread[] filaHilos : hilos) {
			for (Thread hilo : filaHilos) {
				try {
					hilo.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println("Matriz original:");
		imprimirMatriz(matriz, matriz.length);

		System.out.println("Matriz traspuesta:");
		imprimirMatriz(traspuesta, traspuesta.length);
	}

	private static void imprimirMatriz(double[][] T, int N) {
		for (int i = 0; i < N; i++) {
			System.out.println(Arrays.toString(T[i]));
		}
		System.out.println("\n");
	}
}
