import java.io.*;
import java.util.*;

/*
 * DP
 * not plain knapsack
 * order matters
 * 
 * DP from the answer... couldn't come up with the idea.........
 * 
 * 5/10 still, times out...
 * 
 * precompute more things like totheight and weight for subsets
 * 
 * changed so that instead of calling func once 
 * on everything, call only on needed subsets
 */
public class GM3 {
	static int N;
	static int H;
	static int[] height;
	static int[] weight;
	static int[] strength;
	static TreeMap<Subset, Integer> DP = new TreeMap<Subset, Integer>();
	static Subset x1;
	static Subset e;
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

		
		int[] x = {0, 0, 0, 0, 0, 0, 1, 0, 0};
		int[] x2 = {0, 0, 0, 0, 0, 0, 0, 0, 0};
		x1 = new Subset(x);
		e = new Subset(x2);
//		System.out.println(func(x1, 21));
		System.out.println(e.equals(x1));
		
		// DP set up
		DP.put(e, Integer.MAX_VALUE);
		System.out.println(DP.get(x1));
		
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
				if (bin.equalsIgnoreCase("011111100")) {
					System.out.println("        here");
				}
				int nval = func(cur, totweight);
				if (bin.equalsIgnoreCase("011111100")) {
					System.out.println("val " + nval);
				}
				if (maxSF < nval) {
					maxSF = nval;
//					System.out.println("  !!");
				}				
			}
		}
		
		System.out.println(maxSF);
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("guard.out")));
		if (maxSF == -1) {
			pw.println("Mark is too tall");
			pw.close();
			return;
		}
		pw.println(maxSF);
		pw.close();
		

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
	public static int func(Subset cows1, int totweight) {
		Subset cows = new Subset();
		for (int i = 0; i < N; i++) {
			cows.c[i] = cows1.c[i];
		}
		
		boolean print = cows.equals(x1) || cows.equals(e);

		// returns highest remaining strength capacity for stack with these cows
//		System.out.println(Arrays.toString(cows.c) + " open  totweight: " + totweight);
		int val = -1*Integer.MAX_VALUE;

		if (print) {
			System.out.println(" eval: " + DP.get(e) + " e: " + e);
			System.out.println(DP.entrySet());
		}
			
		if (DP.get(cows) != null) { 
//		if (DP.containsKey(cows) && DP.get(cows) != -1 * Integer.MAX_VALUE) { 
//			if (print)
//				System.out.println("  MEMOIZED " + cows + " " + DP.get(cows));
			return DP.get(cows);
		}
		
		for (int i = 0; i < N; i++) { // loop through cows, pick one to "discard"
			
			if (cows.c[i] == 0) continue;

			// put i on the bottom of the stack, deal with subproblems on top
			
			cows.c[i] = 0; // take i out
			if (print) {
				System.out.println("  i: " + i + " " + cows);
			}
			int curval = 0;
			int weightWO = totweight - weight[i];
			
			int v1 = func(cows, weightWO);
			int v2 = strength[i]-weightWO;
			
//			System.out.println(cows + " -->");
			
			cows.c[i] = 1; // put i back
			
			if (print)
				System.out.println(Arrays.toString(cows.c) + " i: " + i + " weight w/o i: " + weightWO + "  from f: " +v1 + "  cur: " + v2);
			curval = Math.min(v1, v2);
			
//			System.out.println(strength[cur]-(totweight-weight[cur]) + " --> " + curval);
			
			val = Math.max(val, curval);
		}
//		System.out.println(Arrays.toString(cows.c) + " close  val: " + val);
		Subset cows2 = new Subset();
		for (int i = 0; i < N; i++) {
			cows2.c[i] = cows.c[i];
		}
		if (cows2.equals(e)) System.out.println("LDFJL:KSDJFKLDSJFSDF");
		DP.put(cows2, val);
//		System.out.println(DP.keySet().contains(x1));
//		System.out.println("entries: " + DP.entrySet());
		return val;
	}
}
