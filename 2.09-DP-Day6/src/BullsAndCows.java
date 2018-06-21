import java.io.*;
import java.util.*;
/*
 * 10/10 test cases, don't fully understand this but ok
 * 
 * O(N)
 * f[n] = num ways with n in a row
 * f[n] = f[n-1] + f[n-k-1] (add a cow or bull at the end; bull needs k cows before it)
 * 
 * basecase: f[n] = n+1 for 1 through K, all C or put a B in every position
 * 
 *  mod by M every time to avoid overflow
 */
public class BullsAndCows {
	public static void main(String[] args) throws IOException {
		Scanner scan = new Scanner(System.in);
		StringTokenizer st = new StringTokenizer(scan.nextLine());
		int N = Integer.parseInt(st.nextToken()); // number of cows
		int K = Integer.parseInt(st.nextToken()); // num cows required between bulls
		int M = 5000011;
		
		int[] DP = new int[N+1];
		// basecases
		for (int i = 0; i <= N; i++) { 
			DP[i] = i+1;
		}
		//System.out.println(Arrays.toString(DP));
		// calc
		for (int i = K+1; i <= N; i++) { // once you can add bulls, do it
			DP[i] = (DP[i-1] + DP[i-K-1])%M;
			
		}
		
		System.out.println(DP[N]%M);
	}
}
