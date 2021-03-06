import java.io.*;
import java.util.*;

// calculate the shortest distance from node A to all other nodes (WEIGHTED)
// works for 1-5, not 6
// 1 direction connections
// priority queue method

public class OneDirMethod2 {
	
	
	
	
	public static int[][] steps;
	static AdjList[] adjs;
	static int N;
	static int a = 0;
	
	public static void main(String args[]) throws IOException {
		for (int i = 6; i <= 6; i++) {
			System.out.println("trial: " + i);
			long t = System.currentTimeMillis();
			String filename = "testData/adjW" + i + ".in";
			processOneFile(filename, i);
			System.out.println("time: " + (System.currentTimeMillis() - t));
			System.out.println();
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
					adjs[i].cost.add(cost);
					
					// add neighbors to the steps list
					steps[i][j] = cost;
				}
			}
		}
		
		br.close();
		/*
		for (int i = 0; i < N; i++) {
			System.out.println();
			for (int j = 0; j < N; j++) {
				System.out.print(steps[i][j] + " ");
			}
		}
		*/
		//System.out.println();
		
		int[][] dists = sDist(steps);
		
		for (int i = 0; i < N; i++) {
		 	System.out.println();
			for (int j = 0; j < N; j++) {
 				System.out.print(dists[i][j] + " ");
			}
		}
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("2" + k + ".out")));
		for (int i = 0; i < dists.length; i++) {
			pw.println(dists[0][i]);
			System.out.println(dists[0][i]);
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
			int k = qu.poll(); // pull from queue, k = intermediary
			HBV[k] = true;
			System.out.println("intermed: " + k);
			for (int i = 0; i < adjs[k].adj.size(); i++) { // get neighbors
				int neigh = adjs[k].adj.get(i);
				System.out.println("  neigh: " + neigh);
				
				
				/* teacher's suggestion (rejected) to make cleaner if else
				if(HBV[neigh]) {
					if ((a != neigh) && steps[a][cur] != 0 && steps[cur][neigh] != 0 && ((steps[a][cur] + steps[cur][neigh] < steps[a][neigh]) || steps[a][neigh] == 0)) {
						//System.out.println("   add and change to " + (steps[a][cur] + steps[cur][neigh]));
						steps[a][neigh] = steps[a][cur] + steps[cur][neigh];
						
						// changes have happened, so readd the neighbor to the list
						qu.add(neigh); 
						HBV[neigh] = true;
					}
				} else { // not yet visited, must now visit
					// not visited means that 
					if (steps[a][cur] != 0 && steps[cur][neigh] != 0 && ((steps[a][cur] + steps[cur][neigh] < steps[a][neigh]) || steps[a][neigh] == 0)) {
						steps[a][neigh] = steps[a][cur] + steps[cur][neigh];
					}
					
					qu.add(neigh);
					HBV[neigh] = true;
				}
				*/
				
				// if the neighbor is not a, a to cur and cur to neigh have 
				// and steps can be decreased or steps is currently 0 (not yet assigned)
				// update steps, add neighbor to qu, mark neighbor as visited
				
				// a to k and k to neigh have values
				// a is not the neighbor (don't loop back and change dist[a][a] to != 0)
				// steps[a][neigh] can be decreased or steps[a][neigh] is unknown
				if (steps[a][k] != 0 && steps[k][neigh] != 0 && a != neigh && 
						((steps[a][k] + steps[k][neigh] < steps[a][neigh]) || steps[a][neigh] == 0)) {
					System.out.println("   add and change from " + steps[a][neigh] + " to " + (steps[a][k] + steps[k][neigh]));
					
					
					steps[a][neigh] = steps[a][k] + steps[k][neigh]; // update
					qu.add(neigh); // changes have happened, so read the neighbor to the list
					HBV[neigh] = true;
					
					for (int j = 0; j < steps.length; j++) {
						System.out.print(steps[0][j] + " ");
					}
					System.out.println();
				}  
				if (HBV[neigh] == false) { // otherwise if the neighbor's not visited still add to qu
					// also make sure all neighbors get visited eventually
					//System.out.println("   add " + neigh);
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
