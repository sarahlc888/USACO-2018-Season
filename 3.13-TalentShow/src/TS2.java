import java.io.*;
import java.util.*;
/*
 * USACO 2018 US Open Contest, Gold
 * Problem 3. Talent Show
 * 8/2/18
 * DP but smarter
 * still too much memory
 */
public class TS2 {
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("talent.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); // num cows FJ has
		int W = Integer.parseInt(st.nextToken()); // min weight required
		
		Pair[] cows = new Pair[N]; // (weight, talent)
		int totaltalent = 0;
		int totalweight = 0;
		
		for (int i = 0; i < N; i++) { // scan everything in
			st = new StringTokenizer(br.readLine());
			cows[i] = new Pair(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
			totaltalent += cows[i].talent;
			totalweight += cows[i].weight;
		}
		br.close();
		
		// track the best
		int bestTalent = 0;
		int bestWeight = 1;
		
		int[] m = new int[N];
		
		// store the next index to look at
		int[][] DP = new int[totalweight+1][totaltalent+1];
		for (int i = 0; i <= totalweight; i++) {
			for (int j = 0; j <= totaltalent; j++) {
				DP[i][j] = -1; // mark impossible
			}
		}
		DP[0][0] = 0;
		
		// knapsack
		for (int i = 0; i <= totalweight; i++) {
			for (int j = 0; j <= totaltalent; j++) {
				if (DP[i][j] == -1) continue;
				// if current pos is reachable, proceed
//				System.out.println("w: " + i + " t: " + j + " val: " + DP[i][j]);
				
				for (int k = DP[i][j]; k < N; k++) { // indices of next cow
					
					Pair next = cows[k]; // get the next cow
//					System.out.println("  k: " + k + "  nextCow: " + next + "  nextMark: " + (i+next.weight) + " " + (j+next.talent));
					DP[i+next.weight][j+next.talent] = k+1; // mark it
					
					if (i+next.weight >= W && better(j+next.talent, i+next.weight, bestTalent, bestWeight)) {
						bestTalent = j+next.talent;
						bestWeight = i+next.weight;
					}
				}
			}
		}
		
//		System.out.println(bestTalent);
//		System.out.println(bestWeight);
		int ans = (int)(((double)bestTalent / (double)bestWeight) * 1000.0);
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("talent.out")));
//		System.out.println(ans);
		pw.println(ans);
		pw.close();
	}
	public static boolean better(int t1, int w1, int t2, int w2) {
		// returns whether set 1 or 2 is better (has a higher ratio t : w)
		if (t1*w2 > t2*w1) {
			return true;
		}
		return false;
	}
	public static class Pair implements Comparable<Pair> {
		int weight;
		int talent;

		public Pair(int a, int b) {
			weight = a;
			talent = b;
		}
		@Override
		public int compareTo(Pair o) { // sort by x, then y
			if (weight == o.weight) return talent-o.talent;
			return o.weight-weight;
		}
		public String toString() {
			return weight + " " + talent;
		}
	}


}
