import java.io.*;
import java.util.*;

/*
 * DP
 * not plain knapsack
 * order matters
 * 
 * DP from the answer... couldn't come up with the idea.........
 * 
 * 6/10 
 * 
 * precompute more things like totheight and weight for subsets
 * 
 * changed so that instead of calling func once 
 * on everything, call only on needed subsets
 */
public class GM31 {
	static int N;
	static int H;
	static int[] height;
	static int[] weight;
	static int[] strength;
	static TreeMap<String, Integer> DP = new TreeMap<String, Integer>();
	static String x;
	static String e;
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
		
//		System.out.println(Arrays.toString(height));
//		System.out.println(Arrays.toString(weight));
//		System.out.println(Arrays.toString(strength));

		x = "000000100";
		e = "";
		for (int i = 0; i < N; i++) {
			e += "0";
		}
//		System.out.println(e.equals(x));
		
		// DP set up
		DP.put(e, Integer.MAX_VALUE);
//		System.out.println(DP.get(e));
		
		int maxSF = -1;
		
		// getting subsets
		for (int i = 0; i < Math.pow(2, N); i++) {
			String cur = Integer.toBinaryString(i);
			int pad = N-cur.length();
			for (int j = 0; j < pad; j++) {
				cur = "0" + cur;
			}
//			System.out.println(cur);
			int totheight = 0;
			int totweight = 0;
			for (int j = 0; j < N; j++) { // loop through binary string
				if (cur.charAt(j) == '1') {
					totheight += height[j];
					totweight += weight[j];
				}
			}
//			System.out.println("  th: " + totheight + " tw: " + totweight);
			if (totheight >= H) { // if it's valid, check to see
				
//				if (cur.equalsIgnoreCase("011111100")) {
//					System.out.println("        here");
//				}
				int nval = func(cur, totweight);
//				System.out.println("  nval: " + nval);
//				if (cur.equalsIgnoreCase("011111100")) {
//					System.out.println("val " + nval);
//				}
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
		

	}
	public static int func(String cows1, int totweight) {
		String cows = "";
		for (int i = 0; i < N; i++) {
			cows += cows1.charAt(i);
		}
		
//		boolean print = cows.equals(x) || cows.equals(e);

		// returns highest remaining strength capacity for stack with these cows
		int val = -1*Integer.MAX_VALUE;

//		if (print) {
//			System.out.println(" eval: " + DP.get(e) + " e: " + e);
//			System.out.println(DP.entrySet());
//		}
			
		if (DP.get(cows) != null) { 
//		if (DP.containsKey(cows) && DP.get(cows) != -1 * Integer.MAX_VALUE) { 
//			if (print)
//			System.out.println("    MEMOIZED " + cows + " " + DP.get(cows));
			return DP.get(cows);
		}
//		System.out.println("    " + (cows) + " open  totweight: " + totweight);

		for (int i = 0; i < N; i++) { // loop through cows, pick one to "discard"
			
			if (cows.charAt(i) == '0') continue;

			// put i on the bottom of the stack, deal with subproblems on top
			
			String ncows = cows.substring(0, i) + "0" + cows.substring(i+1);
			
//			if (print) {
//				System.out.println("  i: " + i + " " + cows);
//			}
			int curval = 0;
			int weightWO = totweight - weight[i];
			
			int v1 = func(ncows, weightWO);
			int v2 = strength[i]-weightWO;
			
//			System.out.println(cows + " -->");
			
			
//			if (print)
//				System.out.println(cows + " i: " + i + " weight w/o i: " + weightWO + "  from f: " +v1 + "  cur: " + v2);
			curval = Math.min(v1, v2);
			
//			System.out.println(strength[cur]-(totweight-weight[cur]) + " --> " + curval);
			
			val = Math.max(val, curval);
		}
//		System.out.println("    " + cows + " close  val: " + val);
////		
		DP.put(cows, val);
//		System.out.println(DP.keySet().contains(x1));
//		System.out.println("entries: " + DP.entrySet());
		return val;
	}
}
