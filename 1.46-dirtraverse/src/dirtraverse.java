
import java.io.*;
import java.util.*;

// use MST? but have to somehow death with the string lengths... weight?

public class dirtraverse {
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("dirtraverse.in"));
		int N = Integer.parseInt(br.readLine()); // total number of files and dirs

		Graph g = new Graph(N); // graph
		
		Node[] fds = new Node[N+1]; // 1...N indexing
		
		for (int i = 1; i <= N; i++) { // scan in each file or dir
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			String name = st.nextToken(); // scan in the name
			
			int numc = Integer.parseInt(st.nextToken()); // num fds inside
			
			int[] child = new int[numc]; // scan in the children
			for (int j = 0; j < numc; j++) 
				child[j] = Integer.parseInt(st.nextToken()); 
			
			fds[i] = new Node(i, name, numc, child); // construct the nodes
			//System.out.println(fds[i].id + "  " + fds[i].name + "  " + fds[i].numchild + "  " + Arrays.toString(fds[i].children));
		}
		
		br.close();
		

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("dirtraverse.out")));

		pw.println();
		pw.close();
	}
	public static class Node { // NODE
		int id;
		String name;
		int numchild;
		int[] children;
		public Node(int idn, String n, int nc, int[] c) {
			id = idn; // same as the index in the node[]
			name = n;
			numchild = nc;
			children = new int[numchild];
			for (int i = 0; i < numchild; i++) {
				children[i] = c[i];
			}
		}
	}
	public static class Graph {
		int[] nodes; // nodes, referencing outside fds
		AdjList[] edges; // adjacency lists
		
		public Graph(int n) { // make a graph with n elements
			nodes = new int[n];
			for (int i = 1; i <= n; i++) {
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
