import java.io.*;
import java.util.*;
/*
 * DP version
 * 12/12 test cases, but had to look at the solution 
 * (came up with idea, but couldn't get it to work)
 * (didn't do it by length of interval, also couldn't figure out how to make it so that the interval had to collapse to a single val)
 */
public class Game248DP {
	static int maxVal = 0;
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("248.in"));
		int N = Integer.parseInt(br.readLine());
		int[] arr = new int[N];
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(br.readLine());
		}
		br.close();
		
		// DP[i][j] = maxVal achievable between i and j (inclusive, i <= j, must collapse to 1 number)
		int[][] DP = new int[N][N]; 
		for (int i = 0; i < N; i++) { // start
			for (int j = i; j < N; j++) { // end
				DP[i][j] = -1;
				if (i == j) DP[i][j] = arr[i];
			}
		}
		for(int len = 1; len <= N; len++) {
			for(int i = 0; i + len <= N; i++) {
				int j = i+len-1;
			
		
//				System.out.println("i: " + i + " j: " + j);
				if (j == i) { // basecase
					DP[i][j] = arr[i];
				} 
				
				// loop through the interval (build from previous case)
				for (int k = i; k < j; k++) {
//					System.out.println("  k: " + k + "  " + DP[i][k] + " " + DP[k+1][j]);
					if (DP[i][k] == DP[k+1][j] && DP[k+1][j] != -1) {
						// if front and back sections have same value
//						System.out.println("here");
						DP[i][j] = Math.max(DP[i][k]+1, DP[i][j]);
					}
				}

				maxVal = Math.max(maxVal, DP[i][j]);
			}
		}
		
		
		System.out.println(maxVal);
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("248.out")));

		pw.println(maxVal);
		pw.close();
	}
}
