import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
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
public class SP3 {
	static long[] P;
	static int M;
	static int K;
	static long mod1;
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); // canvas length
		M = Integer.parseInt(st.nextToken()); // number of stamps
		K = Integer.parseInt(st.nextToken()); // stamp width
		mod1 = 1000000007;

		long[] DP = new long[N+1]; // number of paintings using 1...i w/o k consecutive elements
		P = new long[N+1]; // p[i] = dp[0]...dp[i];

		for (int i = 1; i <= K-1; i++) { 
			DP[i] = power(M, i);
			P[i] = (P[i-1] + DP[i])%mod1;
		}

		//System.out.println(Arrays.toString(DP));
		//System.out.println(Arrays.toString(P)); // prefix sum array

		for (int i = K; i <= N; i++) {
			P[i] = fit(M*P[i-1]-(M-1)*P[i-K]);
		}
		//System.out.println(Arrays.toString(P));
		System.out.println(fit(power(M, N)-(P[N]-P[N-1])));
	}
	public static long fit(long a) {

		while (a%(mod1) < 0) {
			a += mod1;
		}
		return a%mod1;
	}
	public static long power(int a, int b) {
		
		long total = 1;
		
		for (int i = 0 ; i < b; i++) {
			total = (total * a +mod1)%mod1;
		}

		return total;
	}
}