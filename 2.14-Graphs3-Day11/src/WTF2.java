import java.io.*;
import java.util.*;

/*
 * times out 5/10 test cases
 * adj list version
 * 0...N-1 indexing
 */

public class WTF2 {
	static boolean[] visited; // visited for the MST
	static int N;
	static int cost = 0;
	static Pair[] fields;
	// adj lists. (adj.get(i) = j when there's an edge from i to j). cost = cost(i, j)
	static ArrayList<ArrayList<Integer>> adj = new ArrayList<ArrayList<Integer>>(); 

	
	public static void main(String args[]) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // num nodes
		int C = Integer.parseInt(st.nextToken()); // min cost

		fields = new Pair[N]; // locations of nodes

		for (int i = 0; i < N; i++) { // scan in the field locations
			st = new StringTokenizer(br.readLine());
			
			fields[i] = new Pair(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())); 
			adj.add(new ArrayList<Integer>()); // initialize
		}
		for (int i = 0; i < N; i++) { // all fields
			for (int j = 0; j < N; j++) { // all fields
				
				if (cost(i, j) < C) continue; // no edge, too short
				adj.get(i).add(j); // otherwise, add it
				
			}
		}
		visited = new boolean[N];

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
				int neigh = adj.get(node).get(i); // neighbor
				
				pq.add(new Pair(cost(node, neigh), neigh)); // add to PQ
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
	public static int cost(int a1, int b1) { // distance formula squared
		Pair a = fields[a1];
		Pair b = fields[b1];
		return (a.x-b.x)*(a.x-b.x)+(a.y-b.y)*(a.y-b.y);
	}
}
