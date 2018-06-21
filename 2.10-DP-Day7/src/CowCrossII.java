import java.io.*;
import java.util.*;
/*
 * USACO 2017 February Contest, Gold
 * Problem 2. Why Did the Cow Cross the Road II
 * 
 * 10/10 test cases
 * copied from 1.57
 * 
 * build a crosswalk or don't
 * 
 * dp[i][j] = max num crosswalks using a[1...i] and b[1...j]
 * 
 * O(N^2)
 */
public class CowCrossII {
	public static void main(String args[]) throws IOException {

		Scanner br = new Scanner((System.in));
		int N = Integer.parseInt(br.nextLine());
		int[] A = new int[N+1];
		for (int i = 1; i <= N; i++) {
			A[i] = Integer.parseInt(br.nextLine());
		}
		int[] B = new int[N+1];
		for (int i = 1; i <= N; i++) {
			B[i] = Integer.parseInt(br.nextLine());
		}
		br.close();
		
		int[][] DP = new int[N+1][N+1];
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				//System.out.println("i: " + i + " j: " + j + " vals: " + A[i] + "  " + B[i]);
				if (Math.abs(A[i] - B[j]) <= 4) { // friendly, so building a crosswalk
					
					DP[i][j] = DP[i-1][j-1] + 1;
					//System.out.println("  yes " + DP[i][j]);
				} else { // not friendly, so no crosswalk, get stuff from prev
					// don’t drop the cases and just do the same thing w/o +1,
					// must consider if the letters are uneven, so compare the new i 
					// or j with a previous index i-1 or j-1 in the DP
					DP[i][j] = Math.max(DP[i][j-1], DP[i-1][j]);
					//System.out.println("  no " + DP[i][j]);
				}
			}
		}
		//System.out.println(DP[N][N]);
		

		System.out.println(DP[N][N]);
	}


}
