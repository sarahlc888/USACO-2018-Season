import java.io.*;
import java.util.*;
/*
 * USACO 2018 US Open Contest, Gold
 * Problem 3. Talent Show
 * 8/3
 * DP but smarter (with bsearch) (from the answer)
 * 
 * sum of talents / sum of weights >= x ; sum of weights >= W
 * sum of talents - x*sum of weights >= 0
 * rather than search decimals, bsearch for adjusted ratio (int)(1000x)
 * 1000*sum of talents - y*sum of weights >= 0
 * ^^ us the "adjusted talent score"
 * 
 * 10/10
 */
public class TS3 {
	static long[] DP;
	static int N;
	static int W;
	static Pair[] cows;
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("talent.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // num cows FJ has
		W = Integer.parseInt(st.nextToken()); // min weight required
		
		cows = new Pair[N]; // (weight, talent)
		for (int i = 0; i < N; i++) { // scan everything in
			st = new StringTokenizer(br.readLine());
			cows[i] = new Pair(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
		}
		br.close();
	
		// store the max "adjusted talent score"
		DP = new long[W+1];
		
		int ans = greatestBelow();
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("talent.out")));
		System.out.println(ans);
		pw.println(ans);
		pw.close();
	}
	public static boolean works(int y) {
		// returns whether the adjusted ratio y is reachable
		for (int i = 0; i <= W; i++) {
			DP[i] = -Integer.MAX_VALUE; // CANNOT be -1 bc that's too close to 0
			// and is a valid entry 
		}
		DP[0] = 0; // basecase: nothing there, no weight and no talent
		
		for (int i = 0; i < N; i++) { // cows
			long curval = 1000*(long)cows[i].talent - y*(long)cows[i].weight;
			int cw = cows[i].weight;
			for (int k = W; k >= 0; k--) { // loop through weights in DP array
				int nk = Math.min(W, k+cw); // cap tracked weight at W
				if (DP[k] != -Integer.MAX_VALUE) { // if current weight is valid
					if (DP[nk] < DP[k] + curval) { // update if better
						DP[nk] = DP[k] + curval;
					}
				}
			}
		}
		return DP[W] >= 0; // if the ratio is reachable
	}
	public static int greatestBelow() {
		// returns greatest i that "works"

		int lo = 0;
		int hi = 250000;

		while (lo < hi) {
			int mid = (lo + hi + 1)/2; // +1 so lo can become hi in 1st if
			
			if (works(mid)) { // if mid is in range, increase
				lo = mid;
			} else { // mid is not in range, decrease
				hi = mid - 1;
			}
		}
		if (works(hi)) return hi;
		return -1;
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
