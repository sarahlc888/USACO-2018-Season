import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

// 10/10 test cases

// optimized version for time

// DP[i][j] = # sets of size j using first i families
// brute force on how many of each family used
// at ith family, loop through k = 0...f[i] ants from that family
// DP[i][j] = sum of dp(i-1)(j-k) for all those k

// O(10^10)
// time optimizations = sliding window:
// 		to get from DP[8][10] to DP[8][11], use O(1)
// memory optimizations: 2 array trick

// DP[?][i] = DP[?][A-i] to cut complexity
// DP[1] = T; // number of families

public class AntCounting2 {
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int T = Integer.parseInt(st.nextToken()); // number of families
		int A = Integer.parseInt(st.nextToken()); // num total ants
		int S = Integer.parseInt(st.nextToken()); // min group size
		int B = Integer.parseInt(st.nextToken()); // max group size

		int[] f = new int[T+1]; // family[i] = # ants in family i

		for (int i = 0; i < A; i++) { // scan in ants
			int ant = Integer.parseInt(br.readLine());
			f[ant]++;
		}

		// DP[i][j] = # sets of size j using first i families
		int[][] DP = new int[T+1][B+1];
		// when size is 0, basecase = 1

		for (int j = 0; j <= f[1]; j++) { // row 1 is basecase
			DP[1][j] = 1; // 1 family, any size less than the number, 1 way
		}
		for (int i = 2; i <= T; i++) { // TODO: check if it's right
			DP[i][0] = 1; // any families, size 0, 1 way to make nothing
		}

		for (int i = 2; i <= T; i++) { // loop through 2+ families
			int k = f[i]; // the max k from the loop version
			int sum = 0; // running
			for (int j = 0; j <= B; j++) { // any size
				if (j == 1) {
					DP[i][j] = i; // i fams, size 1, i ways
				}
				sum += DP[i-1][j]; // the new thing in sliding window
				sum%=1000000;
				if (j-k-1 >= 0) { // min element for prev j, now gone
					sum -= DP[i-1][j-k-1];
					sum = (sum+1000000)%1000000;
				}
				
				DP[i][j] = sum%1000000;

			}

		}
		
		long total = 0;
		
		for (int i = S; i <= B; i++) {
			total += DP[T][i];
			total %= 1000000;
		}
		System.out.println(total);
		for (int i = 0; i <= T; i++) {
			//System.out.println(Arrays.toString(DP[i]));
		}
	}
}
