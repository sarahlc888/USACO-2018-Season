import java.io.*;
import java.util.*;
/*
 * (15/15)
 * CCO 2016 S4
 * 12/29
 * DP
 * did not come up with idea
 * n^4 optimized
 */

public class Riceball {
	static int[][] DP;
	static int maxSize = 0;
	public static void main(String args[]) throws IOException {

		Scanner scan = new Scanner(System.in);
		int N = Integer.parseInt(scan.nextLine());
		int[] rice = new int[N]; // size of riceball at pos i
		StringTokenizer st = new StringTokenizer(scan.nextLine());
		for (int i = 0; i < N; i++) {
			rice[i] = Integer.parseInt(st.nextToken());
		}
		scan.close();

		// DP[i][j] = max size of riceball between i and j inclusive
		DP = new int[N][N]; // 0 means unvisited, -1 means uncombineable
		
		// basecase: original riceballs
		for (int i = 0; i < N; i++) {
			DP[i][i] = rice[i];
			maxSize = Math.max(maxSize, DP[i][i]);
		}
		DPF(0, N-1);
		System.out.println(maxSize);
	}
	public static int DPF(int x, int y) {
		if (DP[x][y] != 0) return DP[x][y];
		int val = -1;
		// try to combine regions x...a and b...y and intervening riceballs
al:		for (int a = x; a < y; a++) {
			int r1 = DPF(x,a);
	
			for (int b = a+1; b <= y; b++) { 
//				System.out.println("  [" + x + ", " + a + "]" + "[" + b + ", " + y + "]");
				int middle = 0;
				if (a+1 <= b-1) { // if there are intervening riceballs between the two regions
					middle = DPF(a+1, b-1);
					if (middle == -1) continue; // break if the middle not cohesive
				}
				// check if riceballs in range are combineable
				
				int r2 = DPF(b,y);
				if (r1==-1) continue al;
				if (r2==-1) continue; // break if either region is not cohesive
				if (r1 != r2) continue;
		
				// if combineable, combine riceballs
				int combined = r1+r2+middle; // middle = 0 if no middle riceball
				if (combined > val) {
					val = combined;
				}
				
			}
		}
		DP[x][y] = val;
		maxSize = Math.max(maxSize, val);
		return val;
	}
}
