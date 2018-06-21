import java.util.*;
import java.io.*;

// not fully tested

public class GraphImp0 {
	
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new FileReader("graph.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); // number of nodes
		int E = Integer.parseInt(st.nextToken()); // num edges
		Graph g = new Graph(N); // create the graph
		
		int[][] adjMatrix = new int[N][N]; // adjacency matrix
		
		/*
		 // for input = adjacency matrix format
		for (int i = 0; i < N; i++) { // scan in edges
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				adjMatrix[i][j] = Integer.parseInt(st.nextToken()); // fill in matrix
				g.addEdge(i, j, adjMatrix[i][j]); // add the edge
			}
		}
		*/
		
		// for input = v1, v2, weight for the edge
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine());
			
			int a = Integer.parseInt(st.nextToken()) - 1;
			int b = Integer.parseInt(st.nextToken()) - 1;
			int weight = Integer.parseInt(st.nextToken());
			
			// add edge to graph & adj matrix
			// if undirected/symm, add both dirs, otherwise just add in one dir
			
			g.addEdge(a, b, weight); 
			//g.addEdge(b, a, weight); 
			adjMatrix[a][b] = weight;
			//adjMatrix[b][a] = weight;
		}
		
		br.close();
		
	}
	
	public static class Graph {
		int[] nodes; // nodes
		AdjList[] edges; // adjacency lists
		
		public Graph(int n) { // make a graph with n elements
			nodes = new int[n];
			for (int i = 0; i < n; i++) {
				nodes[i] = i;
			}
			edges = new AdjList[n]; // adj list BY NODES, so length N
			for (int i = 0; i < n; i++) {
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
		public Point(int d, int w) {
			dest = d;
			weight = w;
		}
	}
	public static class Edge { // represents an edge in the edge list
		int source; // source
		int dest; // destination
		int weight; // cost
		
		public Edge() {
			source = 0;
			dest = 0;
			weight = 0;
		}
		public Edge(int s, int d, int w) {
			source = s;
			dest = d;
			weight = w;
		}
	}
}

