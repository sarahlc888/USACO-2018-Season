import java.io.*;
import java.util.*;

/*
 * all test cases correct
 * version without PS
 * 
3
1 -1 1
-1 5 -1
1 1 1
 */
public class Leprechaun2 {
	public static void main(String args[]) throws IOException {
		Scanner scan = new Scanner(System.in);
		int N = Integer.parseInt(scan.nextLine()); // N by N matrix
		int[][] matrix = new int[N][N];
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(scan.nextLine());
			for (int j = 0; j < N; j++) {
				matrix[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		
		int max = Integer.MIN_VALUE;
		
		// process rows
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				int sum = 0;
				for (int k = 0; k < N; k++) {
					sum += matrix[i][(j+k)%N];
					if (sum > max) max = sum;
				}
				
			}
		}
		
		// process cols
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				int sum = 0;
				for (int k = 0; k < N; k++) {
					sum += matrix[(i+k)%N][j];
					if (sum > max) max = sum;
				}
				
			}
		}

		// process diags 1 (\)
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				int sum = 0;
				for (int k = 0; k < N; k++) {
					sum += matrix[(i+k)%N][(j+k)%N];
					if (sum > max) max = sum;
				}
				
			}
		}
		
		// process diags 2 (/)
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				
				
				int sum = 0;
				for (int k = 0; k < N; k++) {
					sum += matrix[(i-k+N)%N][(j+k)%N];
					if (sum > max) max = sum;
				}
				
			}
		}
		
		System.out.println(max);
		/*
		// col partial sums
		int[][] colPS = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				colPS[i][j] = matrix[i][j];
			}
		}
		for (int i = 1; i < N; i++) {
			for (int j = 0; j < N; j++) {
				colPS[i][j] = Math.max(colPS[i][j], colPS[i-1][j] + matrix[i][j]);
				if (colPS[i][j] > max) max = colPS[i][j];
			}
		}
		
		// TODO: SHL and fix stuff so that it wraps around
		
		// diag partial sums \ i+1 j+1
		// start by determining the top and the left
		int[][] diag1PS = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				diag1PS[i][j] = matrix[i][j];
			}
		}
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				diag1PS[i][j] = Math.max(diag1PS[i][j], diag1PS[(i-1+N)%N][(j-1+N)%N] + matrix[i][j]);
				if (diag1PS[i][j] > max) max = diag1PS[i][j];
			}
		}
		
		
		// diag partial sums / i-1 j+1
		// start by determining the bottom and the left
		int[][] diag2PS = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				diag2PS[i][j] = matrix[i][j];
			}
		}
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				diag2PS[i][j] = Math.max(diag2PS[i][j], diag2PS[(i+1)%N][(j-1+N)%N] + matrix[i][j]);
				if (diag2PS[i][j] > max) max = diag2PS[i][j];
			}
		}
		for (int i = 0; i < N; i++) {
			System.out.println(Arrays.toString(diag2PS[i]));
		}
		
		System.out.println(max);
		
		*/
		
	}
}
