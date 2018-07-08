import java.io.*;
import java.util.*;

// N^2?
/*
 * 2/10, times out
 * DSU
 * binary search(x) on the difference
 * 	for every (i, j) {
 * 		search the four nodes and check if they're wihtin range
 * 	}
 * see if the size is >= N^2 / 2
 */

public class Tractor {
	static int N;
	static int[] dx = {1, -1, 0, 0};
	static int[] dy = {0, 0, 1, -1};
	static int[][] grid;
	static int diff;

	public static void main(String args[]) throws IOException {
		// INPUT
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		
		grid = new int[N][N];
		
		int minV = Integer.MAX_VALUE;
		int maxV = 0;
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				int a = Integer.parseInt(st.nextToken());
				grid[i][j] = a;
				minV = Math.min(minV, a);
				maxV = Math.max(maxV, a);
			}
		}
		diff = maxV-minV;

		long t = System.currentTimeMillis();
		
		//System.out.println(works(0));
		System.out.println(leastAbove());
		//System.out.println(System.currentTimeMillis()-t);
	}
	public static boolean works(int d) { // return if # reachable cells with cur d val >= (N^2 / 2)

		DisjointSet ds = new DisjointSet((int)(Math.pow(N, 2)));

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) { // loop through all cells
				for (int k = 0; k < 4; k++) { // go through 4 neighbors
					int nx = i+dx[k];
					int ny = j+dy[k];

					if (nx < 0 || nx >= N || ny < 0 || ny >= N) continue; // catch bounds

					// connect the ones with height diff in range
					if (Math.abs(grid[i][j]-grid[nx][ny]) <= d) { 
						// connect in the DSU
						ds.union(new Pair(i, j), new Pair(nx, ny));
					} 
				}
			}
		}
		
		int count = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				count = Math.max(count, ds.size[i][j]);
				if (count >= (Math.pow(N, 2) / 2)) return true;
			}
		}

		return count >= (Math.pow(N, 2) / 2);
	}
	public static int leastAbove() {
		// returns smallest i that works as the d
		int lo = 0;
		int hi = diff; // max valid difference

		while (lo < hi) {
			int mid = (lo + hi)/2; // no +1 because you want hi to become lo in 1st if
//			System.out.println("lo: " + lo + " hi: " + hi + " mid: " + mid);

			if (works(mid)) { // if mid is in range, make it lower
				hi = mid;
			} else { // mid is not in range
				lo = mid + 1;
			}
		}
		return lo;
	}
	public static class DisjointSet {
		Pair[][] par; // returns parent of i j
		int[][] size; // store the size of tree from root i j, init as 1
		public DisjointSet(int n) { // arg = size
			par = new Pair[n][n];
			size = new int[n][n];
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					size[i][j] = 1;
				}
			}
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					par[i][j] = new Pair(i, j);
				}
			}
		}
		public void union(Pair u, Pair v) {
			// log n
			// attach smaller trees to bigger trees. attach smaller root to bigger root
			u = root(u); // optimizations
			v = root(v); // to make find faster
			if (u.equals(v)) return; // already in same component
			
			//System.out.println("    connect");
			
			if (size[u.x][u.y] >= size[v.x][v.y]) {
				par[v.x][v.y] = u;
				size[u.x][u.y] += size[v.x][v.y];
			//	System.out.println("  new size: " + size[u.x][u.y]);

			} else {
				par[u.x][u.y] = v;
				size[v.x][v.y] += size[u.x][u.y];
			//	System.out.println("  new size: " + size[v.x][v.y]);

			}
		}
		public boolean find(Pair u, Pair v) {
			return root(u).equals( root(v) );
		}
		public Pair root(Pair u) { 
			// formerly up to O(N).
			// To optimize, minimized depth by attaching stuff to the root (in union).

			// O(log*n) (basically constant time)
			// means the number of times you need to log to get to root

			while (!u.equals(par[u.x][u.y])) { // while u is not it's own parent
				// path compression!
				par[u.x][u.y] = par[par[u.x][u.y].x][par[u.x][u.y].y]; // makes distance to root shorter
				u = par[u.x][u.y];

			}
			return u;
		}
	}

	public static class Pair implements Comparable<Pair> {
		int x; 
		int y; 

		public Pair(int a, int b) {
			x = a;
			y = b;
		}
		@Override
		public int compareTo(Pair o) { // sort by value
			if (x == o.x) {
				return o.y-y;
			}
			return x-o.x;
		}
		public String toString() {
			return "x: " + x + " y: " + y ;
		}
		public boolean equals(Pair o) {
			return (x == o.x && y == o.y);
		}

	}
}
