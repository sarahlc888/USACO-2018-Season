import java.io.*;
import java.util.*;
/*
 * USACO 2018 February Contest, Gold
 * Problem 3. Taming the Herd
 */
public class TamingTheHerd {
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("taming.in"));
		int N = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		int[] arr = new int[N+1];
		for (int i = 0; i < N; i++) arr[i] = Integer.parseInt(st.nextToken());
		
		br.close();
		
//		System.out.println(Arrays.toString(arr));
		
		
		// [breakouts][day of last breakout] = num changes needed up to and including index j
		int[][] DP = new int[N+2][N+1]; 
		for (int i = 0; i <= N+1; i++) {
			for (int j = 0; j <= N; j++) {
				DP[i][j] = 2*N; // out of bounds upper limiting value
			}
		}
		DP[1][0] = arr[0]==0 ? 0:1;
//		System.out.println(DP[1][0]);
		
		for (int i = 2; i <= N+1; i++) { // number of breakouts
			for (int j = i-1; j <= N; j++) { // day of last breakout
				// DP[i][j] = DP[i-1][j-k] + diff
				
				// day of breakout before that
				for (int k = i-2; k < j; k++) {
					
					if (i == 2 && k != 0) continue; // DP[1][j] invalid for any j except 0
					
					int diff=0;
					for (int m = k+1; m < j; m++) {
						if (arr[m] != m-k) diff++;
					}
					if (arr[j] != 0) diff++;
					
					int val = DP[i-1][k]+diff;
					
//					System.out.println("i: " + i + " j: " + j + " k: " + k + " val: " + val + " diff: " + diff);
					
					DP[i][j] = Math.min(DP[i][j], val);
				}
				
			}
		}
//		for (int i = 1; i <= N; i++) {
//			System.out.println("min: " +  DP[i+1][N]);
//		}
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("taming.out")));
		for (int i = 1; i <= N; i++) {
			pw.println(DP[i+1][N]);
		}
		
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
		public int compareTo(Pair o) { // sort by x, then y
			if (x == o.x) return y-o.y;
			return o.x-x;
		}
		public String toString() {
			return x + " " + y;
		}
	}


}
