import java.io.*;
import java.util.*;
/*
 * USACO 2018 US Open Contest, Gold
 * Problem 3. Talent Show
 * 8/2/18
 * Greedy bash
 * 3/10 test cases
 */
public class TalentShowGreedy {
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("talent.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); // num cows FJ has
		int W = Integer.parseInt(st.nextToken()); // min weight required
		Pair[] cows = new Pair[N]; // (weight, talent)
		for (int i = 0; i < N; i++) { // scan everything in
			st = new StringTokenizer(br.readLine());
			cows[i] = new Pair(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
		}
		br.close();
		
		Arrays.sort(cows);
		System.out.println(Arrays.toString(cows));
		
		int totalTalent = 0;
		int totalWeight = 0;
		int ind = 0;
		while (ind < N && totalWeight < W) {
			totalWeight += cows[ind].x;
			totalTalent += cows[ind].y;
			ind++;
		}
		
		int ans = (int)(((double)totalTalent / (double)totalWeight) * 1000.0);
		System.out.println(ans);
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("talent.out")));

		pw.println(ans);
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
		public int compareTo(Pair o) { // sort by highest ratio
			if (y*o.x > o.y*x) return -1;
			if (y*o.x < o.y*x) return 1;
//			if (y*o.x == o.y*x) {
			return o.x-x;
//			}
		}
		public String toString() {
			return x + " " + y;
		}
	}


}
