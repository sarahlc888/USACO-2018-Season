import java.io.*;
import java.util.*;
/*
 * DP
 * not plain knapsack
 * order matters
 * 
 * DP from the answer... couldn't come up with the idea.........
 * 
 * 4/10 still, times out...,
 * 
 * precompute more things like totheight and weight for subsets
 */
public class GM2 {
	static int N;
	static int H;
	static int[] height;
	static int[] weight;
	static int[] strength;
	static TreeMap<AL, Integer> DP = new TreeMap<AL, Integer>();
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

		// DP
		DP.put(new AL(), Integer.MAX_VALUE);
		AL allcows = new AL(); // all the cows
		for (int i = 0; i < N; i++) {
			allcows.c.add(i);
		}
		
		int maxSF = func(allcows);
		
//		System.out.println(maxSF);
		
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("guard.out")));
		if (maxSF == -1) {
			pw.println("Mark is too tall");
			pw.close();
			return;
		}
		pw.println(maxSF);
		pw.close();
	}
	public static class AL implements Comparable<AL> {
		ArrayList<Integer> c;
		public AL() {
			c = new ArrayList<Integer>();
		}
		public boolean equals(AL o) {
			if (o.c.size() != c.size()) return false;
			for (int i = 0; i < o.c.size(); i++) {
				if (o.c.get(i) != c.get(i)) return false;
			}
			return true;
		}
		@Override
		public int compareTo(AL o) {
			if (c.size() < o.c.size()) return -1;
			if (c.size() > o.c.size()) return 1;
			for (int i = 0; i < o.c.size(); i++) {
				if (c.get(i) > o.c.get(i)) return 1;
				if (c.get(i) < o.c.get(i)) return -1;
			}
			return 0;
		}
		public String toString() {
			return "" + c;
		}
	}
	public static int func(AL cows) {
		// returns highest remaining strength capacity for stack with these cows
		int val = -1*Integer.MAX_VALUE;
		
		
		if (DP.containsKey(cows)) { 
//			System.out.println("  MEMOIZED " + DP.get(cows));
			return DP.get(cows);
		}
//		System.out.println(cows.c + " open");

		AL cows2 = new AL();
		int totweight = 0;
		int totheight = 0;
		for (int i = 0; i < cows.c.size(); i++) {
			cows2.c.add(cows.c.get(i));
			totweight += weight[cows.c.get(i)];
			totheight += height[cows.c.get(i)];
		}
		
		
		for (int i = 0; i < cows2.c.size(); i++) { // loop through cows in cows
			
			int cur = cows2.c.get(i); // put cur on the bottom of the stack
//			System.out.println(cows2.c + " i: " + i + " cur: " + cur);
			cows2.c.remove(i);
			int curval = 0;
			if (totheight-height[cur] >= H && strength[cur]-(totweight-weight[cur]) < 0) {
				// TODO: make sure this condition is right
				curval = func(cows2);
			} else {
				curval = Math.min(func(cows2), strength[cur]-(totweight-weight[cur]));
			}

//			System.out.println(strength[cur]-(totweight-weight[cur]) + " --> " + curval);
			cows2.c.add(i, cur);
			val = Math.max(val, curval);
		}
//		System.out.println(cows.c + " close  val: " + val);
		DP.put(cows2, val);
//		System.out.println("keys: " + DP.keySet());
		return val;
	}
}
