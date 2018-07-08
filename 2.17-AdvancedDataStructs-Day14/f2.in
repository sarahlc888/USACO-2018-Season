import java.io.*;
import java.util.*;

// N^2?
/*
 * 10/10, full credit, but BARELY
 * binary search(x) on the difference
 * 	for every (i, j) {
 * 		search the four nodes and check if they're wihtin range
 * 	}
 * see if the size is >= N^2 / 2
 */

public class Tractor2 {
	static int N;
	static int[] dx = {1, -1, 0, 0};
	static int[] dy = {0, 0, 1, -1};
	static int[][] grid;
	static int minV = Integer.MAX_VALUE;
	static int maxV = 0;
	static int diff;
	static int i;
	static int j;
	static DisjointSet ds;
	
	public static void main(String args[]) throws IOException {
		// INPUT
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		ds = new DisjointSet(N);
		grid = new int[N][N];

		for (i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (j = 0; j < N; j++) {

				grid[i][j] = Integer.parseInt(st.nextToken());

				if (grid[i][j] < minV) minV = grid[i][j];
				else if (grid[i][j] > maxV) maxV = grid[i][j];

			}
		}
		diff = maxV-minV;

		//long t = System.currentTimeMillis();

		//System.out.println(works(0));
		System.out.println(leastAbove());
		//System.out.println(System.currentTimeMillis()-t);
	}
	public static boolean works(int d) { // return if # reachable cells with cur d val >= (N^2 / 2)

		ds.reset();

		for (i = 0; i < N; i++) {
			for (j = 0; j < N; j++) { // loop through all cells
				for (int k = 1; k <= 2; k++) { // go through 4 neighbors
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

		for (i = 0; i < N; i++) {
			for (j = 0; j < N; j++) {
				if (ds.size[i][j] >= (Math.pow(N, 2) / 2)) return true;
			}
		}
		return false;
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
			for (i = 0; i < n; i++) {
				for (j = 0; j < n; j++) {
					size[i][j] = 1;
				}
			}
			for (i = 0; i < n; i++) {
				for (j = 0; j < n; j++) {
					par[i][j] = new Pair(i, j);
				}
			}
		}
		public void reset() {
			for (i = 0; i < size.length; i++) {
				for (j = 0; j < size[0].length; j++) {
					size[i][j] = 1;
				}
			}
			for (i = 0; i < par.length; i++) {
				for (j = 0; j < par[0].length; j++) {
					par[i][j] = new Pair(i, j);
				}
			}
		}
		public void union(Pair u, Pair v) {
			// log n
			// attach smaller trees to bigger trees. attach smaller root to bigger root
			u = root(u); // optimizations
			v = root(v); // to make find faster
			if (!u.equals(v)) { // if already in same component, do nothing

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
		}

		public Pair root(Pair u) { 
			// formerly O(N).
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
