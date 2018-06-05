import java.util.*;
import java.io.*;

// int nodes; uses data structure for the adjacency lists; weighted

public class GraphImp2 {
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
