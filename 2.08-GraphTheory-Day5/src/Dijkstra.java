import java.io.*;
import java.util.*;
/*
 * 10/10
 * works fully
 * 
 * initialize the lack of an edge as infinity edge weight in the adj matrix
 * bidirectional edges
 * weighted graph
 */
public class Dijkstra {
	static int V; // number of nodes
	static int[][] mat; // stores edge weights
	static int[] dist; // dist from source to node i
	static boolean[] visited;
	
	public static void main(String[] args) throws IOException {
		
		Scanner scan = new Scanner(System.in);
		StringTokenizer st = new StringTokenizer(scan.nextLine());
		V = Integer.parseInt(st.nextToken()); // number of nodes
		int E = Integer.parseInt(st.nextToken()); // number of edges
		int S = Integer.parseInt(st.nextToken()) - 1; // source node, fix indexing


		mat = new int[V][V];
		dist = new int[V];
		visited = new boolean[V];

		for (int i = 0; i < E; i++) { // scan in edges
			st = new StringTokenizer(scan.nextLine());
			int v1 = Integer.parseInt(st.nextToken()) - 1; // fix indexing 
			int v2 = Integer.parseInt(st.nextToken()) - 1; // fix indexing
			int cost = Integer.parseInt(st.nextToken()); 
			// put into graph
			mat[v1][v2] = cost;
			mat[v2][v1] = cost;
		}

		
		for (int i = 0; i < V; i++) {
			for (int j = 0; j < V; j++) {
				if (mat[i][j] == 0) {
					mat[i][j] = Integer.MAX_VALUE;
				}
			}
		}
		dijkstra(S);

		for (int i = 0; i < V; i++) { // loop through distance
			// make lack of edge = -1
			if (dist[i] == Integer.MAX_VALUE) dist[i] = -1;
			System.out.println(dist[i]); // print
		}
		
	}
	public static void dijkstra(int u) { // starting node u
		
		// O(N^2) 
		// for all nodes (only process once each), look at all neighbors
		
		// init dist
		for (int i = 0; i < V; i++) {
			if (i == u) {
				dist[i] = 0;
			} else {
				dist[i] = Integer.MAX_VALUE;
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
			int minD = Integer.MAX_VALUE; // min dist
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
				if (mat[v][w] != Integer.MAX_VALUE) { 
					// if there's an edge and w is a neighbor
					dist[w] = Math.min(dist[w], dist[v] + mat[v][w]);
				}
			}
			
			visited[v] = true;

		}
	}
}
