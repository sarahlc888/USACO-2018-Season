import java.util.Stack;

/*
 * DFS
 * until you run into a node already on the stack
 * O(N^2)
 * 
 * not tested
 */
public class FindCycleDirected {
	static int N;
	static int root;
	static int[][] mat;
	static boolean[] visited;
	static int[] parent;
	static int[] mark; // visited and still in the stack
	
	
	public static boolean cycle(int u, int p) {
		// u is current node, p is parent
		mark[u] = 1; 
		// 0 means not visited yet
		// 1 means node has been visited but we haven't "gone back from" the node yet
		// 2 means that we've "left" the node (gone back up in the DFS)
		
		for (int i = 0; i < N; i++) {
			if (i == u) continue;

			if (mat[u][i] != 0) { // loop through the neighbors
				
				
				if (mark[i] == 0) { // if unvisited
					cycle(i, u);
				} else if (mark[i] == 1) { 
					// other node was already visited in the branch, it's a cycle
					return true;
				} /*else if (mark[i] == 2) {
					// visited and left it, should never happen, wouldn't go there
					
				}*/
				
				
			}
		}
		return false;
		
	}
}
