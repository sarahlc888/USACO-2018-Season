import java.io.*;
import java.util.*;
/*
 * USACO 2018 January Contest, Gold
 * Problem 3. Stamp Painting
 * 
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
 *       = (M-1) * (sum of dp[i-j] for j from 1 to K-1)
 * 
 * express the above in terms of a psum array and then you get the answer
 * (move p[i-1] to the other side)	
 * 
 * prefix sum array for DP
 * dp[i] = p[i]-p[i-1]
 * 
 * for loop part = p[i-1]-p[i-k]
 * 
 * p[i] = M*p[i-1]-(M-1)*p[i-k]
 * 
 */
public class SPFileIn {
	static int mod1 = 1000000007;
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("spainting.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); // canvas length
		int M = Integer.parseInt(st.nextToken()); // number of stamps/colors
		int K = Integer.parseInt(st.nextToken()); // stamp width

		// number of paintings using spots 1...i w/o k consecutive elements
		// any valid painting must have k consecutive elements that are the same
		long[] DP = new long[N+1]; 
		long[] P = new long[N+1]; // p[i] = dp[0]...dp[i]; // prefix sum on DP
		long[] power = new long[N+1]; // powers of M: power[i] = M^i
		power[1] = M;
		for (int i = 2; i <= N; i++) 
			power[i] = (power[i-1]*M)%mod1;

		for (int i = 1; i <= K-1; i++) { // before length of a full stamp
			DP[i] = power[i]; // any color can go in each spot
			P[i] = (P[i-1] + DP[i])%mod1;
		}

		System.out.println(Arrays.toString(DP));
		//System.out.println(Arrays.toString(P)); // prefix sum array

		for (int i = K; i <= N; i++) { // after length of a full stamp
			// 
			P[i] = (M*P[i-1]-(M-1)*P[i-K])%mod1;
		}
		System.out.println(Arrays.toString(P));
		System.out.println(fit(power[N]-(P[N]-P[N-1])));
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("spainting.out")));
		pw.println(fit(power[N]-(P[N]-P[N-1])));
		pw.close();
	}
	public static long fit(long a) {

		while (a < 0) {
			a += mod1;
		}
		return a%mod1;
	}
	
}