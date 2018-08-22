import java.io.*;
import java.util.*;
/*
 * USACO 2015 February Contest, Silver
 * Problem 2. Cow Hopscotch (Silver)
 * 
 * 5 minutes, all test cases correct
 * O(R^2 * C^2) but doesn't time out, max is about 1.1 second
 * 
 * naive idea:
 * numWays[i][j] = numways to get to square (i, j)
 * numWays[0][0] = 1
 * for i, j < R, C:
 * 	if (numWays[i][j] == 0) continue;
 * 	for a = i+1, b = j+1 < R, C:
 * 		if (grid[i][j] != grid[a][b]) numWays[a][b] += numWays[i][j]
 * 
 * this idea: similar thing except backwards (same complexity though)
 * Rather than incrementing all a and b, mark DP[i][j] = c
 * to represent all DP[a][b] += c. And then when accessing DP[a][b],
 * look back on all previous i, j pairs and then add to calc the "true" value
 * 
 */
public class Silver {
	static int MOD = 1000000007;
	public static void main(String args[]) throws IOException {

		// INPUT
		BufferedReader br = new BufferedReader(new FileReader("hopscotch.in"));

		StringTokenizer st = new StringTokenizer(br.readLine());
		int R = Integer.parseInt(st.nextToken()); // rows
		int C = Integer.parseInt(st.nextToken()); // cols
		int K = Integer.parseInt(st.nextToken()); // ids from 1...K

		int[][] grid = new int[R][C];
		for (int i = 0; i < R; i++) { // scan everything in
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < C; j++) {
				grid[i][j] = Integer.parseInt(st.nextToken());
			}
			System.out.println(Arrays.toString(grid[i]));
		}
		br.close();
		
		System.out.println();

		// DP[i][j] = sum of DP[a][b] for all a < i and b < j (if color is different)
		long[][] DP = new long[R][C];
		DP[0][0] = 1; // basecase
		
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				if (i == 0 && j == 0) continue; // basecase
				
				for (int a = 0; a < i; a++) {
					for (int b = 0; b < j; b++) {
						if (grid[i][j] == grid[a][b]) continue; // same color, skip
						DP[i][j] += DP[a][b];
						DP[i][j] %= MOD;
					}
				}
				
			}
			System.out.println(Arrays.toString(DP[i]));
		}
		
		
//		System.out.println(DP[R-1][C-1]);
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("hopscotch.out")));

		pw.println(DP[R-1][C-1]);
		pw.close();
	}
}
