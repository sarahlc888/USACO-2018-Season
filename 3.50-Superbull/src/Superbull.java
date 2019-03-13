import java.io.*;
import java.util.*;
/*
 * USACO 2015 February Contest, Silver
 * Problem 3. Superbull
 * wrong idea
 */
public class Superbull {
	public static void main(String args[]) throws IOException {
		// input ids
		BufferedReader br = new BufferedReader(new FileReader("superbull.in"));
		int N = Integer.parseInt(br.readLine());
		int[] ids = new int[N];
		for (int i = 0; i < N; i++) {
			ids[i] = Integer.parseInt(br.readLine());
			System.out.println("i: " + i + " id: " + ids[i]);
		}
		br.close();
		// tourney stats
		int tourneyLength = (int)(0.5+Math.log10(N)/Math.log10(2)); // number of rounds
		int totalMatches = 0; // number of matches
		for (int i = 1; i <= tourneyLength; i++) totalMatches += i;
		System.out.println("tourney length: " + tourneyLength + " totMatches: " + totalMatches);

		// calculate scores
		int[][] score = new int[N][N];
		Pair[] poss = new Pair[N*(N-1)/2];
		int ind = 0;
		for (int i = 0; i < N; i++) {
			for (int j = i+1; j < N; j++) {
				int xorVal = ids[i]^ids[j];
				score[i][j] = xorVal;
				score[j][i] = xorVal;
				poss[ind] = new Pair(ids[i], ids[j], i, j, xorVal);
				ind++;
			}
		}
		Arrays.sort(poss);
		System.out.println(Arrays.toString(poss));
		
		// calculate optimal total
		
		// greedy?
		ArrayList<Pair> order = new ArrayList<Pair>();
		ind = 0;
		int total = 0;
		while (ind < poss.length && order.size() < N-1) {
			Pair cur = poss[ind];
			int side1 = -1;
			int side2 = -1;
			for (int i = 0; i < order.size(); i++) {
				if (order.get(i).x == cur.x || order.get(i).x == cur.y || 
						order.get(i).y == cur.x || order.get(i).y == cur.y) {
					if (side1 == -1) side1 = i;
					else if (side2 == -1) side2 = i;
					else break;
				}
			}
		}
		
		
		int[] count = new int[N]; // count[i] = # times id i is used
		int maxCount = tourneyLength; // max number of times an id can be used
		
		int matchCount = 0; // number of matches so far
		
		while (ind < poss.length && matchCount < N-1) {
			Pair cur = poss[ind];
			System.out.println("cur: " + cur + " maxCt: " + maxCount + " count: " + Arrays.toString(count));
			if (count[cur.xind] < maxCount && count[cur.yind] < maxCount-1 || 
					count[cur.xind] < maxCount-1 && count[cur.yind] < maxCount) {
				System.out.println("  take it");
//				System.out.println(cur);
				count[cur.xind]++;
				count[cur.yind]++;
				
				int bigger = Math.max(count[cur.xind], count[cur.yind]);
				int smaller = Math.max(count[cur.xind], count[cur.yind]);
				
				if (bigger == maxCount) maxCount--;
				if (smaller == maxCount) maxCount--;
				
				total += cur.val;
				matchCount++;
			}
			ind++;
		}
		System.out.println(total);
		
		// DP?
		// DP[i][j] = max total when eliminating j at round i
		// DP[i][j]
		// DP[i-1][j]
//		int[][] DP = new int[N][N];
		
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("superbull.out")));

		pw.println();
		pw.close();
	}
	public static class Pair implements Comparable<Pair> {
		int x;
		int y;
		int xind;
		int yind;
		int val;
		// for order arraylist
		boolean tiedLeft;
		boolean tiedRight;
		public Pair(int a, int b, int aind, int bind, int c) {
			x = a;
			y = b;
			xind = aind;
			yind = bind;
			val = c;
		}
		@Override
		public int compareTo(Pair o) { // sort by x, then y
			return o.val-val;
		}
		public String toString() {
			return x + " " + y + " " + val;
		}
	}


}
