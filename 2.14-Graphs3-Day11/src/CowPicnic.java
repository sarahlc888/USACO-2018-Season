import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
/*
 * 10/10 test cases
 * graph with adjacency lists
 * 
 * from every cow, BFS or DFS to find all reachable pastures
 * find the number of pastures reachable by all cows
 * 
 * O(K * (V + E)) = O(K * (M+N))
 * 
 * 0...N-1 indexing for cows
 * 1...N for graph
 */
public class CowPicnic {
	// DFS stuff
	static boolean[] visited;
	// adj lists. unweighted, so just integer (adj.get(i) = j when there's an edge from i to j)
	static ArrayList<ArrayList<Integer>> adj = new ArrayList<ArrayList<Integer>>(); 
	// reachable array
	static int[] reachable;

	public static void main(String args[]) throws IOException {
		// INPUT
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int K = Integer.parseInt(st.nextToken()); // num cows
		int N = Integer.parseInt(st.nextToken()); // num pastures
		int M = Integer.parseInt(st.nextToken()); // num paths

		int[] cowPos = new int[K]; // cow starting positions


		
		for (int i = 0; i < K; i++) { // scan in cow positions
			cowPos[i] = Integer.parseInt(br.readLine());
		}
		for (int i = 0; i <= N; i++) {
			adj.add(new ArrayList<Integer>()); // initialize adj list
		}
		for (int i = 0; i < M; i++) { // scan in paths
			st = new StringTokenizer(br.readLine());
			int start = Integer.parseInt(st.nextToken());
			int end = Integer.parseInt(st.nextToken());
			if (!adj.get(start).contains(end)) { // check for duplicates
				adj.get(start).add(end); // one way paths
			}
		
		}
		/*
		for (int i = 0; i <= N; i++) {
			System.out.println(adj.get(i));
		}
		*/
		reachable = new int[N+1]; // how many cows can reach the pasture

		for (int i = 0; i < K; i++) {
			visited = new boolean[N+1]; // visited array for current DFS
			int start = cowPos[i];
			//System.out.println(start);
			visited[start] = true;
			DFS(start);
		}

		int count = 0;

		for (int i = 0; i <= N; i++) {
			if (reachable[i] >= K) {
				// if all cows can reach pasture i
				count++;
			}
		}
		//System.out.println(Arrays.toString(reachable));
		System.out.println(count);
	}
	public static void DFS(int u) {
		// u is current node
		reachable[u]++; // mark reachable
		//System.out.println("u: " + u + " " + Arrays.toString(reachable));
		// loop through neighbors
		for (int i = 0; i < adj.get(u).size(); i++) {
			int neigh = adj.get(u).get(i); // neighboring node
			if (!visited[neigh]) { // if not visited
				visited[neigh] = true; // mark visited
				
				DFS(neigh); // DFS
			}
		}
		
	}
}
