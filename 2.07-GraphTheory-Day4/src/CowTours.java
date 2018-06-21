import java.io.*;
import java.util.*;
/*
 * DEBUG THIS!!!!!
 * 
 * PRECOMPUTATION O(N^3 + N^2)
 * turn it into an adjacency matrix using geometry
 * make unconnected edges have distance infinity
 * 
 * find the connected components
 * 
 * for every connected component, precompute diameter using floyd warshall on each one
 * O(N^3)
 * 
 * get path array (path[u] = max distance from u to some node in its component) O(N) * N
 * O(N^2)
 * 
 * 
 * store the components
 * every node should store what component it's in
 * 
 * 
 * COMPUTATION O(N^2) - just access everything
 * brute force over all possible pairs O(N^2) pairs of pastures to connect
 * 
 * if (u, v) are in the same component, skip it
 * else, connect them
 * compute the new diameter = path[u] + dist(u, v) + path[v]
 * if greater than d1 and d2 of the original sets, big it's the diameter
 * 
 * 
 * 
 * OVERALL: O(N^3 + 2N^2)
 */
public class CowTours {
	static double[][] dist;
	static int[][] pastures;
	static int N;
	static boolean[] visited;
	static ArrayList<ArrayList<Integer>> fields;
	static int fieldsInd;
	static int[] component;
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		N = Integer.parseInt(scan.nextLine()); // number of pastures
		pastures = new int[N][2];
		for (int i = 0; i < N; i++) { // scan in x y of pastures
			StringTokenizer st = new StringTokenizer(scan.nextLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			pastures[i][0] = x;
			pastures[i][1] = y;
		}
		
		component = new int[N]; // stores what component node i is in
		
		// create adjacency matrix
		dist = new double[N][N]; // adj matrix
		for (int i = 0; i < N; i++) { // scan in
			String line = scan.nextLine();
			for (int j = 0; j < N; j++) {
				if (line.charAt(j) == '1') {
					// if there is a connection, assign distance
					dist[i][j] = dist(i, j); 
				} else { // otherwise, make it infinity
					dist[i][j] = Double.MAX_VALUE;
				}
			}
			//System.out.println(Arrays.toString(dist[i]));
		}

		// find connected components
		visited = new boolean[N];
		fields = new ArrayList<ArrayList<Integer>>(); // stores ccs
		fieldsInd = 0; // index in fields

		for (int i = 0; i < N; i++) {
			if (!visited[i]) { // if not visited
				//System.out.println(i);
				fields.add(new ArrayList<Integer>()); // create a new cc
				dfs(i);
				//System.out.println(fields.get(fieldsInd));
				fieldsInd++;
			}
		}
		
		// do floyd warshall on all fields to calculate diameters
	
		double[] diams = new double[fieldsInd];
		
		
		for (int i = 0; i < fieldsInd; i++) {
			ArrayList<Integer> curField = fields.get(i); // current cc
			int n = curField.size();
			
			double max = 0; // diameter = max shortest dist between any pair
			
			for (int k = 0; k < n; k++) { // intermediate point
				for (int h = 0; h < n; h++) { // start
					for (int j = 0; j < n; j++) { // end
						// check if the distance from i to j passing through k
						// is shorter than the current
						
						int a = curField.get(k);
						int b = curField.get(h);
						int c = curField.get(j);
						
						dist[b][c] = Math.min(dist[b][c], dist[b][a] + dist[k][a]);
						if (dist[b][c] > max && 
								dist[b][c] != Double.MAX_VALUE) {
							max = dist[b][c]; // update max
						}
						
						
						
					}
				}
			}
			
			diams[i] = max; // assign diameter
			
		}
		// get path[u] = max distance to u from any other node in its component
		// TODO: make sure this and all stuff below works
		double[] path = new double[N];

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (dist[i][j] == Double.MAX_VALUE) continue;
				path[i] = Math.max(path[i], dist[i][j]);
			}
		}
		
		
		double minBigDiam = Integer.MAX_VALUE; // max diameter
		
		// brute force all pairs of nodes
		for (int u = 0; u < N; u++) {
			for (int v = u+1; v < N; v++) {
				if (component[u] == component[v]) {
					// if they are in the same component, continue
					continue;
				}
				
				// otherwise, connect them
				dist[u][v] = dist(u, v);
				
				// new diameter of the big connected field
				double newDiam = path[u] + dist(u, v) + path[v];
				// diameter from within fields
				double newDiam2 = Math.max(diams[component[u]], diams[component[v]]);
				
				newDiam = Math.max(newDiam, newDiam2);
				
				if (newDiam < minBigDiam) minBigDiam = newDiam;
				
			}
		}
		//System.out.println(minBigDiam);
		System.out.printf("%.6f", minBigDiam);

	}
	public static double dist(int i, int j) {
		// distance between pasture i and j
		return Math.sqrt( Math.pow(pastures[i][0] - pastures[j][0], 2) + 
				Math.pow(pastures[i][1] - pastures[j][1], 2));
	}
	public static double path(int u) {
		double max = 0;
		for (int i = 0; i < dist[u].length; i++) {
			if (dist[u][i] != Integer.MAX_VALUE &&
					dist[u][i] > max) {
				// if it's not infinity
				max = dist[u][i];
			}
		}
		return max;
	}
	public static void dfs(int u) { 
		// node u
		
		// add to the current cc, avoid repeats
		if (!fields.get(fieldsInd).contains(u)) {
			fields.get(fieldsInd).add(u); 
			component[u] = fieldsInd;
		}
		
		
		for (int i = 0; i < N; i++) { 
			// loop through the row in the adj matrix
			if (dist[u][i] != Double.MAX_VALUE) { // if i is connected to u
				if (!visited[i]) { // visited array for optimization
					// if not visited, mark visited and DFS
					visited[i] = true;
					dfs(i);
				}
			}
		}
	}
}
