import java.io.*;
import java.util.*;
/*
 * 10/10 test cases
 * 0 1 knapsack
 * O(N*C)
 */
public class Knapsack {
	static int max = 0;
	public static void main(String[] args) throws IOException {
		// INPUT
		Scanner scan = new Scanner(System.in);
		StringTokenizer st = new StringTokenizer(scan.nextLine());
		int N = Integer.parseInt(st.nextToken()); // number of objects
		int C = Integer.parseInt(st.nextToken()); // max capacity
		int[][] objects = new int[N+1][2];
		for (int i = 1; i <= N; i++) { // scan in objects
			st = new StringTokenizer(scan.nextLine());
			int size = Integer.parseInt(st.nextToken());
			int val = Integer.parseInt(st.nextToken());
			objects[i][0] = size;
			objects[i][1] = val;
		}
		// DP
		// DP[index][capacity] = max value reachable at that capacity
		int[][] DP = new int[N+1][C+1];
		
		for (int i = 1; i <= N; i++) { // loop through objects
			for (int j = 0; j <= C; j++) { // loop through capacity
				int curSize = objects[i][0];
				int curVal = objects[i][1];
				//System.out.println(curSize + " " + curVal);
				// take it
				if (curSize <= j) { // if it fits inside
					//System.out.println("here");
					DP[i][j] = DP[i-1][j-curSize] + curVal;
				}
				// don't take it
				DP[i][j] = Math.max(DP[i][j], DP[i-1][j]);
				if (DP[i][j] > max) max = DP[i][j];
			}
			//System.out.println(Arrays.toString(DP[i]));
		}
		System.out.println(max);
	}
	
}
