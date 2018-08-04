import java.io.*;
import java.util.*;
/*
 * DP and bitmask
 * 
 * 14/14 cases
 */
public class GM1 {
	static int N;
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("guard.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // num cows
		int H = Integer.parseInt(st.nextToken()); // goal height
		
		// cows
		int[] height = new int[N];
		int[] weight = new int[N];
		int[] strength = new int[N];
		
		for (int i = 0; i < N; i++) { // scan everything in
			st = new StringTokenizer(br.readLine());
			height[i] = Integer.parseInt(st.nextToken());
			weight[i] = Integer.parseInt(st.nextToken());
			strength[i] = Integer.parseInt(st.nextToken());
		}
		br.close();

		int maxSF = -1*Integer.MAX_VALUE;
		
		// "DP"
		int[] DP = new int[(int)Math.pow(2, N)]; // max safety factor for indicated subset (see bitmask)
		int[] subHeight = new int[(int)Math.pow(2, N)];
				
		
		for (int i = 0; i < Math.pow(2, N); i++) { // everything else can't hold anything yet
			DP[i] = -1 * Integer.MAX_VALUE + 20;
		}
		
		DP[0] = Integer.MAX_VALUE-20; // ground can hold infinity
		
		for (int i = 0; i < Math.pow(2, N); i++) {
			String cur = pad(Integer.toBinaryString(i)); // current subset
			
			for (int j = 0; j < N; j++) { // loop thru cur
				if (cur.charAt(j) == '0') { // if the element is not included, include it
					int places = N-j-1; // number of places
					int newInd = i+(int)Math.pow(2, places);
//					if (newInd == 252) {
//						System.out.println("cur: " + cur + " j: " + j);
//						System.out.println("  prev: " + DP[i]);
//					}
//					DP[newInd] = Math.min(strength[j], DP[i]-weight[j]);
					DP[newInd] = Math.max(DP[newInd], Math.min(strength[j], DP[i]-weight[j]));
					subHeight[newInd] = subHeight[i] + height[j];

//					if (newInd == 252) {
//						System.out.println("  " + pad(Integer.toBinaryString(newInd)));
//						System.out.println("  sj: " + strength[j] + "  curSF: " + (DP[i]-weight[j]) + 
//							"   height: " + subHeight[newInd] + " val: " + DP[newInd]);
					
//					}
					if (subHeight[newInd] >= H) {
						maxSF = Math.max(maxSF, DP[newInd]);
					}
				}
			}
		}
			
		
		System.out.println(maxSF);
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("guard.out")));
		if (maxSF < 0) {
			pw.println("Mark is too tall");
			pw.close();
			return;
		}
		pw.println(maxSF);
		pw.close();
	}
	public static String pad(String s) {
		while (s.length() < N) {
			s = "0" + s;
		}
		return s;
	}
	public static class S implements Comparable<S> {
		int height;
		int strength;
		boolean[] hasCow = new boolean[N]; // if cows are present
		TreeSet<Integer> cows = new TreeSet<Integer>(); // cows used (not in order)
		// TODO: if needed, cows in order
		
		public S(int a, int b) {
			height = a;
			strength = b;
		}
		@Override
		public int compareTo(S o) { // sort by x, then y
			if (height == o.height) return strength-o.strength;
			return o.height-height;
		}
		public String toString() {
			return height + " " + strength;
		}
	}


}
