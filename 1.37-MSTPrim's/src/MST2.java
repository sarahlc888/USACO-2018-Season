import java.util.*;
import java.io.*;

// minimum spanning tree

// refer to pg. 140 of comp prog book
// uses priority queue to find shortest edge to add to tree instead of linear
// O(E*logV) or thereabouts


// MSTs are undirected

public class MST2 {
	
	public static void main(String[] args) throws IOException {
		int num = 6; // file
		
		BufferedReader br = new BufferedReader(new FileReader(num + ".in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); // number of nodes
		int E = Integer.parseInt(st.nextToken()); // num edges
		Graph g = new Graph(N, E); // create the graph
		
		
		for (int i = 0; i < E; i++) {
			// scan in the input: v1, v2, weight for the edge
			st = new StringTokenizer(br.readLine());
			
			int a = Integer.parseInt(st.nextToken()) - 1;
			int b = Integer.parseInt(st.nextToken()) - 1;
			int weight = Integer.parseInt(st.nextToken());
			
			// add the edge to graph and adj matrix
			// undirected, make sure to add both ways
			g.addEdge(a, b, weight); 
			g.addEdge(b, a, weight); 
			
		}
		
		br.close();
		
		// list of edges [weight, v1, v2] - weight first for the priority 
		PriorityQueue<Edge> edgeQ = new PriorityQueue<Edge>(edgeComparator);
		
		// list of groups
		ArrayList<Integer> S = new ArrayList<Integer>();
		S.add(0); // start off with just 0 in S
		for (int i = 0; i < g.getAdjs(0).size(); i++) { // all edges from 0
			// put all edges (v1, v2, weight) into the edgeQ
			edgeQ.add(new Edge(0, g.getAdjs(0).get(i).dest, g.getAdjs(0).get(i).cost));
		}
		
		ArrayList<Edge> EL = new ArrayList<Edge>(); // list of edges in the MST
		
		while (!edgeQ.isEmpty()) {
			Edge e = edgeQ.poll(); // shortest edge
			
			int x; // node in S
			int y; // node to be added to S
			
			// undirected edges!
			if (S.contains(e.source) && !S.contains(e.dest) 
					|| S.contains(e.dest) && !S.contains(e.source)) {
				// if the edge goes from S to a new node
				
				// determine which node is new
				if (S.contains(e.source)) {
					x = e.source;
					y = e.dest;
				} else {
					x = e.dest;
					y = e.source;
				}
				
				S.add(y); // add the new node to S
				EL.add(e); // add edge to edge list
				
				// add edges of new node to edgeQ
				for (int i = 0; i < g.getAdjs(y).size(); i++) {
					// get edges connected to node y and add to the edgeq
					Edge newedge = new Edge (y, g.getAdjs(y).get(i).dest, g.getAdjs(y).get(i).cost);
					edgeQ.add(newedge);
				}
			}
		}
		
		
		PrintWriter pw = new PrintWriter(new File(num + "v2.out"));
		
		for (int i = 0; i < EL.size(); i++) { // print out the MST
			// go back to correct indexing
			pw.print("v1: " + (EL.get(i).source + 1));
			pw.print("  v2: " + (EL.get(i).dest + 1));
			pw.println("  w: " + EL.get(i).cost);
		}
		
		pw.close();
		
	}
	
	public static class Graph {
		int[] nodes; // nodes
		AdjList[] edges; // adjacency lists
		
		public Graph(int n, int e) { // make a graph with n elements
			nodes = new int[n];
			for (int i = 0; i < n; i++) {
				nodes[i] = i;
			}
			edges = new AdjList[n];
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
				costs.add(edges[source].list.get(i).cost);
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
		int cost; // cost
		
		public Point() {
			dest = 0;
			cost = 0;
		}
		public Point(int d, int c) {
			dest = d;
			cost = c;
		}
	}
	public static class Edge { // represents an edge in the edge list
		int source; // source
		int dest; // destination
		int cost; // cost
		
		public Edge() {
			source = 0;
			dest = 0;
			cost = 0;
		}
		public Edge(int s, int d, int c) {
			source = s;
			dest = d;
			cost = c;
		}
	}
	public static Comparator<Edge> edgeComparator = new Comparator<Edge>(){

		@Override
		public int compare(Edge x, Edge y) {
			if (x.cost > y.cost) return 1;
			else if (x.cost < y.cost) return -1;
			else return 0;
		}
	};
}

