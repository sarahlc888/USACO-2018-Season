import java.io.*;
import java.util.*;
/*
 * 10/10 test cases
 * O(N^2)
 */
public class Pinball {
	public static void main(String[] args) throws IOException {
		// INPUT
		Scanner scan = new Scanner(System.in);
		int R = Integer.parseInt(scan.nextLine()); // number of rows
		
		// scan in rows, only filled in a right triangle starting in [1][1]
		int[][] rows = new int[R+1][R+1]; 

		for (int i = 1; i <= R; i++) { // scan in the rows
			StringTokenizer st = new StringTokenizer(scan.nextLine());
			for (int j = 1; j <= i; j++) {
				rows[i][j] = Integer.parseInt(st.nextToken());
			}
			//System.out.println(Arrays.toString(rows[i]));
		}
		
		// DP, stores max score
		int[][] DP = new int[R+1][R+1];
		DP[1][1] = rows[1][1]; // initialize
		// loop through the positions
		int maxscore = 0;
		for (int i = 1; i < R; i++) { // loop through all but the last row
			for (int j = 1; j <= i; j++) { // loop through all j
				// go to the next 2 in the next row
				DP[i+1][j] = Math.max(DP[i+1][j], DP[i][j] + rows[i+1][j]);
				if (j+1 > R) continue;
				DP[i+1][j+1] = Math.max(DP[i+1][j+1], DP[i][j] + rows[i+1][j+1]);
			}
		}
		
		for (int j = 1; j <= R; j++) {
			if (DP[R][j] > maxscore) maxscore = DP[R][j];
		}
		
		System.out.println(maxscore);
	}
}
