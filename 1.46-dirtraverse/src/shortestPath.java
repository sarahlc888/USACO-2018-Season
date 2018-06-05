import java.io.*;
import java.util.*;

public class shortestPath {
	
	static int N;
	static int[][] steps;
	static int srce;
	
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("dirtraverse.in"));
		N = Integer.parseInt(br.readLine()); // total number of files and dirs

		Graph g = new Graph(N); // graph
		Node[] fds = new Node[N]; // 0...N-1 indexing
		
		
		steps = new int[N][N];
		
		for (int i = 0; i < N; i++) { // scan in each file or dir
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			String name = st.nextToken(); // scan in the name
			int numc = Integer.parseInt(st.nextToken()); // num children inside
			int[] child = new int[numc]; // scan in the children ids
			for (int j = 0; j < numc; j++) {
				child[j] = Integer.parseInt(st.nextToken()) - 1; // to fix indexing 
				steps[child[j]][i] = 3;
			}
				
			fds[i] = new Node(i, name, numc, child); // construct the nodes
			
			//System.out.println(fds[i].id + "  " + fds[i].name + "  " + fds[i].numchild + "  " + Arrays.toString(fds[i].children));
		}
		br.close();
		
	
		
		// loop through nodes to add edges
		for (int i = 0; i < N; i++) { // starting node
			for (int j = 0; j < fds[i].children.length; j++) { // children
				int par = i; // source
				int child = fds[i].children[j]; // dest
				
				// update adj lists
				if (fds[child].numchild == 0) { // if it is a file
					g.addEdge(par, child, fds[child].name.length());
				} else { // its a directory
					g.addEdge(par, child, fds[child].name.length() + 1); // cost = length of name + 1 (the /)
				}
				
				g.addEdge(child, par, 3); // reference = "../"
				
				
				
				// update adjacency matrix
				if (fds[child].numchild == 0) { // file
					steps[par][child] = fds[child].name.length();
				} else { // folder (need the extra "/"
					steps[par][child] = fds[child].name.length() + 1;
				}
				
			}
		}
		
		int[][] dists = new int[N][N];
		
		
		
		for (int srce = 0; srce < N; srce++) {
			//System.out.println("srce: " + srce);
			int[][] curdists = sDist(steps, srce, g);
			
			for (int i = 0; i < N; i++) {
				dists[srce][i] = curdists[srce][i];
			}
			
			
		}
		/*
		for (int i = 0; i < N; i++) {
			System.out.println();
			for (int j = 0; j < N; j++) {
				System.out.print(dists[i][j] + " ");
			}
		}
		System.out.println();
		*/
		
		long minsum = Long.MAX_VALUE; // min length to access everything
		for (int i = 0; i < N; i++) {
			// all the possible starting places
			
			long cursum = 0;
			
			for (int j = 0; j < N; j++) {
				if (fds[j].numchild == 0) {
					cursum += (long) dists[i][j];
				}
			}
			if (cursum < minsum) minsum = cursum;
		}
		
		//System.out.println(minsum);
		
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("dirtraverse.out")));
		pw.println(minsum);
		pw.close();
	}
	public static int[][] sDist(int[][] steps, int srce, Graph g) {
		int[][] steps1 = new int[steps.length][steps[0].length];
		for (int i = 0; i < steps.length; i++) {
			for (int j = 0; j < steps[0].length; j++) {
				steps1[i][j] = steps[i][j];
			}
		}
		
		PriorityQueue<Integer> qu = new PriorityQueue<Integer>(5, idComparator);
		qu.add(srce);
		boolean[] HBV = new boolean[N];
		HBV[srce] = true;
		// have priority based on the number of steps from a to n?
		
		while (!qu.isEmpty()) {
			int cur = qu.poll();
			HBV[cur] = true;
			//System.out.println("cur: " + cur);
			for (int i = 0; i < g.edges[cur].list.size(); i++) {
				
				// the neighbor: adjLists[cur].alist.get(i);
				int neigh = g.edges[cur].list.get(i).dest;
				//System.out.println("  neigh: " + neigh);
				
				if ((srce != neigh) && ((steps1[srce][cur] + steps1[cur][neigh] < steps1[srce][neigh]) || steps1[srce][neigh] == 0)) {
					//System.out.println("   add and change to " + (steps[srce][cur] + steps[cur][neigh]));
					steps1[srce][neigh] = steps1[srce][cur] + steps1[cur][neigh];
					qu.add(neigh);
					HBV[neigh] = true;
				} else if (HBV[neigh] == false) {
					//System.out.println("   add " + neigh);
					qu.add(neigh);
					HBV[neigh] = true;
				}
				
			}
			
		}
		/*for (int i = 0; i < N; i++) {
			System.out.print(steps1[srce][i] + " ");
		}
		System.out.println();*/
		
		return steps1;
	}
	public static Comparator<Integer> idComparator = new Comparator<Integer>(){

		@Override
		public int compare(Integer x, Integer y) {
			if (steps[srce][x] == 0) {
				return 1;
			} else if (steps[srce][y] == 0) {
				return -1;
			} else if (steps[srce][x] > steps[srce][y]) {
				return 1;
			} else if (steps[srce][x] < steps[srce][y]) {
				return -1;
			} else {
				return 0;
			}
		}
	};
	public static class Graph {
		int[] nodes; // nodes, referencing outside fds
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
			//System.out.println(source);
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

}
