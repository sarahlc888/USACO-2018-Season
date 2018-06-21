import java.io.*;
import java.util.*;
/*
 * based off of TreeDistLCA
 * 
 * 10/10 test cases
 * 
 * IDEA: O(NQ) total complexity
 * find distance array of root using DFS
 * for all queries, find the LCA, O(N)
 * d(u, LCA) + d(v, LCA), O(1)
 * 
 */
public class PastureWalking {
	static int N;
	static int Q;
	static int[][] mat;
	static int[] par; 
	static int[] dist;
	static boolean[] visited;
	static int root;
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		StringTokenizer st = new StringTokenizer(scan.nextLine());
		
		N = Integer.parseInt(st.nextToken()); // number of cows (nodes)
		Q = Integer.parseInt(st.nextToken()); // number of queries
		
		mat = new int[N][N]; // tree
		par = new int[N]; // parents
		Arrays.fill(par, -1); // signifies no parent
		for (int i = 0; i < N; i++) {
			Arrays.fill(mat[i], -1);
		}
		
		// scan in the N-1 edges
		for (int i = 1; i < N; i++) {
			st = new StringTokenizer(scan.nextLine());
			// connects a to b, changes to 0...N-1 spacing
			int a = Integer.parseInt(st.nextToken()) - 1;
			int b = Integer.parseInt(st.nextToken()) - 1;
			int c = Integer.parseInt(st.nextToken());
			// mark the edges in the graph
			mat[a][b] = c;
			mat[b][a] = c;
		}
		//System.out.println();
		/*for (int i = 0; i < N; i++) {
			System.out.println(Arrays.toString(mat[i]));
		}*/
		
		// TODO: DFS to get a distance array from root to all nodes
		root = 0; // arbitrary
		dist = new int[N];
		dist[root] = 0;
		
		visited = new boolean[N];
		visited[root] = true; // mark root as visited
		dfsWeighted(root, -1);
		
		//System.out.println("par: " + Arrays.toString(par));
		
		// process the queries, go from a to b
		for (int i = 0; i < Q; i++) {
			
			st = new StringTokenizer(scan.nextLine());
			int a = Integer.parseInt(st.nextToken()) - 1;
			int b = Integer.parseInt(st.nextToken()) - 1;
			
			//System.out.println("Q: " + a + ", " + b);
			
			// find the LCA
			int LCA = LCA(a, b);
			
			// find the distance
			System.out.println(dist(a, b, LCA));
			
		}
		
		
		scan.close();
	}
	public static void dfsWeighted(int u, int parent) {
		//System.out.println("u: " + u + " parent: " + parent);
		par[u] = parent;
		for (int i = 0; i < N; i++) { // for all i
			//System.out.println("  " + mat[u][i]);
			if (mat[u][i] != -1) { // if there is a path from i to u
				//System.out.println("  i: " + i);
				if (!visited[i]) {
					visited[i] = true;
					dist[i] = dist[u] + mat[u][i];
					dfsWeighted(i, u);
				}
			}
			
		}
		
	}
	public static ArrayList<Integer> anc(int x) {
		// get the ancestors of node x, including itself
		// O(H) where H is the height of the tree
		
		 ArrayList<Integer> p = new ArrayList<Integer>();
		 
		 p.add(x);
		 
		 while (par[x] != -1) { // make parent of the root -1
			 x = par[x];
			 p.add(x);
		 }
		 return p;
	}
	public static int LCA(int a, int b) {
		// returns the lowest common ancestor
		// AKA first common element of both arrays
		// O(3N) to find ancestors
		
		// there should always be at least 1 ancestor
		ArrayList<Integer> a1 = anc(a);
		ArrayList<Integer> b1 = anc(b);
		
		boolean[] seen = new boolean[N];
		for (int x : a1) {
			seen[x] = true;
		}
		for (int x : b1) {
			if (seen[x]) return x; // return the first one
		}
		return -1; // none
	}
	public static int dist(int u, int v, int LCA) {
		
		// O(1) time to access these precomputed distances
		
		// distance from u to LCA = dist(root, u) - dist(root, LCA);
		// precomputed in dist array
		int d1 = dist[u] - dist[LCA];
		// distance from v to LCA = dist(root, v) - dist(root, LCA);
		int d2 = dist[v] - dist[LCA];
		return d1 + d2; // dist(u, LCA) + dist(v, LCA);
		
	}
}
