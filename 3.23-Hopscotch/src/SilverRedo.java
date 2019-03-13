import java.io.*;
import java.util.*;
/*
 * USACO 2015 February Contest, Silver
 * Problem 2. Cow Hopscotch (Silver)
 * 12/31 redo
 */
public class SilverRedo {
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
					grid[i][j] = Integer.parseInt(st.nextToken())%1000000007;
				}
			}
			br.close();
			
			// DP[i][j] = number of ways to get to square i j
			int[][] DP = new int[R][C]; 
			DP[0][0] = 1;
			for (int i = 0; i < R; i++) {
				for (int j = 0; j < C; j++) {
					for (int k = 0; k < i; k++) {
						for (int m = 0; m < j; m++) {
							if (grid[i][j] != grid[k][m]) {
								DP[i][j] += DP[k][m];
								DP[i][j] %= 1000000007;
							}
						}
					}
				}
			}			
			
//			System.out.println(DP[R-1][C-1]);

			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("hopscotch.out")));
			pw.println(DP[R-1][C-1]%1000000007);
			pw.close();
	}
}
