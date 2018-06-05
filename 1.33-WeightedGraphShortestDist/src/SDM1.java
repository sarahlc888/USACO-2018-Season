import java.util.*;
import java.io.*;

// finds the shortest distance from all sources to all other points
// don't yet know if it is correct...

public class SDM1 {
	public static void main(String args[]) throws IOException {
		
		BufferedReader br = new BufferedReader(new FileReader("graph.in"));
		int n = Integer.parseInt(br.readLine()); // number of nodes
		int[] nodes = new int[n]; // nodes
		for (int i = 0; i < n; i++)
			nodes[i] = Integer.parseInt(br.readLine()); // scan in values
		int m = Integer.parseInt(br.readLine()); // number of adjacencies below
		AdjList[] adjLists = new AdjList[n]; // adjacency lists
		for (int i = 0; i < n; i++)
			adjLists[i] = new AdjList(); // initialize
		for (int i = 0; i < m; i++) { 
			StringTokenizer st = new StringTokenizer(br.readLine()); // scan in values
			int n1 = Integer.valueOf(st.nextToken()); // node 1
			int n2 = Integer.valueOf(st.nextToken()); // node 2
			int cost = Integer.valueOf(st.nextToken()); // cost
			// add the edges
			adjLists[n1].alist.add(n2); 
			adjLists[n2].alist.add(n1);
			// add the costs
			adjLists[n1].cost.add(cost);
			adjLists[n2].cost.add(cost);
		}
		
		Graph g = new Graph(n);
		g.nodes = nodes;
		g.adjLists = adjLists;
		
		/*
		System.out.println("nodes");
		for (int i = 0; i < n; i++) {
			System.out.println(g.nodes[i]);
		}
		System.out.println("adjs");
		for (int i = 0; i < n; i++) {
			System.out.println("from " + i);
			for (int j = 0; j < g.adjLists[i].alist.size(); j++) {
				System.out.print("  to : " + g.adjLists[i].alist.get(j));
				System.out.println("  cost: " + g.adjLists[i].cost.get(j));
			}
		}
		*/
		
		int[][] steps = new int[n][n];
		for (int i = 0; i < g.adjLists.length; i++) { // for every list
			for (int j = 0; j < g.adjLists[i].alist.size(); j++) { // for every adjacency inside
				// update the cost from and to i
				steps[i][g.adjLists[i].alist.get(j)] = g.adjLists[i].cost.get(j);
				steps[g.adjLists[i].alist.get(j)][i] = g.adjLists[i].cost.get(j);
			}
		}
		
		// graph has now been completely scanned in and initialized
		
		
		
		for (int k = 0; k < n; k++) {
			for (int i = 0; i < n; i++) {
				if (i==k) continue; // i, j, and k must be unique
				for (int j = 0; j < n; j++) {
					if (j==i) continue; // i, j, and k must be unique
					if (j==k) continue; // i, j, and k must be unique
					
					// check the cost of going from i to j through node k
					
					if ((steps[i][j] == 0 || steps[i][k] + steps[k][j] < steps[i][j])
							&& steps[i][k] != 0 && steps[k][j] != 0) {
						// if...
						//  - the dist from i to j (is yet unknown) or (would decrease if updated)
						//  - AND the dists from i to k and k to j are known
						
						// update the cost of distCost[i][j] to the new value going through k
						steps[i][j] = steps[i][k] + steps[k][j];
					}
					
				}
			}
		}
		for (int i = 0; i < steps.length; i++) { // for every list
			System.out.println();
			for (int j = 0; j < steps[i].length; j++) { // for every adjacency inside
				System.out.print(steps[i][j] + " ");
			}
		}
		
	}
	public static class Graph {
		int[] nodes;
		AdjList[] adjLists;
		
		public Graph(int n) { // make a graph with n elements
			nodes = new int[n];
			adjLists = new AdjList[n];
		}
	}
	public static class AdjList {
		ArrayList<Integer> alist;
		ArrayList<Integer> cost;
		
		public AdjList() {
			alist = new ArrayList<Integer>();
			cost = new ArrayList<Integer>();
		}
	}
}
