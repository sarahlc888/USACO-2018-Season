import java.util.*;
import java.io.*;

// different input method

public class GraphImp4 {
	public static void main(String args[]) throws IOException {
		
		BufferedReader br = new BufferedReader(new FileReader("graph2.in"));
		int N = Integer.parseInt(br.readLine()); // number of nodes
		
		int[] nodes = new int[N]; // nodes
		
		for (int i = 0; i < N; i++)
			nodes[i] = Integer.parseInt(br.readLine()); // scan in values
		
		int m = Integer.parseInt(br.readLine()); // number of adjacencies below
		AdjList[] adjLists = new AdjList[N]; // adjacency lists
		for (int i = 0; i < N; i++)
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
		br.close();
		
		Graph g = new Graph(N);
		g.nodes = nodes;
		g.adjLists = adjLists;
		
		
		System.out.println("nodes");
		for (int i = 0; i < N; i++) {
			System.out.println(g.nodes[i]);
		}
		System.out.println("adjs");
		for (int i = 0; i < N; i++) {
			System.out.println("from " + i);
			for (int j = 0; j < g.adjLists[i].alist.size(); j++) {
				System.out.print("  to : " + g.adjLists[i].alist.get(j));
				System.out.println("  cost: " + g.adjLists[i].cost.get(j));
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
