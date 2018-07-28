import java.io.*;
import java.util.*;
/*
 * Graph
 * DSU
 * 
 * USACO 2018 January Contest, Gold
 * Problem 1. MooTube
 * 
 * 10/10 test cases first try!
 */
public class MooTube {
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("mootube.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); // num videos
		int Q = Integer.parseInt(st.nextToken()); // num queries
		
		ArrayList<ArrayList<Pair>> adj = new ArrayList<ArrayList<Pair>>(); // graph (dest, weight)
		ArrayList<Edge> e = new ArrayList<Edge>(); // edges
		
		for (int i = 0; i < N; i++) { // init adj lists
			adj.add(new ArrayList<Pair>());
		}
		for (int i = 1; i < N; i++) { // scan in connections
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken())-1; // adjust indexing
			int b = Integer.parseInt(st.nextToken())-1;
			int c = Integer.parseInt(st.nextToken()); // weight
			adj.get(a).add(new Pair(b, c));
			adj.get(b).add(new Pair(a, c));
			e.add(new Edge(a, b, c));
		}
		Pair[] queries = new Pair[Q];
		Triple[] queriesTriple = new Triple[Q];
		for (int i = 0; i < Q; i++) {
			st = new StringTokenizer(br.readLine());
			int k = Integer.parseInt(st.nextToken()); // min relevance
			int v = Integer.parseInt(st.nextToken())-1; // starting video
			queries[i] = new Pair(k, v);
			queriesTriple[i] = new Triple(i, queries[i]);
		}
		br.close();
		Collections.sort(e); // sort edges
//		System.out.println(e);
		Arrays.sort(queries); // sort queries
		Arrays.sort(queriesTriple); // sort queries
//		System.out.println(Arrays.toString(queries));
//		System.out.println(Arrays.toString(queriesTriple));
		
		int ind = 0; // next edge
		
		DSU d = new DSU(N); // DSU of the graph
		
		int[] ans = new int[Q];
		
		for (int i = 0; i < Q; i++) { // queries
			int k = queries[i].x; // min relevance allowed
			int v = queries[i].y; // starting video
			while (ind < N-1 && e.get(ind).cost >= k) { // while edge is at least the min relevance
				int p1 = e.get(ind).a;
				int p2 = e.get(ind).b;
				d.union(p1, p2); // connect p1 and p2
				ind++;
			}
			int root = d.root(v); // get the root of the starting vid
			int size = d.size[root];
			ans[i] = size;
		}
		int[] ansInOrder = new int[Q];
		for (int i = 0; i < Q; i++) {
			ansInOrder[queriesTriple[i].x] = ans[i];
		}
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("mootube.out")));

		for (int i = 0; i < Q; i++) {
//			System.out.println(ansInOrder[i]-1);
			pw.println(ansInOrder[i]-1);
		}
		
		pw.close();
	}
	public static class DSU {
		int[] p; // points
		int[] size; // store the size of tree from each root, init as 1
		
		public DSU(int n) {
			p = new int[n];
			for (int i = 0; i < n; i++) {
				p[i] = i;
			}
			size = new int[n];
			Arrays.fill(size, 1); 
		}
		public void union(int u, int v) {
			// log n
			// attach smaller trees to bigger trees. attach smaller root to bigger root
			u = root(u); // optimizations
			v = root(v); // to make find faster
			if (u == v) return; // already in same component
			
			if (size[u] >= size[v]) {
				p[v] = u;
				size[u] += size[v];
			} else {
				p[u] = v;
				size[v] += size[u];
			}
		}
		public boolean find(int u, int v) {
			return root(u) == root(v);
		}
		public int root(int u) { 
			// formerly up to O(N).
			// To optimize, minimized depth by attaching stuff to the root (in union).
			
			// O(log*n) (basically constant time)
			// means the number of times you need to log to get to root
			
			while (u != p[u]) {
				// path compression!
				p[u] = p[p[u]]; // makes distance to root shorter
				u = p[u];
			}
			return u;
		}
	}
	public static class Edge implements Comparable<Edge> {
		int a;
		int b;
		int cost;
		public Edge(int x, int y, int z) {
			a = x;
			b = y;
			cost = z;
		}
		@Override
		public int compareTo(Edge o) { // sort by cost, high to low
			return o.cost-cost;
		}
		public String toString() {
			return a + " " + b + " " + cost;
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
		public int compareTo(Pair o) { // sort by x (highest first), then y
			if (x == o.x) return y-o.y;
			return o.x-x;
		}
		public String toString() {
			return x + " " + y;
		}
	}
	public static class Triple implements Comparable<Triple> {
		int x;
		Pair y;

		public Triple(int a, Pair b) {
			x = a;
			y = b;
		}
		@Override
		public int compareTo(Triple o) { // sort by x (highest first), then y
			return y.compareTo(o.y);
		}
		public String toString() {
			return x + " " + y;
		}
	}

}
