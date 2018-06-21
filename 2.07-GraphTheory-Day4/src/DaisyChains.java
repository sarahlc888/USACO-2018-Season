import java.io.*;
import java.util.*;

/*
 * heavily based on connected components
 * 
 * O(N^2)
 * 
 * 10/10 test cases
 */
public class DaisyChains {
	static int N;
	static int M;
	static int[][] mat;
	static boolean[] visited;
	static ArrayList<Integer> component1;
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		StringTokenizer st = new StringTokenizer(scan.nextLine());
		
		N = Integer.parseInt(st.nextToken()); // number of cows
		M = Integer.parseInt(st.nextToken()); // number of connections
		
		mat = new int[N][N];
		visited = new boolean[N];
		
		
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(scan.nextLine());
			// connects a to b, changes to 0...N-1 spacing
			int a = Integer.parseInt(st.nextToken()) - 1;
			int b = Integer.parseInt(st.nextToken()) - 1;
			// mark the edges in the graph
			mat[a][b] = 1;
			mat[b][a] = 1;
		}
		
		int count = 0;
		
		component1 = new ArrayList<Integer>();
		
		// node 1 is only in one chunk
		dfs(0);
		
		boolean printed = false;
		
		for (int i = 0; i < N; i++) {
			if (!component1.contains(i)) { // if not in component 1, print
				System.out.println(i+1); // go back to 1...N indexing
				printed = true;
			}
		}
		if (!printed) {
			System.out.println(0);
		}
	}
	
	public static void dfs(int u) { 
		// node u
		component1.add(u);
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
	public static class Node {
		int id;
		public Node(int i) {
			id = i;
		}
	}
}
