import java.io.*;
import java.util.*;
/*
 * 10/10
 * uses Djikstra
 * 
 */
public class PowerFailure {
	static int V; // number of nodes
	static double[][] mat; // stores edge weights
	static double[] dist; // dist from source to node i
	static boolean[] visited;
	static int[][] pos; // stores x y positions of the nodes
	
	public static void main(String[] args) throws IOException {
		
		Scanner scan = new Scanner(System.in);
		StringTokenizer st = new StringTokenizer(scan.nextLine());
		V = Integer.parseInt(st.nextToken()); // number of nodes
		int E = Integer.parseInt(st.nextToken()); // number of edges
	
		double M = Double.parseDouble(scan.nextLine()); // max edge length
		
		mat = new double[V][V];
		pos = new int[V][2]; 
		dist = new double[V];
		visited = new boolean[V];

		
		for (int i = 0; i < V; i++) { // scan in positions of the nodes
			st = new StringTokenizer(scan.nextLine());
			int x = Integer.parseInt(st.nextToken()) - 1; // fix indexing 
			int y = Integer.parseInt(st.nextToken()) - 1; // fix indexing
			
			pos[i][0] = x;
			pos[i][1] = y;
		}
		
		
		for (int i = 0; i < V; i++) {
			for (int j = 0; j < V; j++) {
				mat[i][j] = dist(i, j);
				if (mat[i][j] > M) mat[i][j] = Double.MAX_VALUE;
			}
		}
		
		for (int i = 0; i < E; i++) { // scan in edges
			st = new StringTokenizer(scan.nextLine());
			int v1 = Integer.parseInt(st.nextToken()) - 1; // fix indexing 
			int v2 = Integer.parseInt(st.nextToken()) - 1; // fix indexing
			
			// if the connection already exists, make weight 0
			mat[v1][v2] = 0;
			mat[v2][v1] = 0;
		}

		dijkstra(0); // start from pole 0, get to pole N-1

		// make lack of edge = -1
		if (dist[V-1] == Double.MAX_VALUE)  {
			dist[V-1] = -1;
			System.out.println(-1);
		} else {
			System.out.println((int) (dist[V-1] * 1000)); // print

		}


	}
	public static void dijkstra(int u) { // starting node u
		
		// O(N^2) for all nodes (only process once each), look at all neighbors
		
		// init dist
		for (int i = 0; i < V; i++) {
			if (i == u) {
				dist[i] = 0;
			} else {
				dist[i] = Double.MAX_VALUE;
			}
		}
		
		// check if all the ones are visited
		boolean allVisited = true;
		
		for (int i = 0; i < V; i++) {
			if (!visited[i]) {
				allVisited = false;
			}
		}
		while (!allVisited) { // not all nodes visited
			//System.out.println(Arrays.toString(dist));
			//System.out.println(Arrays.toString(visited));
			
			// int v = node so that dist[v] is min and v is unvisited
			
			int v = -1; // current node
			double minD = Double.MAX_VALUE; // min dist
			for (int i = 0; i < V; i++) {
				// loop through all the nodes
				if (!visited[i] && dist[i] < minD) {
					// if not visited and smaller distance
					minD = dist[i];
					v = i;
				}
			}
			
			if (v == -1) break; // if there's nothing more
			//System.out.println(v);
			
			// update dist[] for neighbors of v
			
			for (int w = 0; w < V; w++) { // loop through neighbors
				if (mat[v][w] != Double.MAX_VALUE) { 
					// if there's an edge and w is a neighbor
					dist[w] = Math.min(dist[w], dist[v] + mat[v][w]);
				}
			}
			
			visited[v] = true;

		}
	}
	public static double dist(int i, int j) {
		// distance between pasture i and j
		return Math.sqrt( Math.pow(pos[i][0] - pos[j][0], 2) + 
				Math.pow(pos[i][1] - pos[j][1], 2));
	}
}
