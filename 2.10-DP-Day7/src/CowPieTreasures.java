import java.io.*;
import java.util.*;

/*
 * 10/10
 * DP[r][c] = max # coins you can get by the time you reach that spot from (1, 1)
 * DP[r][c] = max(DP[r][c-1], DP[r-1][c-1], DP[r+1][c-1]) + coins[r][c])
 * O(R*C)
 * I came up with this yay!
 */
public class CowPieTreasures {
	public static void main(String[] args) throws IOException {
		Scanner scan = new Scanner(System.in);
		StringTokenizer st = new StringTokenizer(scan.nextLine());
		int R = Integer.parseInt(st.nextToken()); // num rows
		int C = Integer.parseInt(st.nextToken()); // num cols
		int[][] coins = new int[R][C];
		for (int i = 0; i < R; i++) {
			st = new StringTokenizer(scan.nextLine());
			for (int j = 0; j < C; j++) {
				coins[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		int[][] DP = new int[R][C];
		// basecases
		DP[0][0] = coins[0][0];
		/*
		for (int c = 0; c < C; c++) {
			for (int r = 0; r < R; r++) {
				DP[r][c] = coins[r][c];
			}
		}*/
		
		for (int c = 1; c < C; c++) {
			for (int r = 0; r < R; r++) {
				// three possible source points
				//System.out.println("r: " + r + " c: " + c);
				if (DP[r][c-1] != 0)  // if former is initialized
					DP[r][c] = DP[r][c-1];
				
			
				if (r > 0 && DP[r-1][c-1] != 0) 
					DP[r][c] = Math.max(DP[r][c], DP[r-1][c-1]);
				if (r < R - 1 && DP[r+1][c-1] != 0) 
					DP[r][c] = Math.max(DP[r][c], DP[r+1][c-1]);
				
				if (DP[r][c] != 0)
					DP[r][c] += coins[r][c];
			}
		}
		/*for (int r = 0 ; r < R; r++) {
			System.out.println(Arrays.toString(DP[r]));
		}*/
		System.out.println(DP[R-1][C-1]);

	}
}
