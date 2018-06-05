import java.util.*;
import java.io.*;

// int nodes; uses arraylist for the adjacency lists; unweighted

public class GraphImp1 {
	public static void main(String args[]) throws IOException {
		
		BufferedReader br = new BufferedReader(new FileReader("graph.in"));
		int N = Integer.parseInt(br.readLine()); // number of nodes
		
		int[] nodes = new int[N]; // array of nodes
		for (int i = 0; i < N; i++) {
			nodes[i] = i;
		}
		
		int[][] adjMatrix = new int[N][N]; // adjacency matrix
		
		ArrayList<ArrayList<Integer>> edges = new ArrayList<ArrayList<Integer>>(); // adjacency lists
		for (int i = 0; i < N; i++) {
			edges.add(new ArrayList<Integer>());
		}
		
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				adjMatrix[i][j] = Integer.parseInt(st.nextToken()); // fill in matrix
				if (adjMatrix[i][j] > 0) edges.get(i).add(j); // add an edge from i to j
			}
		}
		br.close();
		
	}
}
