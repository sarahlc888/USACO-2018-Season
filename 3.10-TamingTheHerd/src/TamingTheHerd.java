import java.io.*;
import java.util.*;
/*
 * in contest code in 1.47 (no actual code included there)
 * USACO 2018 February Contest, Gold
 * Problem 3. Taming the Herd
 * 
 * dynamic programming: at each step either break out or don't break out
 * 
 * 11/11 test cases
 * about 20 minutes
 * 
 * had to do the DP where the last breakout was exactly at step j
 * not my original idea
 * 
 * 7/31 lesson
 */
public class TamingTheHerd {
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("taming.in"));
		int N = Integer.parseInt(br.readLine()); // num days in log
		StringTokenizer st = new StringTokenizer(br.readLine());
		int[] log = new int[N];
		for (int i = 0; i < N; i++) { // scan everything in
			log[i] = Integer.parseInt(st.nextToken());
		}
		br.close();

//		System.out.println(Arrays.toString(log));
		
		// DP[i][j] = num changes needed to have i breakouts up to and including index j
		// where the last breakout occurs AT index j
		int[][] DP = new int[N+2][N+1]; // extra index j at end for answering purposes
		for (int j = 0; j <= N; j++) { // max index
			for (int i = 1; i <= j+1; i++) { // num breakouts
//				System.out.println("i: " + i + " j: " + j);
				DP[i][j] = 1000;
				/*// old stuff from where breakout did not have to be at j
				// case 1: breakout at j
				DP[i][j] = DP[i-1][j-1];
				if (log[j] != 0) DP[i][j]++; // if you have to alter index j
				// case 2: no breakout at j
				DP[i][j] = Math.min(DP[i][j-1], DP[i][j]);
				// TODO: fix this problem! can't tell what index j and j-1 
				// are so can't tell if need to +1 or not
				*/
				if (j == 0 && i == 1) { // basecase (breakout @ index 0)
					DP[i][j] = (log[j] == 0 ? 0 : 1);
//					System.out.println("  val: " + DP[i][j]);
					continue;
				}
				if (i == 1 && j != 0) { // MUST have the first breakout @ j
					continue;
				}
//				System.out.println(i-1 + " " + (j-1));
				int k = 1;
				while (true) {
					if (k > j-i+2 || j-k < 0) break; // catch out of bounds
					// j-k >= i-2 so k <= j-i+2 when valid
					int newVal = DP[i-1][j-k]; // prev breakout @ j-k, so j-k = 0
					/* log[j-k] = 0
					 * log[j-k+1] = 1
					 * log[j-k+2] = 2
					 * log[j-k+k = j] = k
					*/
					for (int m = 1; m < k; m++) { // if the things don't match up
						if (log[j-k+m] != m) newVal++;
					}
					if (j < N && log[j] != 0) newVal++;
					if (newVal < DP[i][j]) {
						DP[i][j] = newVal;
//						System.out.println("  breakout at: " + (j-k));
//						System.out.println("    DP[" + (i-1) + "][" + (j-1) + "]: " + DP[i-1][j-k]);
					}
					
					k++;
				}
//				System.out.println("  VAL: " + DP[i][j]);
			}
		}
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("taming.out")));

		for (int i = 1; i <= N; i++) { 
//			System.out.println(DP[i+1][N]);
			pw.println(DP[i+1][N]);
		}
//		pw.println();
		pw.close();
	}


}

