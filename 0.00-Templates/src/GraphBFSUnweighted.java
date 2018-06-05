import java.util.*;
import java.io.*;

// unweighted

public class GraphBFSUnweighted {
	public static void main(String args[]) throws IOException {
		
		BufferedReader br = new BufferedReader(new FileReader("graph.in"));
		int N = Integer.parseInt(br.readLine()); // number of nodes
		
		int[] nodes = new int[N];
		for (int i = 0; i < N; i++) {
			nodes[i] = i;
		}
		
		int[][] adjMatrix = new int[N][N]; // adjacency matrix
		AdjList[] edges = new AdjList[N]; // adjacency lists
		
		for (int i = 0; i < N; i++) edges[i] = new AdjList();
		
		
		for (int i = 0; i < N; i++) { 
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				// new edge
				adjMatrix[i][j] = Integer.parseInt(st.nextToken());
				edges[i].adj.add(j);
				edges[i].cost.add(j);
			}
		}
		
		br.close();
		
		int source = 0; // beginning of the BFS
		
		LinkedList<Integer> toVisit = new LinkedList<Integer>(); // to visit array
		boolean[] visited = new boolean[N]; // visited array
		toVisit.add(source); // mark the source as to visit 
		visited[source] = true; // mark the source as visited (or added)
		
		while (!toVisit.isEmpty()) {
			int curNode = toVisit.removeFirst();
			for (int i = 0; i < edges[curNode].adj.size(); i++) {
				int neigh = edges[curNode].adj.get(i);
				if (!visited[neigh]) { // if not visited, add and mark visited
					toVisit.add(neigh);
					visited[neigh] = true;
				}
			}
		}
		
		
	}
	public static class AdjList {
		ArrayList<Integer> adj;
		ArrayList<Integer> cost;
		
		public AdjList() {
			adj = new ArrayList<Integer>();
			cost = new ArrayList<Integer>();
		}
	}
}
