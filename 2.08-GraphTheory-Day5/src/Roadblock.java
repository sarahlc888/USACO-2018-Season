import java.io.*;
import java.util.*;
/*
 * 
 * uses dijkstra
 * recovers the actual shortest path
 * 
 * 
 * for ever node, keep a "parent" where it's the one that's before it on the path
 * 
 * 10/10
 */
public class Roadblock {
	static int V; // number of nodes
	static int[][] mat; // stores edge weights
	static int[] dist; // dist from source to node i
	static boolean[] visited;
	static int[] prev; // the node previous to node i in the shortest path
	
	public static void main(String[] args) throws IOException {
		
		Scanner scan = new Scanner(System.in);
		StringTokenizer st = new StringTokenizer(scan.nextLine());
		V = Integer.parseInt(st.nextToken()); // number of nodes
		int E = Integer.parseInt(st.nextToken()); // number of edges

		mat = new int[V][V];
		dist = new int[V];
		prev = new int[V];
		prev[0] = -1; // start at the source, no previous
		visited = new boolean[V];

		int[][] edges = new int[E][3]; // store edges
		
		for (int i = 0; i < E; i++) { // scan in edges
			st = new StringTokenizer(scan.nextLine());
			int v1 = Integer.parseInt(st.nextToken()) - 1; // fix indexing 
			int v2 = Integer.parseInt(st.nextToken()) - 1; // fix indexing
			int cost = Integer.parseInt(st.nextToken()); 
			// put into graph, bidirectional
			mat[v1][v2] = cost;
			mat[v2][v1] = cost;
			// store the edges
			edges[i][0] = v1;
			edges[i][1] = v2;
			edges[i][2] = cost;
		}

		
		for (int i = 0; i < V; i++) {
			for (int j = 0; j < V; j++) {
				if (mat[i][j] == 0) {
					mat[i][j] = Integer.MAX_VALUE;
				}
			}
		}
		
		int ogDist = 0;
		
		dijkstra(0); // source = node 0
		
		ogDist = dist[V-1]; // original dist of shortest path
		
		//System.out.println(ogDist);
		
		int maxDiff = 0;
		
		// only double the edges on the original shortest path
		
		/*
		 * old version, bash:
		for (int i = 0; i < E; i++) { // loop through which edge to block
			visited = new boolean[V]; // reset visited
			
			edges[i][2] *= 2; // double the cost
			
			mat[edges[i][0]][edges[i][1]] = edges[i][2];
			mat[edges[i][1]][edges[i][0]] = edges[i][2];
			
			dijkstra(0); // source = node 0
			
			if (dist[V-1] != Integer.MAX_VALUE) { // see if this is the biggest block
				maxDiff = Math.max(maxDiff, Math.abs(dist[V-1]-ogDist));
			}
			
			// undouble the cost
			edges[i][2] /= 2;
			mat[edges[i][0]][edges[i][1]] = edges[i][2];
			mat[edges[i][1]][edges[i][0]] = edges[i][2];
			
			//System.out.println(Arrays.toString(dist));
		}*/
		
		for (int i = 1; i < V; i++) { 
			// loop through the edges to block
			
			int a = i; // v1
			int b = prev[i]; // v2
			
			mat[a][b] *= 2; // double the cost
			mat[b][a] *= 2; // double the cost
			
			visited = new boolean[V]; // reset visited
			dijkstra(0); // source = node 0
			
			if (dist[V-1] != Integer.MAX_VALUE) { // see if this is the biggest block
				maxDiff = Math.max(maxDiff, Math.abs(dist[V-1]-ogDist));
			}
			
			// undouble the cost
			mat[a][b] /= 2; // double the cost
			mat[b][a] /= 2; // double the cost
			
			//System.out.println(Arrays.toString(dist));
		}
		
		System.out.println(maxDiff);
	
		
	}
	public static void dijkstra(int u) { // starting node u
		
		// O(N^2) for all nodes (only process once each), look at all neighbors
		
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
					// check new path dist[v] + mat[v][w]
					if (dist[v] + mat[v][w] < dist[w]) { // if it's a shorter path
						dist[w] = dist[v] + mat[v][w];
						prev[w] = v; // mark v as the node before w
					}
					
				}
			}
			
			visited[v] = true;

		}
	}
}
