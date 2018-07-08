import java.io.*;
import java.util.*;

// needs to be optimized, 3/10 test cases

public class BestCowLine2 {
	public static void main(String args[]) throws IOException {
		// INPUT
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine()); // num cows
		char[] cows = new char[N+1]; // pad it at the start
		for (int i = 1; i <= N; i++) {
			cows[i] = br.readLine().charAt(0);
		}
		
		// DP[i][j] = optimal new string possible using 0...i and j...N-1

		String[][] DP = new String[N+2][N+2];
		
		DP[0][N+1] = ""; // basecase, redundant
		String best = "a"; // will always come after
		for (int i = 0; i <= N+1; i++) {
			for (int j = N+1; j >= i; j--) {
				
				if (i == 0 && j == N+1) continue; // basecase
				if (i == N+1 && j == N+1) continue; // redundant
				if (i == 0 && j == 0) continue; // redundant
				
//				//System.out.println("i: " + i + " j: " + j);
				if (i-1 >= 0 && j+1 <= N+1) { // left and right
//					//System.out.println("  h1");
					DP[i][j] = minString(DP[i-1][j] + cows[i], DP[i][j+1] + cows[j]).trim();
				} else if (i-1 >= 0) { // left
//					//System.out.println("  h2");
					DP[i][j] = DP[i-1][j]+cows[i];
				} else { // right
//					//System.out.println("  h3");
					DP[i][j] = DP[i][j+1] + cows[j];
				}
//				//System.out.println("  " + DP[i][j]);
				
				if (j == i+1) {
					if (DP[i][j] != "" && DP[i][j].length() == N) {
						best = minString(best, DP[i][j]).trim();
					}
				}
				System.out.println(DP[i][j].length());
				
			}
			//System.out.println("i: " + i);
		}
		
		//System.out.println(best.length());
		//System.out.println(best.charAt(1));
		int ind1 = 0;
		int ind2 = 80;
		while (ind2 <= best.length()) {
			System.out.println(best.substring(ind1, ind2));
			
			ind1 += 80;
			ind2 += 80;
		}
		System.out.println(best.substring(ind1));

		//System.out.println(best);
		
	}
	
	public static String minString(String a, String b) {
		if (a.compareTo(b) < 0) return a;
		else return b;
	}
}
