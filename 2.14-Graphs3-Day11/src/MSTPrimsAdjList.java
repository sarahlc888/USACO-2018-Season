import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
/* O( N log N ) where N is about V + E
 * 1...N indexing
 * 
 * 10/10 test graphs
 */
public class MSTPrimsAdjList {
	
	static boolean[] visited; // visited for the MST
	static ArrayList<ArrayList<Pair>> adj = new ArrayList<ArrayList<Pair>>(); // adj list (weight, dest)
	static int cost = 0;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int V = Integer.parseInt(st.nextToken()); // num nodes
		int E = Integer.parseInt(st.nextToken()); // num edges
		visited = new boolean[V+1];
		
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
		
		prim();
		System.out.println(cost);
	}
	
	public static void prim() {
		int S = 1; // starting node
		PriorityQueue<Pair> pq = new PriorityQueue<Pair>();
		// add the edges that cross from S to VS
		pq.add(new Pair(0, S)); // weight, destination
		while (!pq.isEmpty()) {
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
			
			// look at the neighbors and push to pq
			for (int i = 0; i < adj.get(node).size(); i++) {
				Pair neigh = adj.get(node).get(i); // neighbor
				pq.add(new Pair(neigh.x, neigh.y)); // add to PQ
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
