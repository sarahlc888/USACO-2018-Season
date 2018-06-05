import java.util.*;


import java.io.*;

// MST impelementation using Krushkal's algorithm
// not complete yet
// V and E indexing 0...n-1

public class test {
	
	public static void main(String[] args) throws IOException {
		
		
		int N = 3; // number of nodes
		int E = 3; // num edges
		Graph g = new Graph(N, E); // create the graph
		
		LinkedList<Edge> Q = new LinkedList<Edge>(); // list of all edges
		ArrayList<Edge> MST = new ArrayList<Edge>(); // MST edges, max = N-1 edges
		
		g.addEdge(1, 2, 1); 
		g.addEdge(2, 3, 2); 
		g.addEdge(1, 3, 3);
		
		// add edge to Q
		Q.add(new Edge(1, 2, 1));

		Q.add(new Edge(1, 3, 3));
		Q.add(new Edge(2, 3, 2));		
		
		Collections.sort(Q); // sort all the edges by weight
		
		for (int i = 0; i < Q.size(); i++) {
			System.out.println(Q.get(i).v1 + " " + Q.get(i).v2 + " " + Q.get(i).weight);
		}
		/*
		while (!Q.isEmpty() && MST.size() < N-1) {
			// while edges remain and the MST is not completed
			Q.removeFirst(); // get the first edge
		}
		*/
		
	}
	
	public static class Graph {
		int[] nodes; // nodes
		AdjList[] edges; // adjacency lists
		
		public Graph(int n, int e) { // make a graph with n elements
			nodes = new int[n];
			for (int i = 0; i < n; i++) {
				nodes[i] = i;
			}
			edges = new AdjList[e];
			for (int i = 0; i < e; i++) {
				edges[i] = new AdjList();
			}
		}
		public void addEdge(int source, int dest, int cost) { // add an edge
			edges[source].list.add(new Point(dest, cost));
		}
		public void addEdge(int source, int dest) { // no listed cost, so presume cost == 1
			edges[source].list.add(new Point(dest, 1));
		}
		public void printGraph() { // prints the graph
			for (int i = 0; i < nodes.length; i++) {
				System.out.println(i + ": {");
				for (int j = 0; j < edges[i].list.size(); j++) {
					System.out.print(edges[i].list.get(j) + ", ");
				}
				System.out.print("}");
			}
		}
		public ArrayList<Point> getAdjs(int source) { // get adjacencies
			return edges[source].list;
		}
		public ArrayList<Integer> getDests(int source) { // get list of destinations
			ArrayList<Integer> dests = new ArrayList<Integer>();
			for (int i = 0; i < edges[source].list.size(); i++) {
				dests.add(edges[source].list.get(i).dest);
			}
			return dests;
		}
		public ArrayList<Integer> getCosts(int source) { // get list of costs
			ArrayList<Integer> costs = new ArrayList<Integer>();
			for (int i = 0; i < edges[source].list.size(); i++) {
				costs.add(edges[source].list.get(i).weight);
			}
			return costs;
		}
		public int[] calcIndeg() { // calculate indegrees
			int[] indeg = new int[nodes.length];
			
			for (int i = 0; i < nodes.length; i++) {
				for (int j = 0; j < edges[i].list.size(); j++) {
					indeg[edges[i].list.get(j).dest]++;
				}
			}
			
			return indeg;
		}
		public int[] calcOutdeg() { // calculate outdegrees
			int[] outdeg = new int[nodes.length];
			
			for (int i = 0; i < nodes.length; i++) {
				outdeg[i] = edges[i].list.size();
			}
			
			return outdeg;
		}
	}
	public static class AdjList { // list of edges 
		ArrayList<Point> list; // destinations and costs
		public AdjList() {
			list = new ArrayList<Point>();
		}
	}
	public static class Point { // represents an edge in the adj lists
		int dest; // destination
		int weight; // cost
		
		public Point() {
			dest = 0;
			weight = 0;
		}
		public Point(int d, int c) {
			dest = d;
			weight = c;
		}
	}
	public static class Edge implements Comparable<Edge> { // represents an edge in the edge list
		int v1; // source
		int v2; // destination
		int weight; // cost
		
		public Edge() {
			v1 = 0;
			v2 = 0;
			weight = 0;
		}
		public Edge(int s, int d, int w) {
			v1 = s;
			v2 = d;
			weight = w;
		}
		public int compareTo(Edge e) {
			return weight - e.weight;
		}
		
	}
}

