import java.io.*;
import java.util.*;
/*
 * USACO 2018 February Contest, Gold
 * Problem 2. Directory Traversal
 * 
 * originally attempted in 1.46 in contest, 2/10 test cases
 * 
 * naive way, see v2
 */

public class DirectoryTraversal {
	static int N;
	static int[] dist;
	static boolean[] visited;
	static ArrayList<ArrayList<Pair>> adj;
	static boolean[] isFile;
	static String[] nodes;
	public static void main(String args[]) throws IOException {
		// INPUT
		BufferedReader br = new BufferedReader(new FileReader("dirtraverse.in"));
		N = Integer.parseInt(br.readLine()); // total number of files and dirs

		isFile = new boolean[N]; // if false, then it's a dir
		nodes = new String[N];
		
		// set up graph
		// (dest, weight)
		adj = new ArrayList<ArrayList<Pair>>(); 
		for (int i = 0; i < N; i++) {
			adj.add(new ArrayList<Pair>());
		}
		
		for (int i = 0; i < N; i++) { // scan in each file or dir
			StringTokenizer st = new StringTokenizer(br.readLine());
			String name = st.nextToken(); // name
			nodes[i] = name;
			int numc = Integer.parseInt(st.nextToken()); // num children inside
			if (numc == 0) isFile[i] = true;
			for (int j = 0; j < numc; j++) {
				int child = Integer.parseInt(st.nextToken()) - 1; // to fix indexing 
				adj.get(child).add(new Pair(i, 3)); // "../"
				adj.get(i).add(new Pair(child, -1)); // not known yet
//				adj.get(i).add(new Pair(child, name.length()+1)); // "name/"
			}
		}
		br.close();
//		System.out.println(Arrays.toString(nodes));
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < adj.get(i).size(); j++) {
				if (adj.get(i).get(j).y == -1) { // if the weight is uninitialized
					adj.get(i).get(j).y = nodes[adj.get(i).get(j).x].length()+1; // "name/"
				}
			}
		}
		
//		System.out.println(adj);
		int minCost = Integer.MAX_VALUE;
		
		for (int i = 0; i < N; i++) {
			if (isFile[i]) continue;
//			System.out.println("i: " + i);
			dist = new int[N];
			visited = new boolean[N];
			Arrays.fill(dist, Integer.MAX_VALUE); // reset dist array
			int cost = dij(i); // single source shortest path
//			System.out.println("  cost: " + cost);
			minCost = Math.min(minCost, cost);
		}
		System.out.println(minCost);
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("dirtraverse.out")));
		pw.println(minCost);
		pw.close();
	}
	public static int dij(int S) { // S = starting node
		PriorityQueue<Pair> pq = new PriorityQueue<Pair>();
		int cost = 0;
		// add the edges that cross from S to VS
		pq.add(new Pair(0, S)); // weight, destination
		while (!pq.isEmpty()) {
			//System.out.println(Arrays.toString(dist));
			// let (weight, node) be at top
			Pair p = pq.poll();
//			System.out.println("  edge: " + p);
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
				pq.add(new Pair(neigh.y + dist[node], neigh.x)); // add to PQ
			}
			if (isFile[node]) {
				cost += weight-1;
//				System.out.println("  node: " + nodes[node] + "  weight:  " + (weight-1));
			}
		}
		return cost;
	}

	public static class Edge implements Comparable<Edge> {
		int dest;
		int cost;

		public Edge(int a, int b) {
			dest = a;
			cost = b;
		}
		@Override
		public int compareTo(Edge o) { // sort by x, then y
			if (dest == o.dest) return cost-o.cost;
			return o.dest-dest;
		}
		public String toString() {
			return dest + " " + cost;
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
		public int compareTo(Pair o) { // sort by x, then y
			if (x == o.x) return y-o.y;
			return x-o.x;
		}
		public String toString() {
			return x + " " + y;
		}
	}
}
