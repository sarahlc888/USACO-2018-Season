import java.io.*;
import java.util.*;

//calculate the shortest distance from node A to all other nodes (WEIGHTED)
//works for 1-5, not 6
//2 direction connections always

public class Method2 {
	
	public static int[][] steps; // adjacency matrix
	static AdjList[] adjs; // adjacency lists
	static int N; // number of nodes
	static int a = 0; // source
	
	public static void main(String args[]) throws IOException {
		for (int i = 1; i <= 1; i++) {
			System.out.println("trial: " + i);
			long t = System.currentTimeMillis();
			String filename = "testData/adjW" + i + ".in";
			processOneFile(filename, i);
			System.out.println("time: " + (System.currentTimeMillis() - t));
		}
	}
	public static void processOneFile(String filename, int k) throws IOException{
		
		BufferedReader br = new BufferedReader(new FileReader(filename));
		N = Integer.parseInt(br.readLine()); // number of nodes
		
		adjs = new AdjList[N]; // array of lists of neighbors for node 0...N-1
		for (int i = 0; i < N; i++) {
			adjs[i] = new AdjList();
		}
		
		// initially, steps will be a copy of the adjacency matrix
		steps = new int[N][N]; // steps[i][j] = weighted distance from i to j
		
		// process the adjacency matrix
		for (int i = 0; i < N; i++) { // node i
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) { // to node j
				int cost = Integer.parseInt(st.nextToken());
				if (cost != 0) { // if there is an edge
					// add edges and costs to adjacency lists
					adjs[i].adj.add(j);
					adjs[j].adj.add(i);
					adjs[i].cost.add(cost);
					adjs[j].cost.add(cost);
					
					// add neighbors to the steps list
					steps[i][j] = cost;
					steps[j][i] = cost;
				}
			}
		}
		
		br.close();
		
		for (int i = 0; i < N; i++) {
			System.out.println();
			for (int j = 0; j < N; j++) {
				System.out.print(steps[i][j] + " ");
			}
		}
		
		
		int[][] dists = sDist(steps);
		
		/*for (int i = 0; i < N; i++) {
			System.out.println(dists[0][i]);
		}*/
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("2" + k + ".out")));
		for (int i = 0; i < steps.length; i++) {
			pw.println(steps[0][i]);
			//System.out.print(steps[0][i]);
		}
		
		pw.close();
	}
	public static class AdjList {
		ArrayList<Integer> adj;
		ArrayList<Integer> cost;
		public AdjList() {
			adj = new ArrayList<Integer>();
			cost = new ArrayList<Integer>();
		}
	}
	public static int[][] sDist(int[][] steps) {
		
		PriorityQueue<Integer> qu = new PriorityQueue<Integer>(5, idComparator);
		qu.add(a);
		boolean[] HBV = new boolean[N];
		HBV[a] = true;
		// have priority based on the number of steps from a to n?
		
		while (!qu.isEmpty()) {
			int cur = qu.poll();
			HBV[cur] = true;
			System.out.println("cur: " + cur);
			for (int i = 0; i < adjs[cur].adj.size(); i++) {
				// the neighbor: adjLists[cur].alist.get(i);
				int neigh = adjs[cur].adj.get(i);
				System.out.println("  neigh: " + neigh);
				
				
				if ((a != neigh) && ((steps[a][cur] + steps[cur][neigh] < steps[a][neigh]) || steps[a][neigh] == 0)) {
					System.out.println("   add and change to " + (steps[a][cur] + steps[cur][neigh]));
					steps[a][neigh] = steps[a][cur] + steps[cur][neigh];
					qu.add(neigh);
					HBV[neigh] = true;
				} else if (HBV[neigh] == false) {
					System.out.println("   add " + neigh);
					qu.add(neigh);
					HBV[neigh] = true;
				}
				
			}
			
		}
		return steps;
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

}
