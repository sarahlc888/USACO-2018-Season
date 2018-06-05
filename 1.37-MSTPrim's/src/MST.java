import java.util.*;
import java.io.*;

//minimum spanning tree

// slower version

// MSTs are undirected
//indexing 1...N for input

// O(V*E)


// additional instructions:
// make it faster than version 1, precalculate dist between all nodes in R 
// and all nodes in S so that it doesn't recalc every time so O(N*E)
// but how? R and S change every time

public class MST {
	
	public static void main(String[] args) throws IOException {
		int num = 6; // file
		
		BufferedReader br = new BufferedReader(new FileReader(num + ".in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); // number of nodes
		int E = Integer.parseInt(st.nextToken()); // num edges
		
		//System.out.println(N + " " + E);
		
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
		
		// groups of nodes
		ArrayList<Integer> R = new ArrayList<Integer>();
		ArrayList<Integer> S = new ArrayList<Integer>();
		S.add(0); // start off with just 0 in S
		for (int i = 1; i < N; i++) { // put everything else in R
			R.add(i);
		}
		
		ArrayList<Edge> EL = new ArrayList<Edge>(); // list of edges in the MST
		
		while (!R.isEmpty()) {
			//System.out.println("R size: " + R.size());
			// while r is not empty
			
			// find edge from r to s with min weight
			// check all edges sourcing from R to S
			
			// the shortest edge
			int minWeight = Integer.MAX_VALUE;
			int v1 = -1; 
			int v2 = -1;
			
			
			for (int i = 0; i < R.size(); i++) {
				// R.get(i) = source node
				for (int j = 0; j < g.getAdjs(R.get(i)).size(); j++) { // go through edges
					
					int destin = g.getAdjs(R.get(i)).get(j).dest; // destination
					//System.out.println("dest: " + destin);
					if (S.contains(destin)) { // if the node goes from R to S
						// edge j from node i in R
						
						//System.out.println("yes");
						
						int weight = g.getAdjs(R.get(i)).get(j).cost; 
						
						if (weight < minWeight) { // weight --> minweight
							minWeight = weight;
							v1 = R.get(i); // source
							v2 = g.getAdjs(R.get(i)).get(j).dest; // dest
						}
					}
				}
			}
			
			//System.out.println("v1: " + v1);
			//System.out.println("v2: " + v2);
			
			EL.add(new Edge(v1, v2, minWeight)); // add the edge to EL
			R.remove((Integer) v1); // remove v1 from R (use integ cast to remove obj not ind)
			S.add(v1); // put it in S
			
		}
		
		PrintWriter pw = new PrintWriter(new File(num + "v1.out"));
		
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
			//System.out.println(source + " " + dest + " " + cost);
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
}

