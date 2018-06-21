import java.io.*;
import java.util.*;

/*
 * 
 * DP[x][y] = max points if end at node x from previous node y
 * loop through all nodes after x and do it
 * DP[x+i][x] = DP[x][y] + p[x+i]
 * 
 * bottom up
 * 
 * O(N^3) optimized for O(N^2)
 * (i to y to x)
 * for all i so that pos[x]-pos[u] >= pos[y]-pos[i]
 * DP[x][y] = max of all the i's so (DP[y][i] + pts[x])
 * aka DP[x][y] = pts[x] + max of (DP[y][i]s)
 * so you want to get the max of the DPs
 * 
 * keep y constant and then move x bc then it's hard to keep track of all the DPs
 * fix y in place.
 * when x moves forward, the i range expands left. So keep a local max from ileft to y
 * y is a fixed endpoint
 * keep an array for the maximum from some var k to y (suffix maximums) for DP[y][i]
 * 
 * precompute the row before you use, sweep left
 * suffmax[x][y] = max(dp[y][x] to dp[y][y]
 * 
 * then loop over all pairs as starting points
 * 
 */
public class Pogo3 {
	static int max = 0;
	public static void main(String[] args) throws IOException {
		Scanner scan = new Scanner(System.in);
		
		int N = Integer.parseInt(scan.nextLine()); // number of targets
		
		
		Point[] targets = new Point[N]; // number of targets
		
		for (int i = 0; i < N; i++) { // scan in targets (pos, val)
			StringTokenizer st = new StringTokenizer(scan.nextLine());
			int p = Integer.parseInt(st.nextToken());
			int val = Integer.parseInt(st.nextToken()); 
			targets[i] = new Point(p, val);
		}
		
		Arrays.sort(targets);
		
		System.out.println(Arrays.toString(targets));
		int[] pos = new int[N];
		int[] pts = new int[N];
		
		for (int i = 0; i < N; i++ ) {
			pos[i] = targets[i].x;
			pts[i] = targets[i].y;
		}
		
		System.out.println(Arrays.toString(pos));
		System.out.println(Arrays.toString(pts));
		
		// JUMPING LEFT TO RIGHT, y < x
		int[][] dp = new int[N][N];
		int[][][] suffmax = new int[N][N][N]; // suffmax[b][y][a] = max(dp[b][y] to dp[b][a])
		
		for (int y = 0; y < N; y++) { // previous point
			dp[y][y] = pts[y]; // basecase
			
			int leftEdge = y;
			
			suffmax[y][y][y] = dp[y][y];
			
			for (int x = N-1; x >= y; x--) { // current point
				// assign the value as pts + max of the jumps from y to i
				
				System.out.println("x: " + x + " y: " + y);
				
				if (leftEdge >= 0 && pos[x]-pos[y] >= pos[y]-pos[leftEdge]) {
					// while leftEdge creates a plausible path from le to y to x
					
					leftEdge--;
				}
				if (leftEdge < 0 || !(pos[x]-pos[y] >= pos[y]-pos[leftEdge])) {
					// if it doesn't work, correct because the loop went one too far
					leftEdge++;
				}
				
				System.out.println("  left edge: " + leftEdge);
				System.out.println("  suffmax: " + suffmax[y][leftEdge][y]);
				dp[x][y] = Math.max(dp[x][y], pts[x] + suffmax[y][leftEdge][y]); // update dp
				
				
				dp[y][y] = pts[y]; // basecase
				System.out.println("  val: " + dp[x][y]);
				if (dp[x][y] > max) {
					System.out.println("!!!new max");
					max = dp[x][y];
				}
				
				// update suffmax with new info: DP[x][y]
				if (y == 0) continue;
				//suffmax[x][y][x] = Math.max(suffmax[x][y][x-1], dp[x][y]);
				suffmax[x][y][x] = Math.max(suffmax[x][y-1][x], dp[x][y]);
				System.out.println("  suffmax[" + x + "][" + y + "] :  " + suffmax[x][y]);
			}
		}
		
		


		// JUMPING RIGHT TO LEFT, y > x
		// reverse the array
		int[] pos2 = new int[N];
		int[] pts2 = new int[N];
		for (int i = 0; i < N; i++) { // reverse the order
			pos2[i] = pos[N-i-1];
			pts2[i] = pts[N-i-1];
		}
		
		System.out.println(Arrays.toString(pos2));
		System.out.println(Arrays.toString(pts2));
		
		dp = new int[N][N];
		suffmax = new int[N][N][N]; // suffmax[y][a] = max(dp[y][a] to dp[y][y])
		
		for (int y = 0; y < N; y++) { // previous point
			dp[y][y] = pts2[y]; // basecase
			
			int leftEdge = y;
			
			suffmax[y][y][y] = dp[y][y];
			
			for (int x = y; x < N; x++) { // current point
				// assign the value as pts + max of the jumps from y to i
				
				dp[y][y] = pts2[y]; // basecase
				
				System.out.println("x: " + x + " y: " + y);
				
				if (leftEdge >= 0 && Math.abs(pos2[x]-pos2[y]) >= Math.abs(pos2[y]-pos2[leftEdge])) {
					// while leftEdge creates a plausible path from le to y to x
					
					leftEdge--;
					
					
				}
				if (leftEdge < 0 || !(Math.abs(pos2[x]-pos2[y]) >= Math.abs(pos2[y]-pos2[leftEdge]))) {
					// if it doesn't work, correct because the loop went one too far
					leftEdge++;
				}
				
				System.out.println("  left edge: " + leftEdge);
				
				System.out.println("  suffmax: " + suffmax[y][leftEdge]);
				dp[x][y] = Math.max(dp[x][y], pts2[x] + suffmax[y][leftEdge][y]); // update dp
				dp[y][y] = pts2[y]; // basecase
				
				
				System.out.println("  val: " + dp[x][y]);
				if (dp[x][y] > max) {
					System.out.println("!!!new max");
					max = dp[x][y];
				}
				
				// update suffmax with new info: DP[x][y]
				if (y == 0) continue;
				//suffmax[x][y][x] = Math.max(suffmax[x][y][x-1], dp[x][y]);
				suffmax[x][y][x] = Math.max(suffmax[x][y-1][x], dp[x][y]);

			}
		}
		
		
		System.out.println(max);
		
	}
	public static class Point implements Comparable<Point> {
		int x;
		int y;
		public Point(int a, int b) {
			x = a;
			y = b;
		}
		@Override
		public int compareTo(Point o) {
			if (x == o.x) {
				return y-o.y;
			}
			return x-o.x;
		}
		public String toString() {
			return "(" + x + ", " + y + ")";
		}
		
	}
}
