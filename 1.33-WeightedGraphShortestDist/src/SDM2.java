import java.util.*;
import java.io.*;


public class SDM2 {
	public static int[][] steps;
	public static int a;
	public static int b;
	public static void main(String args[]) throws IOException {
		
		BufferedReader br = new BufferedReader(new FileReader("graph2.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		a = Integer.valueOf(st.nextToken()); // source node
		b = Integer.valueOf(st.nextToken()); // destination node
		
		// the whole point is to find the distance between a and b
		
		int n = Integer.parseInt(br.readLine()); // number of nodes
		int[] nodes = new int[n]; // nodes
		for (int i = 0; i < n; i++)
			nodes[i] = Integer.parseInt(br.readLine()); // scan in values
		int m = Integer.parseInt(br.readLine()); // number of adjacencies below
		AdjList[] adjLists = new AdjList[n]; // adjacency lists
		for (int i = 0; i < n; i++)
			adjLists[i] = new AdjList(); // initialize
		for (int i = 0; i < m; i++) { 
			st = new StringTokenizer(br.readLine()); // scan in values
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
		
		steps = new int[n][n];
		for (int i = 0; i < g.adjLists.length; i++) { // for every list
			for (int j = 0; j < g.adjLists[i].alist.size(); j++) { // for every adjacency inside
				// update the cost from and to i
				steps[i][g.adjLists[i].alist.get(j)] = g.adjLists[i].cost.get(j);
				steps[g.adjLists[i].alist.get(j)][i] = g.adjLists[i].cost.get(j);
			}
		}
		
		// -----------------------------------------------------------------------------------
		// graph has now been completely scanned in and initialized
		
		
		PriorityQueue<Integer> qu = new PriorityQueue<Integer>(5, idComparator);
		qu.add(a);
		boolean[] HBV = new boolean[n];
		HBV[a] = true;
		// have priority based on the number of steps from a to n?
		
		while (!qu.isEmpty()) {
			int cur = qu.poll();
			HBV[cur] = true;
			System.out.println("cur: " + cur);
			for (int i = 0; i < adjLists[cur].alist.size(); i++) {
				// the neighbor: adjLists[cur].alist.get(i);
				int neigh = adjLists[cur].alist.get(i);
				System.out.println("  neigh: " + neigh);
				
				if ((a != neigh) && ((steps[a][cur] + steps[cur][neigh] < steps[a][neigh]) || steps[a][neigh] == 0)) {
					System.out.println("   add and change to " + (steps[a][cur] + steps[cur][neigh]));
					steps[a][neigh] = steps[a][cur] + steps[cur][neigh];
					qu.add(neigh);
				} if (HBV[neigh] == false) {
					System.out.println("   add " + neigh);
					qu.add(neigh);
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
	public static Comparator<Integer> idComparator = new Comparator<Integer>(){

		@Override
		public int compare(Integer x, Integer y) {
			if (steps[a][x] == 0) {
				return 1;
			} else if (steps[a][y] == 0) {
				return -1;
			} else if (steps[a][x] > steps[a][y]) {
				return 1;
			} else if (steps[a][x] < steps[a][y]) {
				return -1;
			} else {
				return 0;
			}
		}
	};
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
	/*
	class Checker implements Comparator<Integer>
	 {
	    public int compare(Integer x, Integer y)
	    {
	    	if (steps[a][x] > steps[a][y]) {
				return 1;
			} else if (steps[a][x] < steps[a][y]) {
				return -1;
			} else {
				return 0;
			}
	    }
	 }
	*/
}
