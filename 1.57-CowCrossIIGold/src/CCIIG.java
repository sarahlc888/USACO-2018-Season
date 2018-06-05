import java.io.*;
import java.util.*;
/*
 * USACO 2017 February Contest, Gold
 * Problem 2. Why Did the Cow Cross the Road II
 * 
 * 10/10 test cases
 * ~8 min
 */
public class CCIIG {
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("nocross.in"));
		int N = Integer.parseInt(br.readLine());
		int[] A = new int[N+1];
		for (int i = 1; i <= N; i++) {
			A[i] = Integer.parseInt(br.readLine());
		}
		int[] B = new int[N+1];
		for (int i = 1; i <= N; i++) {
			B[i] = Integer.parseInt(br.readLine());
		}
		br.close();
		
		int[][] DP = new int[N+1][N+1];
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				//System.out.println("i: " + i + " j: " + j + " vals: " + A[i] + "  " + B[i]);
				if (Math.abs(A[i] - B[j]) <= 4) { 
					
					DP[i][j] = DP[i-1][j-1] + 1;
					//System.out.println("  yes " + DP[i][j]);
				} else {
					
					// don’t drop the cases and just do the same thing w/o +1, must consider if the letters are uneven, so compare the new index i or j with a previous index i-1 or j-1 in the DP
					DP[i][j] = Math.max(DP[i][j-1], DP[i-1][j]);
					//System.out.println("  no " + DP[i][j]);
				}
			}
		}
		//System.out.println(DP[N][N]);
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("nocross.out")));

		pw.println(DP[N][N]);
		pw.close();
	}


}
