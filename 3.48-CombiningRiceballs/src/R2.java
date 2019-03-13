import java.io.*;
import java.util.*;
/*
 * (15/15) - much faster than v1 even though both work bc it uses two pointers method (eliminate b loop)
 * CCO 2016 S4
 * 12/29
 * DP
 * did not come up with idea
4
1 50 50 50
 */

public class R2 {
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
		for (int i = 0; i < N; i++) {
			for (int j = i+1; j < N; j++) {
				DPF(i, j);
			}
		}
//		DPF(0, N-1);
		System.out.println(maxSize);
	}
	public static int DPF(int x, int y) {
		if (DP[x][y] != 0) return DP[x][y];
		int val = -1;
		// try to combine regions x...a and b...y and intervening riceballs
		int b = y;
		int r2 = DPF(b,y);
		
al:		for (int a = x; a < y; a++) {
//			System.out.println("[" + x + ", " + a + "]" + "[" + b + ", " + y + "]");
			int r1 = DPF(x,a);
			int curR2 = 0;
			if (b>a) curR2 = DPF(b,y);
			while (b > a && curR2 <= r1) {
				// check if riceballs in range are combineable
				r2 = DPF(b,y);
//				System.out.println("  r1: " + r1 + " r2: " + r2 + " [" + x + ", " + a + "]" + "[" + b + ", " + y + "]");
				
				if (r1 < r2) break; // IMPORTANT: stop decrementing b if r2 gets too big
				
				if (r1==-1) continue al; // if region 1 is not cohesive, go to the next
				if (r2==-1) { // keep going if b region is not cohesive
					b--;
					continue; 
				}
				if (r1 > r2) { // can't combine, need r2 to be bigger
//					System.out.println("    dec b");
					b--;
					continue;
				}
				
				int middle = 0;
				if (a+1 <= b-1) { // if there are intervening riceballs between the two regions
					middle = DPF(a+1, b-1);
					if (middle == -1) { // break if the middle not cohesive
						
						b--;
						continue; 
					}
				}
				
				// if combineable, combine riceballs
				int combined = r1+r2+middle; // middle = 0 if no middle riceball
				if (combined > val) {
					val = combined;
//					System.out.println("  val: " +  val+" [" + x + ", " + a + "]" + "[" + b + ", " + y + "]");
				}
				b--;
				break; // break out of the b loop
			}
		}
		
		DP[x][y] = val;
		maxSize = Math.max(maxSize, val);
		return val;
	}
}
