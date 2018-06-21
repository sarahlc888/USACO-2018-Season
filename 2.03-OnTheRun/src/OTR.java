import java.io.*;
import java.util.*;


/*
 * 
 * uses MST2, doesn't work
input:
4 10
1
9
11
19

 */
public class OTR {
	public static void main(String args[]) throws IOException {

		Scanner scan = new Scanner(System.in);
		StringTokenizer st = new StringTokenizer(scan.nextLine());

		int N = Integer.parseInt(st.nextToken()); // length of field
		int L = Integer.parseInt(st.nextToken()); // starting location

		int[] clumps = new int[N]; // stores locations of clumps

		for (int i = 0; i < N; i++) {
			clumps[i] = Integer.parseInt(scan.nextLine());
		}
		scan.close();
		/*
		int curPos = L; // B's position
		int[] relDist = new int[N]; // stores relative distance of clumps from B

		for (int i = 0; i < N; i++) {
			relDist[i] = Math.abs(curPos - clumps[i]);
		}*/
		// graph: MST

		Graph g = new Graph(N, N*(N+1)/2); // create the graph
		for (int i = 0; i < N; i++) {
			for (int j = i+1; j < N; j++) {
				int a = i;
				int b = j;
				int weight = Math.abs(clumps[i]-clumps[j]);
				g.addEdge(a, b, weight); 
				g.addEdge(b, a, weight); 
			}
		}


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
		
		System.out.println(EL);

		// L is the starting location
		int below = clumps[greatestBelow(clumps, L)];
		int above = clumps[leastAbove(clumps, L)];
		
		

		// going to above first
		int firstDist = above-L;
		int cost = 0;
		cost += N*firstDist;
		System.out.println(cost);
		for (int i = 0; i < EL.size(); i++) {
			
			cost += EL.get(i).cost*(N-i-1);
			System.out.println(cost);
		}
		
		System.out.println();
		
		// going to below first
		int cost2 = 0;
		firstDist = L-below;
		cost2 += N*firstDist;
		System.out.println(cost2);
		for (int i = 0; i < EL.size(); i++) {
			cost2 += EL.get(i).cost*(N-i-1);
			System.out.println(cost2);
		}
		
		System.out.println(Math.min(cost, cost2));
		


	}
	public static int greatestBelow(int[] arr, long val) {
		// returns greatest i <= val

		int lo = -1;
		int hi = arr.length-1;

		while (lo < hi) {
			int mid = (lo + hi + 1)/2; // +1 so lo can become hi in 1st if
			
			if (arr[mid] <= val) { // if mid is in range, increase
				lo = mid;
			} else { // mid is not in range, decrease
				hi = mid - 1;
			}
		}
		return hi;
		
	}
	public static int leastAbove(int[] arr, long val) {
		// returns smallest i >= val
		int lo = 0;
		int hi = arr.length-1;
		
		while (lo < hi) {
			int mid = (lo + hi)/2; // no +1 because you want hi to become lo in 1st if

			if (arr[mid] >= val) { // if mid is in range
				hi = mid;
			} else { // mid is not in range
				lo = mid + 1;
			}
		}
		return lo;
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
		public String toString() {
			return ""+cost;
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
