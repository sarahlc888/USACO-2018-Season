import java.io.*;
import java.util.*;

/*
 * 12/9
 * minumum spanning tree with some required edges
 * probably works
 */
public class Prims {
	static boolean[] visited; // visited for the MST
	static ArrayList<ArrayList<Pair>> adj = new ArrayList<ArrayList<Pair>>(); // adj list (weight, dest)
	static ArrayList<ArrayList<Pair>> coreAdj = new ArrayList<ArrayList<Pair>>(); // adj list (weight, dest)
	static int cost = 0;
	
	static int V;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("roads.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		V = Integer.parseInt(st.nextToken()); // num nodes
		int E = Integer.parseInt(st.nextToken()); // num edges
		visited = new boolean[V+1];
		
		for (int i = 0; i <= V; i++) { // initialize adj list
			adj.add(new ArrayList<Pair>()); 
			coreAdj.add(new ArrayList<Pair>());
		}
		
		for (int i = 0; i < E; i++) {
			// scan in edges
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			// 2 way edges
			adj.get(a).add(new Pair(c, b));
			adj.get(b).add(new Pair(c, a));
			if (d == 1) {
				coreAdj.get(a).add(new Pair(c, b));
				coreAdj.get(b).add(new Pair(c, a));
				visited[a] = true;
				visited[b] = true;
				cost += c;
			}
		}
		
		prim();
		System.out.println(cost);
	}
	
	public static void prim() {
		PriorityQueue<Pair> pq = new PriorityQueue<Pair>();
		// add the edges that cross from S to VS
		for (int i = 0; i <= V; i++) {
			if (visited[i]) {
				for (int j = 0; j < adj.get(i).size(); j++) {
					Pair curEdge = adj.get(i).get(j);
					if (!visited[curEdge.y]) {
						pq.add(new Pair(curEdge.x, curEdge.y)); // change to (weight, destination)
					}
				}
			}
		}
		
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
