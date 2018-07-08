import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;


/*
 * 10/10 test cases
 * 
 * get all pairs shortest path by djikstra from every path
 * V = N^2 nodes
 * E = 4 N^2 / 2
 * djikstra from one source = E log V, will fit in time
 */
public class DistantPastures {
	
	static int maxDist = 0;
	
	static int cost = 0;
	// for getting neighbors
	static int[] dx = {0, 0, -1, 1}; 
	static int[] dy = {-1, 1, 0, 0};

	// djikstra stuff
	static int[][] dist;
	static boolean[][] visited; 
	
	static int N;
	static int A;
	static int B;

	static int[][] grid;
	public static void main(String args[]) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // N by N grid 
		A = Integer.parseInt(st.nextToken()); // time to go to same grass type
		B = Integer.parseInt(st.nextToken()); // time to go to diff grass type

		grid = new int[N][N];
		for (int i = 0; i < N; i++) { // scan in grid
			String a = br.readLine();
			for (int j = 0; j < N; j++) {
				grid[i][j] = Integer.MAX_VALUE; 
				if (a.charAt(j) == '(') grid[i][j] = 0;
				if (a.charAt(j) == ')') grid[i][j] = 1;
			}
		}
		
		// djikstra from all pastures
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				dij(new Point(i, j));
			}
		}
		System.out.println(maxDist);
	}
	public static void dij(Point S) { // S = starting node
		
		visited = new boolean[N][N];
		dist = new int[N][N];
		
		PriorityQueue<Pair> pq = new PriorityQueue<Pair>();
		// add the edges that cross from S to VS
		pq.add(new Pair(0, S)); // weight, destination
		while (!pq.isEmpty()) {
			//System.out.println(Arrays.toString(dist));
			// let (weight, node) be at top
			Pair p = pq.poll();
			int weight = p.x;
			Point node = p.y;
			int nx = node.x;
			int ny = node.y;

			// check if node is visited
			if (visited[nx][ny]) {
				continue; // if already visited, don't add it
			} else {
				visited[nx][ny] = true; // mark visited and proceed
			}

			dist[nx][ny] = weight; // assign weight
			if (weight > maxDist) maxDist = weight;

			// look at the neighbors and push to pq
			for (int i = 0; i < 4; i++) {
				if (nx + dx[i] < 0 || nx + dx[i] >= N || ny + dy[i] < 0 || ny + dy[i] >= N) {
					continue; // out of bounds
				}
				Point neigh = new Point(nx + dx[i], ny + dy[i]); // neighbor
				
				if (grid[nx + dx[i]][ny + dy[i]] == grid[nx][ny]) {
					// if same type of grass
					pq.add(new Pair(A + dist[nx][ny], neigh)); // add to PQ
				} else {
					pq.add(new Pair(B + dist[nx][ny], neigh)); // add to PQ
				}
			}

			// add weight to the MST
			cost += weight;
		}
	}
	public static class Point {
		int x;
		int y;
		public Point(int a, int b) {
			x = a;
			y = b;
		}
	}
	public static class Pair implements Comparable<Pair> {
		int x; // weight
		Point y; // dest
		public Pair(int a, Point b) {
			x = a;
			y = b;
		}
		@Override
		public int compareTo(Pair o) { // sort by edge
			return x-o.x;
		}

	}
}
