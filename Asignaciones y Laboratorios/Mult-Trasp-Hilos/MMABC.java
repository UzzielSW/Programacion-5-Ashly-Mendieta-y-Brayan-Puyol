
/**
 * @Nota: 100
 * @Material: Programación V
 * @autor: Brayan Puyol, Ashly Mendieta
 * @date: 24/09/2024
 * @version: 2.0
 */
public class MMABC {

	public static int Rand(int min, int max) {
		return (int) (Math.random() * (max - min + 1)) + min;
	}

	public static void main(String[] args) {

		int N = 3;
		double[][] A, B, C;

		A = new double[N][N];
		B = new double[N][N];
		C = new double[N][N];

		for (int i = 0; i < C.length; i++) {
			for (int j = 0; j < C.length; j++) {
				A[i][j] = Rand(0, 10);
				B[i][j] = Rand(0, 10);
			}
		}

		MultConcurrente mult = new MultConcurrente(A, B, N);

		C = mult.calcular();
		System.out.println("\nMatriz A");
		mult.desplegar(A);
		System.out.println("Matriz B");
		mult.desplegar(B);
		System.out.println("Matriz C");
		mult.desplegar(C);
	}
}
