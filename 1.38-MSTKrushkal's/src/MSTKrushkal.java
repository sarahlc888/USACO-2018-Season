import java.util.*;


import java.io.*;

// MST impelementation using Krushkal's algorithm
// complete, semi-tested (tested on the weak test cases from the Prim's versions)
// V and E indexing 1...n
// complexity?

public class MSTKrushkal {
	
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new FileReader("6.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); // number of nodes
		int E = Integer.parseInt(st.nextToken()); // num edges
		Graph g = new Graph(N, E); // create the graph
		
		ArrayList<Edge> Q = new ArrayList<Edge>(); // list of all edges
		ArrayList<Edge> MST = new ArrayList<Edge>(); // MST edges, max = N-1 edges
		DisjointSet ds = new DisjointSet(N); // disjoint set, maps nodes into groups
		
		// edge input = v1, v2, weight
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine());
			
			int a = Integer.parseInt(st.nextToken()) - 1;
			int b = Integer.parseInt(st.nextToken()) - 1;
			int weight = Integer.parseInt(st.nextToken());
			
			// add edge to graph, undirected so add it in both directions
			g.addEdge(a, b, weight); 
			g.addEdge(b, a, weight); 
			// add edge to Q
			Q.add(new Edge(a, b, weight));
		}
		
		Collections.sort(Q); // sort all the edges by weight, smallest weight first
		
		int i = 0; // iterate through edgelist Q
		
		while (i < Q.size() && MST.size() < N-1) { // while edges remain and MST != completed
			
			Edge cur = Q.get(i); // get the next edge, the shortest of the remaining
			
			boolean cycle = false;
			
			
			if (ds.find(cur.v1) != ds.find(cur.v2)) {
				// if the two nodes have not yet been connected
				// otherwise, adding cur to the MST would create a cycle
				// (there would already be another edge tethering cur in the MST)

				MST.add(cur); // add cur to the MST
				ds.union(cur.v1, cur.v2); // merge the sets for v1 and v2
			}
			i++;
		}
		
		br.close();
		
		for (i = 0; i < MST.size(); i++) {
			System.out.println((MST.get(i).v1+1) + " " + (MST.get(i).v2+1) + " " + MST.get(i).weight);
		}
		
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
		public int compareTo(Edge e) { // sort by weight
			return weight - e.weight;
		}
		
	}
	public static class DisjointSet {
		DSNode[] nodes; // array of Nodes
		
		public DisjointSet(int N) {
			// N = number of elements
			
			// create and initialize the node[]
			nodes = new DSNode[N];
			
			for (int i = 0; i < N; i++) {
				nodes[i] = new DSNode();
				nodes[i].ID = i;
				// each node is in its own set, parent = null
			}
			
		}
		
		public int union(int a, int b) {
			// join the sets with node id a and node id b, returns the final parent
			
			DSNode rootA = nodes[find(a)]; // root of A
			DSNode rootB = nodes[find(b)]; // root of B
			rootB.parentID = rootA.ID; // make rootA parent of rootB
			return rootA.ID; // return rootA
		}
		public int find(int a) {
			// returns the head of the set with node a
			DSNode curnode = nodes[a]; 
			int steps = 0; // number of steps between final parent and curnode
			// will = 0 if all connections are direct (* shape instead of chain)
			while (curnode.parentID >= 0) {
				curnode = nodes[curnode.parentID]; // jump up to the parent
				steps++; // increment steps
			}
			if (steps > 1) { // if the connection is not direct, streamline path
				compress(a, curnode.ID);
			}
			return curnode.ID; // the final parent of a
		}
		public void compress(int a, int root) {
			DSNode curnode = nodes[a];
			
			// trace the chain from curnode to root and make cur.parent = root
			
			while (curnode.ID != root) { // while curnode is not the root
				int temp = curnode.parentID; // the parent of curnode
				curnode.parentID = root; // set the direct root path
				curnode = nodes[temp]; // make the parent curnode, cont tracing the path
			}
		}
	}
	public static class DSNode { // node in disjoint set
		int val;
		int ID;
		int parentID;
		
		public DSNode(int v, int id, int pid) {
			val = v;
			ID = id;
			parentID = pid;
		}
		public DSNode() {
			val = 0;
			ID = 0;
			parentID = -1;
		}
	}
}

