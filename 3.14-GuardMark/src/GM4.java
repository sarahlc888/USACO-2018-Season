import java.io.*;
import java.util.*;

/*
 * DP
 * not plain knapsack
 * order matters
 * 
 * DP from the answer... couldn't come up with the idea.........
 * 
 * 6/10 still, times out...
 * 
 * NO DP ARRAY HERE
 * 
 * changed so that instead of calling func once 
 * on everything, call only on needed subsets
 */
public class GM4 {
	static int N;
	static int H;
	static int[] height;
	static int[] weight;
	static int[] strength;
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("guard.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // num cows
		H = Integer.parseInt(st.nextToken()); // goal height
		
		// cows
		height = new int[N];
		weight = new int[N];
		strength = new int[N];
		
		for (int i = 0; i < N; i++) { // scan everything in
			st = new StringTokenizer(br.readLine());
			height[i] = Integer.parseInt(st.nextToken());
			weight[i] = Integer.parseInt(st.nextToken());
			strength[i] = Integer.parseInt(st.nextToken());
		}
		br.close();

		// DP set up
		Subset allcows = new Subset(); // all the cows
		for (int i = 0; i < N; i++) {
			allcows.c[i] = 1;
		}
		int maxSF = -1;
		
		// getting subsets
		for (int i = 0; i < Math.pow(2, N); i++) {
			String bin = Integer.toBinaryString(i);
			int pad = N-bin.length();
			for (int j = 0; j < pad; j++) {
				bin = "0" + bin;
			}
//			System.out.println(bin);
			Subset cur = new Subset();
			int totheight = 0;
			int totweight = 0;
			for (int j = 0; j < N; j++) { // loop through binary string
				if (bin.charAt(j) == '1') {
					cur.c[j] = 1;
					totheight += height[j];
					totweight += weight[j];
				}
			}
			
			if (totheight >= H) { // if it's valid, check to see
				int nval = func(cur, totweight);
				if (maxSF < nval) {
					maxSF = nval;
//					System.out.println("  !!");
				}				
			}
		}
		
//		System.out.println(maxSF);
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("guard.out")));
		if (maxSF == -1) {
			pw.println("Mark is too tall");
			pw.close();
			return;
		}
		pw.println(maxSF);
		pw.close();
		
//		int[] x = {0, 1, 1, 1, 1, 1, 1, 0, 0};
//		Subset x1 = new Subset(x);
//		System.out.println(func(x1, 21));
	}
	public static class Subset implements Comparable<Subset> {
		int[] c; // basically boolean
		public Subset() {
			c = new int[N];
		}
		public Subset(int[] b) {
			c = b;
		}
		@Override
		public int compareTo(Subset o) {
			if (c.length < o.c.length) return -1;
			if (c.length > o.c.length) return 1;
			for (int i = 0; i < o.c.length; i++) {
				if (c[i] > o.c[i]) return 1;
				if (o.c[i] < c[i]) return -1;
			}
			return 0;
		}
		public boolean equals(Subset o) {
			for (int i = 0; i < o.c.length; i++) {
				if (c[i] != o.c[i]) return false;
			}
			return true;
		}
		public String toString() {
			return "" + Arrays.toString(c);
		}
	}
	public static int func(Subset cows, int totweight) {
		// returns highest remaining strength capacity for stack with these cows
		int val = -1*Integer.MAX_VALUE;
//		System.out.println(Arrays.toString(cows.c) + " open  totweight: " + totweight);

		boolean empty = true;
		for (int i = 0; i < N; i++) {
			if (cows.c[i] == 1) {
				empty = false;
				break;
			}
		}
		if (empty) {
			return Integer.MAX_VALUE;
		}
		for (int i = 0; i < N; i++) { // loop through cows 
			if (cows.c[i] == 0) continue;
			
			// put i on the bottom of the stack, deal with subproblems on top
			
			cows.c[i] = 0; // take i out
			int curval = 0;
			int weightWO = totweight - weight[i];
			
			int v1 = func(cows, weightWO);
			int v2 = strength[i]-weightWO;
			
//			System.out.println(cows + " -->");
			cows.c[i] = 1; // put i back
			
//			System.out.println(Arrays.toString(cows.c) + " i: " + i + " weight w/o i: " + weightWO + "  from f: " +v1 + "  cur: " + v2);
			curval = Math.min(v1, v2);
			
//			System.out.println(strength[cur]-(totweight-weight[cur]) + " --> " + curval);
			
			val = Math.max(val, curval);
		}
//		System.out.println(Arrays.toString(cows.c) + " close  val: " + val);
		
//		System.out.println("entries: " + DP.entrySet());
		return val;
	}
}
