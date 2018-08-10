import java.io.*;
import java.util.*;
/*
 * USACO 2016 December Contest, Platinum
 * Problem 2. Team Building
 * 8/9/18
 * DP, couldn't come up with answer, from lesson
 * the thing is kind of knapsack reminiscent
 * 
 * copied from the answer
 */
public class TeamBuilding {
	static int MOD = 1000000009;
	public static void main(String args[]) throws IOException {
		// input
		BufferedReader br = new BufferedReader(new FileReader("team.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); // num FJ cows
		int M = Integer.parseInt(st.nextToken()); // num FP cows
		int K = Integer.parseInt(st.nextToken()); // team size
		
		int[] fj = new int[N];
		int[] fp = new int[M];
		
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) 
			fj[i] = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < M; i++) 
			fp[i] = Integer.parseInt(st.nextToken());
	
		br.close();
		Arrays.sort(fj);
		Arrays.sort(fp);
//		System.out.println(Arrays.toString(fj));
//		System.out.println(Arrays.toString(fp));
		
		// dp[fj pos][fp pos] = num ways to win
		// pos means last pos used
		long[][] dp = new long[N][M]; 
		
		for (int i = 0; i < K; i++) { // loop through all the rounds
//			System.out.println("teamsize: " + (i+1));
			long[][] next = new long[N][M]; // next iteration of "knapsack"
			// get the wins from the next round and update
			// the "highest scoring cow that fj and fp use" and the num ways to get there
			for (int j = 0; j < N; j++) {
				for (int k = 0; k < M; k++) { // loop through all the cows
					if (fj[j] <= fp[k]) continue; // if losing, skip
					
					if (j+1 < i || k+1 < i) continue; // if not enough to form a team, skip
					
					if (i == 0) { // basecase to set up the first dp correctly
						next[j][k] += 1;
					} else if (j > 0 && k > 0) {
//						System.out.println("j: " + j + "  k: " + k);
						next[j][k] += dp[j-1][k-1]; 
					}
				}
			}
			dp = next; // no need to transfer over old stuff 
			// because you'll just do the same process again on them
//			for (int j = 0; j < N; j++) {
//				System.out.println(Arrays.toString(dp[j]));
//			}
			// loop through dp and update upwards
			// same k cows, so bigger groups can enter the same cows as smaller groups
			for (int j = 0; j < N; j++) {
				for (int k = 0; k < M; k++) {
					// use one fewer fj cow or one fewer fp cow
					if (j > 0)
						dp[j][k] += dp[j-1][k];
					if (k > 0)
						dp[j][k] += dp[j][k-1];
					if (j > 0 && k > 0)
						dp[j][k] -= dp[j-1][k-1]; // MINUS to prevent overcounting! see diagram
					
					/*
					 * 
					 *  | --> | -->
					 *  \/   \/
					 *  | --> *
					 *  \/
					 * 
					 */
					
					dp[j][k] %= MOD;
					dp[j][k] += MOD;
					dp[j][k] %= MOD;
				}
			}
			
		}
		System.out.println(dp[N-1][M-1]%MOD);
		
		
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("team.out")));

		pw.println(dp[N-1][M-1]%MOD);
		pw.close();
	}
	public static class Pair implements Comparable<Pair> {
		int x;
		int y;

		public Pair(int a, int b) {
			x = a;
			y = b;
		}
		@Override
		public int compareTo(Pair o) { // sort by x, then y
			if (x == o.x) return y-o.y;
			return o.x-x;
		}
		public String toString() {
			return x + " " + y;
		}
	}


}
