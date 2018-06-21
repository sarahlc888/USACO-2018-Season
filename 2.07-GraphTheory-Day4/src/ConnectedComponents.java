
public class ConnectedComponents {
	static int N = 5;
	static int[][] mat = new int[N][N];
	static boolean[] visited = new boolean[N];
	
	public static void main(String[] args) {
		
		// O(N^2)
		// go through each node once in the DFS
		// only call DFS on one node in the connected component
		
		int count = 0;
		
		for (int i = 0; i < N; i++) {
			if (!visited[i]) { // if not visited
				dfs(i);
				count++;
			}
		}
	}
	
	public static void dfs(int u) { 
		// node u
		for (int i = 0; i < N; i++) { 
			// loop through the row in the adj matrix
			if (mat[u][i] == 1) { // if i is connected to u
				if (!visited[i]) { // visited array for optimization
					// if not visited, mark visited and DFS
					visited[i] = true;
					dfs(i);
				}
			}
		}
	}
}
