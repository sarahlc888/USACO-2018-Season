import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
/*
 * 10/10 test cases
 * 
 * DP[i][j] = max P1 score using i...j
 * DP2[i][j] = max P1 score using i...j IF it's P2 turn (so like after P2 goes)
 * DP[i][j] + DP2[i][j] = sum of coins i...j
 * 
 * DP[i][j] = max(
 * 		c[j] + DP2[i][j-1], // take j
 * 		c[i] + DP2[i+1][j]) // take i
 * 
 * DP[i][j] = max(
 * 		c[j] + sum(i, j-1) - DP[i][j-1], // take j
 * 		c[i] + sum(i+1, j) - DP[i+1][j]) // take i
 * DP[i][j] = sum(i, j) - min(
 * 		DP[i][j-1], // take j
 * 		DP[i+1][j]) // take i
 * 
 * O(N^2) time and memory, memory is too much, can't do the two
 * optimize into 1D
 * compute it by diagonal, 2 arrays or vectors
 * 
 * O(N) memory
 * 
 * (i, j) where i == j doesn't rely on anything, = c[i];
 * 
 */
public class TreasureChest {
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int N = Integer.parseInt(br.readLine());

		int[] coins = new int[N];
		int[] P = new int[N]; // partial sum array for coins

		
		
		for (int i = 0; i < N; i++) {
			coins[i] = Integer.parseInt(br.readLine());
			if (i == 0) {
				P[0] = coins[0];
			} else { // update p sum array
				P[i] = P[i-1] + coins[i]; 
			}
		}
		// loop by diagonals
		int[] DPa = new int[N];
		int[] DPb = new int[N];
		int sind = 1; // ind of topmost element in the diagonal
		// for DPb
		
		for (int i = 0; i < N; i++) {
			DPa[i] = coins[i]; // enter coin values for DPa (major diag)
		}
		//System.out.println("sind: " + 0);
		//System.out.println(Arrays.toString(DPa));
		while (sind < N) { // loop through
			// DP[0][sind] start, then i++ and j++
			//System.out.println("sind: " + sind);
			int i = 0;
			int j = sind;
			
			while (i < N-sind) { // loop through
				//System.out.println("  i: " + i);
				if (i == 0) { // bounds for P[i-1]
					//System.out.println("  sum1: " + P[j]);
					DPb[i] = P[j]-Math.min(DPa[i], DPa[i+1]);
				} else {
					//System.out.println("  sum2: " + (P[j]-P[i-1]));
					DPb[i] = P[j]-P[i-1]-Math.min(DPa[i], DPa[i+1]);
				}
				
				// increment to the next thing in the diagonal
				i++;
				j++;
			}
			while (i < N) {
				DPb[i] = 0;
				i++;
			}
			//System.out.println(Arrays.toString(DPb));
			for (int k = 0; k < N; k++) { // transfer over
				DPa[k] = DPb[k];
			}
			
			sind++;
		}

		System.out.println(DPb[0]);

	}
}
