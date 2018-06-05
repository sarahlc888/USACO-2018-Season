import java.util.*;
import java.io.*;

// untested

public class GraphBFSWeighted {
	
	public static int[][] steps;
	public static int source;
	
	public static void main(String args[]) throws IOException {
		
		BufferedReader br = new BufferedReader(new FileReader("graph.in"));
		int N = Integer.parseInt(br.readLine()); // number of nodes
		
		int[] nodes = new int[N];
		for (int i = 0; i < N; i++) {
			nodes[i] = i;
		}
		
		steps = new int[N][N]; // adjacency matrix
		AdjList[] edges = new AdjList[N]; // adjacency lists
		
		for (int i = 0; i < N; i++) edges[i] = new AdjList();
		
		
		for (int i = 0; i < N; i++) { 
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				// new edge
				steps[i][j] = Integer.parseInt(st.nextToken());
				edges[i].adj.add(j);
				edges[i].cost.add(j);
			}
		}
		
		br.close();
		
		source = 0; // beginning of the BFS
		
		PriorityQueue<Integer> toVisit = new PriorityQueue<Integer>(idComparator); // to visit array
		boolean[] visited = new boolean[N]; // visited array
		toVisit.add(source); // mark the source as to visit 
		visited[source] = true; // mark the source as visited (or added)
		
		while (!toVisit.isEmpty()) {
			int curNode = toVisit.poll();
			for (int i = 0; i < edges[curNode].adj.size(); i++) {
				int neigh = edges[curNode].adj.get(i);
				if (!visited[neigh]) { // if not visited, add and mark neighbor
					toVisit.add(neigh);
					visited[neigh] = true;
				}
				
				if ((source != neigh) && ((steps[source][curNode] + steps[curNode][neigh] < steps[source][neigh]) || steps[source][neigh] == 0)) {
					steps[source][neigh] = steps[source][curNode] + steps[curNode][neigh]; // update steps
					toVisit.add(neigh); // add and mark neighbor
					visited[neigh] = true;
				} 
				if (visited[neigh] == false) {
					System.out.println("   add " + neigh);
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
	public static Comparator<Integer> idComparator = new Comparator<Integer>(){

		@Override
		public int compare(Integer x, Integer y) {
			if (steps[source][x] == 0) {
				return 1;
			} else if (steps[source][y] == 0) {
				return -1;
			} else if (steps[source][x] > steps[source][y]) {
				return 1;
			} else if (steps[source][x] < steps[source][y]) {
				return -1;
			} else {
				return 0;
			}
		}
	};
}
