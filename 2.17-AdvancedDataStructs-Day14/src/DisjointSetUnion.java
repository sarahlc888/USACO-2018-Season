import java.util.Arrays;

/*
 * tested DSU
 */
public class DisjointSetUnion {
	static int[] p;
	static int[] size; // store the size of tree from each root, init as 1
	
	public DisjointSetUnion(int n) {
		p = new int[n];
		for (int i = 0; i < n; i++) {
			p[i] = i;
		}
		size = new int[n];
		Arrays.fill(size, 1); 
	}
	public static void union(int u, int v) {
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
	public static boolean find(int u, int v) {
		return root(u) == root(v);
	}
	public static int root(int u) { 
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
