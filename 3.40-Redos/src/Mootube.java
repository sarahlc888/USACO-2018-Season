import java.util.*;
import java.io.*;
/*
 * USACO 2018 January Contest, Gold
 * Problem 1. MooTube
 * 
 * 20 minutes
 */
public class Mootube {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("mootube.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); // num videos
		int Q = Integer.parseInt(st.nextToken()); // num queries
		Edge[] arr = new Edge[N-1];
		for (int i = 0; i < N-1; i++) {
			st = new StringTokenizer(br.readLine());
			arr[i] = new Edge(Integer.parseInt(st.nextToken())-1, 
					Integer.parseInt(st.nextToken())-1, Integer.parseInt(st.nextToken()));
		}
		Pair[] qs = new Pair[Q];
		for (int i = 0; i < Q; i++) { // scan everything in
			st = new StringTokenizer(br.readLine());
			qs[i] = new Pair(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())-1, i);
		}
		br.close();
		Arrays.sort(arr);
		Arrays.sort(qs);
		// sort edges and queries
//		System.out.println(Arrays.toString(arr));
//		System.out.println(Arrays.toString(qs));
		DisjointSetUnion dsu = new DisjointSetUnion(N);
		
		int edgeInd = 0; // the next edge to merge
		
		for (int q = 0; q < Q; q++) {
			// loop through queries from highest k to lowest k
			while (edgeInd < N-1 && arr[edgeInd].z >= qs[q].k) {
				dsu.union(arr[edgeInd].x, arr[edgeInd].y);
				edgeInd++;
			}
			qs[q].ans = dsu.size[dsu.root(qs[q].v)]-1;
		}
		Arrays.sort(qs, qComparator);
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("mootube.out")));
		for (int q = 0; q < Q; q++) {
			pw.println(qs[q].ans);
//			System.out.println(qs[q].ans);
		}
		
		pw.close();
	}
	public static Comparator<Pair> qComparator = new Comparator<Pair>(){

		@Override
		public int compare(Pair x, Pair y) {
			return x.ind-y.ind;
		}
	};
	public static class Edge implements Comparable<Edge> {
		int x; // node 1
		int y; // node 2
		int z; // dist
		public Edge(int a, int b, int c) {
			x = a;
			y = b;
			z = c;
		}
		@Override
		public int compareTo(Edge o) { // sort by distance, high to low
			return o.z-z;
		}
		public String toString() {
			return x + " " + y + " " + z;
		}
	}
	public static class Pair implements Comparable<Pair> {
		int k; // min relevance allowed
		int v; // source video
		int ind; // original index of query
		int ans = -1;
		public Pair(int a, int b, int i) {
			k = a;
			v = b;
			ind = i;
		}
		@Override
		public int compareTo(Pair o) { // sort by x, then y
			if (k == o.k) return v-o.v;
			return o.k-k;
		}
		public String toString() {
			return k + " " + v;
		}
	}
	public static class DisjointSetUnion {
		int[] p;
		int[] size; // store the size of tree from each root, init as 1
		
		public DisjointSetUnion(int n) {
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

}
