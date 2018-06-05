import java.io.*;
import java.util.*;

/*
 * USACO 2016 December Contest, Gold
 * Problem 2. Cow Checklist
 * 
 * 1...N indexing
 * 10/10 test cases
 * 
 */

public class Checklist {
	static int[][] hc;
	static int[][] gc;
	
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("checklist.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int H = Integer.parseInt(st.nextToken()); // number of H cows
		int G = Integer.parseInt(st.nextToken()); // number of G cows
		
		hc = new int[H+1][2]; // H cows coordinates
		for (int i = 1; i <= H; i++) {
			st = new StringTokenizer(br.readLine());
			hc[i][0] = Integer.parseInt(st.nextToken());
			hc[i][1] = Integer.parseInt(st.nextToken());
		}
		gc = new int[G+1][2]; // G cows coordinates
		for (int i = 1; i <= G; i++) {
			st = new StringTokenizer(br.readLine());
			gc[i][0] = Integer.parseInt(st.nextToken());
			gc[i][1] = Integer.parseInt(st.nextToken());
		}
		br.close();
		
		// DP array: num H cows visited, num G cows visited, group (0 = H, 1 = G)
		// store the shortest distance to that cow
		int[][][] dp = new int[H+1][G+1][2];
		
		// to show invalid numbers, mark all spots as -1
		for (int i = 0; i <= H; i++) {
			for (int j = 0; j <= G; j++) {
				dp[i][j][0] = -1;
				dp[i][j][1] = -1;
			}
		}
		
		// basecase
		dp[1][0][0] = 0; // haven't left starting point
		
		// fill the array
		for (int i = 1; i <= H; i++) {
			for (int j = 0; j <= G; j++) { // two cases
				

				if (i != 1) { // basecase
					// Case 1: go from H[i-1] to H[i]	
					int op1 = dp[i-1][j][0] + cost(0, i, 0, i-1);
					if (dp[i-1][j][0] == -1) {
						op1 = Integer.MAX_VALUE;
					}
					
					// Case 2: go from G[j] to H[i]
					int op2 = dp[i-1][j][1] + cost(1, j, 0, i);
					if (dp[i-1][j][1] == -1) {
						op2 = Integer.MAX_VALUE;
					}
					
					dp[i][j][0] = Math.min(op1, op2);
				}
				
			
				if (j != 0) { // can't be on a G cow when j == 0
					// Case 1: from an H[i] to G[j]
					int opp1 = dp[i][j-1][0] + cost(0, i, 1, j);
					if (dp[i][j-1][0] == -1) {
						opp1 = Integer.MAX_VALUE;
					}
					
					// Case 2: from G[j-1] to G[j]
					int opp2 = dp[i][j-1][1] + cost(1, j-1, 1, j);
					if (dp[i][j-1][1] == -1) {
						opp2 = Integer.MAX_VALUE;
					}
					
					dp[i][j][1] = Math.min(opp1, opp2);

				}
			}
		}

		System.out.println(dp[H][G][0]);
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("checklist.out")));

		pw.println(dp[H][G][0]); // all H and G visited, end on H
		pw.close();
	}
	public static int cost(int type1, int ind1, int type2, int ind2) { 
		// returns cost of energy to go from cows 1 to 2 (= distance^2)
		// dist = sqrt( (y2-y1)^2 + (x2-x1)^2 );
		
		if (ind1 == 0) ind1 = 1;
		if (ind2 == 0) ind2 = 1;
		
		int x1;
		int y1;
		int x2;
		int y2;
		
		if (type1 == type2 && ind1 == ind2) {
			return 0; // same cow
		}  
		
		if (type1 == 0) { // H cow
			x1 = hc[ind1][0];
			y1 = hc[ind1][1];
		} else { // type1 == 1, G cow
			x1 = gc[ind1][0];
			y1 = gc[ind1][1];
		}
		
		if (type2 == 0) { // H cow
			x2 = hc[ind2][0];
			y2 = hc[ind2][1];
		} else { // type2 == 1, G cow
			x2 = gc[ind2][0];
			y2 = gc[ind2][1];
		}
		
		//System.out.println(x1 + ", " + y1 + "  to  " + x2 + ", " + y2);
		
		return (y2 - y1)*(y2 - y1) + (x2 - x1)*(x2 - x1);
	}


}
