import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;
/*
 * not done
 * 3/10 test cases
 * O(NK) version
 * if painting works, somewhere there must be K of 1 color consecutively. If so, painting works
 * new problem: find # of strips of length N using 1...M with NO K consecutive of 1 color
 * total is M^N perms
 * 
 * DP[i] = # paintings using 1...i 
 */
public class StampPainting {
	static int mod;
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); // canvas length
		int M = Integer.parseInt(st.nextToken()); // number of stamps
		int K = Integer.parseInt(st.nextToken()); // stamp width

		mod = (int)(Math.pow(10, 9) + 7);

		int total = power(M, N, mod); // total possible perms

		long[] DP = new long[N+1]; 
		// initialize
		for (int i = 1; i <= K-1; i++) { 
			DP[i] = power(M, i, mod);
		}

		// if it's K length, it's all combos - M ways to have all K
		DP[K] = fit( power(M, K, mod) - (M%mod)  );

		// loop through 
		for (int i = K+1; i <= N; i++) {
			//System.out.println("i: " + i);
			DP[i] = fit((M%mod)*DP[i-1]-DP[i-K]*((M-1)%mod) + mod);
		}
		// _ _ _
		//System.out.println(Arrays.toString(DP));

		System.out.println(fit(total-DP[N]));
	}
	public static long fit(long a) {
		while (a%mod < 0) {
			a += mod;
		}
		return a%mod;
	}
	public static int power(int a, int b, int m) {
		int total = 1;
		for (int i = 0 ; i < b; i++) {
			total = (total * a+m)%m;
		}
		return total;
	}
}
