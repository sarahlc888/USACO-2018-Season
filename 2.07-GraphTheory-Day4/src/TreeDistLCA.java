import java.util.ArrayList;

/*
 * should be working (see pasture walking for comparison)
 * 
 * adj matrix version
 * 
 * lowest Common Ancestor strategy to find distance between two nodes
 */
public class TreeDistLCA {
	static int[] par;
	static int[] dist;
	static boolean[] visited;
	static int N;
	static int root;
	static int[][] mat;
	
	
	public static void main(String[] args) {
		N = 10;
		root = 0;
		par = new int[N];
		dist = new int[N];
		mat = new int[N][N];
		dist[root] = 0;
		visited = new boolean[N];
		dfs(root, -1);
	}
	public static void dfs(int u, int parent) {
		// for all i adjacent to u
		par[u] = parent;
		for (int i = 0; i < N; i++) {
			if (mat[u][i] != -1) { // if there is a path from i to u
				if (!visited[i]) {
					visited[i] = true;
					dist[i] = dist[u] + 1;
					dfs(i, u);
				}
			}
		}
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
