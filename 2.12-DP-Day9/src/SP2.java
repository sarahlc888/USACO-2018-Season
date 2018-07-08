import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;
/*
 * O(N)
 * 
 * brute force on how many of the last elements are equal
 * use prefix sums 
 * 
 * dp[i] = sum of
 * 	for j from 1 to K-1
 * 	dp[i-j]*(M-1)
 * 
 * prefix sum array for DP
 * 
 * dp[i] = p[i]-p[i-1]
 * 
 * for loop part = p[i-1]-p[i-k]
 * 
 * p[i] = M*p[i-1]-(M-1)*p[i-k]
 * 
 */
public class SP2 {
	static long[] P;
	static int M;
	static int K;
	static int mod;
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); // canvas length
		M = Integer.parseInt(st.nextToken()); // number of stamps
		K = Integer.parseInt(st.nextToken()); // stamp width
		
		mod = (int)(Math.pow(10, 9) + 7);
		int total = power(M, N, mod); // total possible perms
		
		long[] DP = new long[N+1]; // number of paintings using 1...i w/o k consecutive elements
		P = new long[N+1]; // p[i] = dp[0]...dp[i];

		for (int i = 1; i <= K-1; i++) { 
			DP[i] = power(M, i, mod);
			P[i] = fit(P[i-1] + DP[i]);
		}
		
		//System.out.println(Arrays.toString(DP));
		//System.out.println(Arrays.toString(P)); // prefix sum array
		
		for (int i = K; i <= N; i++) {
			P[i] = fit((M%mod)*P[i-1]-((M-1)%mod)*P[i-K]);
		}
		
		System.out.println(fit(total-(P[N]-P[N-1])));
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
