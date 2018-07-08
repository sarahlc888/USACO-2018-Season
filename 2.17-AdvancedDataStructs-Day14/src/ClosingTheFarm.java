import java.io.*;
import java.util.*;
/*
 * 10/10
 * 
 * DSU
 * go backward, add in the nodes
 * 0...N-1 indexing
 */
public class ClosingTheFarm {
	static int N;
	static int M;

	static int numAdded = 1;
	static boolean[] added;
	static boolean[] connected;

	// adj lists
	static ArrayList<ArrayList<Integer>> adj = new ArrayList<ArrayList<Integer>>();

	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // number of barns
		M = Integer.parseInt(st.nextToken()); // number of paths

		added = new boolean[N];
		connected = new boolean[N];

		// init adj
		for (int i = 0; i <= N; i++) {
			adj.add(new ArrayList<Integer>());
		}

		for (int i = 0; i < M; i++) { // bidirectional paths between barns
			st = new StringTokenizer(br.readLine());
			int start = Integer.parseInt(st.nextToken())-1;
			int end = Integer.parseInt(st.nextToken())-1;
			adj.get(start).add(end);
			adj.get(end).add(start);
		}
		int[] closings = new int[N];
		for (int i = 0; i < N; i++)  // order of barn closings
			closings[i] = Integer.parseInt(br.readLine())-1;
		br.close();

		DisjointSetUnion dsu = new DisjointSetUnion(N);


		for (int i = N-1; i >= 0; i--) { // loop backward
			int curBarn = closings[i]; // current barn
//			System.out.println("cur barn: " + curBarn + " num added: " + numAdded);

			// add edges to the graph if they go to something already in the graph
			ArrayList<Integer> edges = adj.get(curBarn); // edges from curBarn

			for (int j = 0; j < edges.size(); j++) {
				int dest = edges.get(j); // source = curBarn
//				System.out.println("  edge to: " + dest);
				if (added[dest]) { // if the dest is already added, 
					// connect curBarn to dest in the DSU
					dsu.union(dest, curBarn);
//					System.out.println("    connect");
				}
			}

			// check if the size of any node = number of nodes added
//			System.out.println("  root of cb: " +dsu.root(curBarn));
//			System.out.println("  size: " + dsu.size[dsu.root(curBarn)]);
//			System.out.println("  sizes: " + Arrays.toString(dsu.size));
			
			if (dsu.size[dsu.root(curBarn)] == numAdded) {
//				System.out.println("all connected");
				connected[curBarn] = true; // if so, all connected
			} 

			added[curBarn] = true;
			numAdded++;
		}
		for (int i = 0; i < N; i++) {
			if (connected[closings[i]]) {
				System.out.println("YES");
			} else {
				System.out.println("NO");
			}
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
