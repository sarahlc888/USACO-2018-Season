import java.io.*;
import java.util.*;
/*
 * see 1.45 for the original version (in contest attempt)
 * USACO 2018 February Contest, Gold
 * Problem 1. Snow Boots
 * 
 * 4/10 test cases, just like the last attempt in 1.45....
 * 12/12 correct, had to write own linked list instead of using predef!
 * 
 * had to look at answer, linked list
 */

public class SnowBoots {
	public static void main(String args[]) throws IOException {
		// INPUT
		BufferedReader br = new BufferedReader(new FileReader("snowboots.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int N = Integer.parseInt(st.nextToken()); // num tiles
		int B = Integer.parseInt(st.nextToken()); // num pairs of boots
		
		// (snow, init pos)
		Pair[] tiles = new Pair[N]; 
		Pair[] tiles2 = new Pair[N];
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			int s = Integer.parseInt(st.nextToken());
			Pair p = new Pair(s, i);
			tiles[i] = tiles2[i] = p;
		}
		Arrays.sort(tiles2);
		int minInd = 0; // next ind in tiles2 to remove
//		System.out.println("tiles2: " + Arrays.toString(tiles2));
		
		// holds max [depth] and [step]
		Pair[] boots = new Pair[B];
		Triple[] boots2 = new Triple[B];
		for (int i = 0; i < B; i++) {
			st = new StringTokenizer(br.readLine());
			boots[i] = new Pair(Integer.parseInt(st.nextToken()), 
					Integer.parseInt(st.nextToken()));
			boots2[i] = new Triple(i, boots[i]);
			//System.out.println(boots[i][0] + "  " + boots[i][1]);
		}
		br.close();
		Arrays.sort(boots);
		Arrays.sort(boots2);
//		System.out.println(Arrays.toString(boots));
	

		// linked list for the tiles
		int[] prev = new int[N];
		int[] next = new int[N];
		for (int i = 0; i < N; i++) {
			prev[i] = i-1;
			next[i] = i+1;
			if (i + 1 == N) next[i] = -1;
		}
		
		int maxGap = 1; // max gap between tiles that can be stepped on

		int[] ans = new int[B];
		for (int i = 0; i < B; i++) {
			Pair curBoot = boots[i];
//			System.out.println("i: " + i + "  curboot: " + curBoot);
//			System.out.println("  " + tiles);
//			System.out.println("  minInd: " + minInd);
			while (minInd < N && tiles2[minInd].x > curBoot.x) {
				int curInd = tiles2[minInd].y; 
				// remove curInd
				next[prev[curInd]] = next[curInd];
				prev[next[curInd]] = prev[curInd];
				
//				System.out.println("  " + tiles);
				// check the new gap
				maxGap = Math.max(maxGap, tiles[next[curInd]].y - tiles[prev[curInd]].y);
				minInd++;
			}
			if (maxGap > curBoot.y) {
				ans[boots2[i].ind] = 0;
			} else {
				ans[boots2[i].ind] = 1;
			}
//			System.out.println("  maxGap: " + maxGap);
		}
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("snowboots.out")));
		for (int i = 0; i < B; i++) {
//			System.out.println(ans[i]);
			pw.println(ans[i]);
		}
		pw.close();
	}
	public static class Triple implements Comparable<Triple> {
		int ind;
		Pair b;
		public Triple(int x, Pair y) {
			ind = x;
			b = y;
		}
		public int compareTo(Triple o) {
			return b.compareTo(o.b);
		}
		public String toString() {
			return ind + "  " + b;
		}
	}
	public static class Pair implements Comparable<Pair> {
		int x;
		int y;

		public Pair(int a, int b) {
			x = a;
			y = b;
		}
		public String toString() {
			return x + " " + y;
		}
		@Override
		public int compareTo(Pair o) {
			if (x == o.x) return y-o.y;
			return o.x-x;
		}
		public boolean equals(Pair o) {
			return x == o.x && y == o.y;
		}
	}


}
