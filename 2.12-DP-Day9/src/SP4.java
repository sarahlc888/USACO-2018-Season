import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.StringTokenizer;
/*
 * 12/12 test cases
 * 
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
public class SP4 {
	static int mod1 = 1000000007;
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); // canvas length
		int M = Integer.parseInt(st.nextToken()); // number of stamps
		int K = Integer.parseInt(st.nextToken()); // stamp width

		long[] DP = new long[N+1]; // number of paintings using 1...i w/o k consecutive elements
		long[] P = new long[N+1]; // p[i] = dp[0]...dp[i];

		long[] power = new long[N+1]; // power[i] = M^i
		power[1] = M;
		
		for (int i = 2; i <= N; i++) {
			power[i] = (power[i-1]*M)%mod1;
		}
		
		//System.out.println(Arrays.toString(power));

		for (int i = 1; i <= K-1; i++) { 
			DP[i] = power[i];

			P[i] = (P[i-1] + DP[i])%mod1;
		}

		//System.out.println(Arrays.toString(DP));
		//System.out.println(Arrays.toString(P)); // prefix sum array

		for (int i = K; i <= N; i++) {
			//P[i] = fit(M*P[i-1]-(M-1)*P[i-K]);
			P[i] = (M*P[i-1]-(M-1)*P[i-K])%mod1;
		}
		//System.out.println(Arrays.toString(P));
		System.out.println(fit(power[N]-(P[N]-P[N-1])));
	}
	public static long fit(long a) {

		while (a < 0) {
			a += mod1;
		}
		return a%mod1;
	}
	
}