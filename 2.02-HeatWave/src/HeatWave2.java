import java.io.*;
import java.util.*;


/*
 * uses SDM2, doesn't work
input:
7 11 5 4
2 4 2
1 4 3
7 2 2
3 4 3
5 7 5
7 3 3
6 1 1
6 3 4
2 4 3
5 6 3
7 2 1

 */
public class HeatWave2 {
	
	static int[][] steps;
	static int Ts;
	static int Te;
	
	public static void main(String args[]) throws IOException {

		Scanner scan = new Scanner(System.in);
		StringTokenizer st = new StringTokenizer(scan.nextLine());
		
		int T = Integer.parseInt(st.nextToken()); // number of towns
		int C = Integer.parseInt(st.nextToken()); // number of connections
		Ts = Integer.parseInt(st.nextToken()); // starting town
		Te = Integer.parseInt(st.nextToken()); // ending town
		
		// nodes
		int[] nodes = new int[T]; 
		for (int i = 0; i < T; i++)
			nodes[i] = i;
		
		
		AdjList[] adjLists = new AdjList[T]; // adjacency lists
		for (int i = 0; i < T; i++)
			adjLists[i] = new AdjList(); // initialize
		
		
		//Graph g = new Graph(T, C); // create the graph
		steps = new int[T][T]; // adjacency matrix
		
		for (int i = 0; i < C; i++) { // scan in the connections from a to b
			st = new StringTokenizer(scan.nextLine());
			int a = Integer.parseInt(st.nextToken())-1; // 0...T-1 indexing
			int b = Integer.parseInt(st.nextToken())-1; // 0...T-1 indexing
			int cost = Integer.parseInt(st.nextToken());
			// add the edges
						adjLists[a].alist.add(b); 
						adjLists[b].alist.add(a);
						// add the costs
						adjLists[a].cost.add(cost);
						adjLists[b].cost.add(cost);
						
			steps[a][b] = cost; // add to adj matrix
		}
		
		
		for (int i = 0; i < steps.length; i++) { // for every list
			System.out.println();
			for (int j = 0; j < steps[i].length; j++) { // for every adjacency inside
				System.out.print(steps[i][j] + " ");
			}
		}
		System.out.println();
		scan.close();


		
		
		// graph has now been completely scanned in and initialized
		
		PriorityQueue<Integer> qu = new PriorityQueue<Integer>(5, idComparator);
		qu.add(Ts);
		boolean[] HBV = new boolean[T];
		HBV[Ts] = true;
		// have priority based on the number of steps from a to n?
		
		while (!qu.isEmpty()) {
			int cur = qu.poll();
			HBV[cur] = true;
			//System.out.println("cur: " + cur);
			for (int i = 0; i < adjLists[cur].alist.size(); i++) {
				// the neighbor: adjLists[cur].alist.get(i);
				int neigh = adjLists[cur].alist.get(i);
				//System.out.println("  neigh: " + neigh);
				
				if ((Ts != neigh) && ((steps[Ts][cur] + steps[cur][neigh] < steps[Ts][neigh]) || steps[Ts][neigh] == 0)) {
					//System.out.println("   add and change to " + (steps[Ts][cur] + steps[cur][neigh]));
					steps[Ts][neigh] = steps[Ts][cur] + steps[cur][neigh];
					qu.add(neigh);
				} if (HBV[neigh] == false) {
					//System.out.println("   add " + neigh);
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
		System.out.println();
		System.out.println(steps[Ts-1][Te-1]);
		
		
		
	}
	public static Comparator<Integer> idComparator = new Comparator<Integer>(){

		@Override
		public int compare(Integer x, Integer y) {
			if (steps[Ts][x] == 0) {
				return 1;
			} else if (steps[Ts][y] == 0) {
				return -1;
			} else if (steps[Ts][x] > steps[Ts][y]) {
				return 1;
			} else if (steps[Ts][x] < steps[Ts][y]) {
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
}
