import java.util.*;

/*
 * DFS
 * until you run into an already visited node (non-parent)
 * O(N^2)
 * 
 * not tested
 */
public class FindCycleUndirected {
	static int N;
	static int root;
	static int[][] mat;
	static boolean[] visited;
	static int[] parent;
	
	static Stack<Integer> v = new Stack<Integer>();
	
	public static boolean cycle(int u, int p) {
		// u is current node, p is parent
		// neighbors
		for (int i = 0; i < N; i++) {
			if (mat[u][i] != 0) { // neighbor
				if (!visited[i]) {
					v.push(i); // push on the node
					cycle(i, u);
				} else {
					// if the visited edge isn't from its parent
					if (i != parent[u]) {
						// there's a cycle
						// pop from v until you find the node
						while (v.peek() != i) {
							System.out.println(v.pop());
						}
						System.out.println(v.pop());
						
						return true;
					}
					
				}
				
			}
		}
		return false;
		
	}
}
