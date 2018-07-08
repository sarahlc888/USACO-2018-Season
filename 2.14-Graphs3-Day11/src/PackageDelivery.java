import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
/* 
 * 10/10 test cases
 * 
 * djikstra adj list
 */
public class PackageDelivery {
	
	static int[] dist;
	static boolean[] visited; // visited for the MST
	static ArrayList<ArrayList<Pair>> adj = new ArrayList<ArrayList<Pair>>(); // adj list (weight, dest)
	static int cost = 0;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int V = Integer.parseInt(st.nextToken()); // num nodes
		int E = Integer.parseInt(st.nextToken()); // num edges

		visited = new boolean[V+1];
		
		dist = new int[V+1]; // distance array from source to i
		
		for (int i = 0; i <= V; i++) { // initialize adj list
			adj.add(new ArrayList<Pair>()); 
		}
		
		for (int i = 0; i < E; i++) {
			// scan in edges
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			// 2 way edges
			adj.get(a).add(new Pair(c, b));
			adj.get(b).add(new Pair(c, a));
		}
		Arrays.fill(dist, Integer.MAX_VALUE);
		dij(1); // start at 1
		System.out.println(dist[V]); // distance to last node
	}
	
	public static void dij(int S) { // S = starting node
		PriorityQueue<Pair> pq = new PriorityQueue<Pair>();
		// add the edges that cross from S to VS
		pq.add(new Pair(0, S)); // weight, destination
		while (!pq.isEmpty()) {
			//System.out.println(Arrays.toString(dist));
			// let (weight, node) be at top
			Pair p = pq.poll();
			int weight = p.x;
			int node = p.y;
			
			
			// check if node is visited
			if (visited[node]) {
				continue; // if already visited, don't add it
			} else {
				visited[node] = true; // mark visited and proceed
			}
			
			dist[node] = weight; // assign weight

			
			// look at the neighbors and push to pq
			for (int i = 0; i < adj.get(node).size(); i++) {
				Pair neigh = adj.get(node).get(i); // neighbor
				pq.add(new Pair(neigh.x + dist[node], neigh.y)); // add to PQ
			}
			
			// add weight to the MST
			cost += weight;
		}
	}
	public static class Pair implements Comparable<Pair> {
		int x; // weight
		int y; // dest
		public Pair(int a, int b) {
			x = a;
			y = b;
		}
		@Override
		public int compareTo(Pair o) { // sort by edge
			return x-o.x;
		}
		
	}
}
