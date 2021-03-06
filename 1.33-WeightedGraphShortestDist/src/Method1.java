import java.io.*;
import java.util.*;

//calculate the shortest distance from node A to all other nodes (WEIGHTED)
//works for 1-5, not 6
//slow.....
//2 direction connections ALWAYS

public class Method1 {
	
	static AdjList[] adjs;
	static int N;
	
	public static void main(String args[]) throws IOException {
		for (int i = 1; i <= 6; i++) {
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
		int[][] steps = new int[N][N]; // steps[i][j] = weighted distance from i to j
		
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
		/*
		for (int i = 0; i < N; i++) {
			System.out.println();
			for (int j = 0; j < N; j++) {
				System.out.print(steps[i][j] + " ");
			}
		}*/
		
		
		int[][] dists = sDist(steps);
		
		/*for (int i = 0; i < N; i++) {
			System.out.println(dists[0][i]);
		}*/
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("1" + k + ".out")));
		for (int i = 0; i < steps.length; i++) {
			pw.println(steps[0][i]);
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
		for (int k = 0; k < N; k++) { // intermediate point
			for (int i = 0; i < N; i++) {
				if (i==k) continue; // i, j, and k must be unique
				for (int j = 0; j < N; j++) {
					if (j==i) continue; // i, j, and k must be unique
					if (j==k) continue; // i, j, and k must be unique
					
					// check the cost of going from i to j through node k
					
					if ((steps[i][j] == 0 || steps[i][k] + steps[k][j] < steps[i][j])
							&& steps[i][k] != 0 && steps[k][j] != 0) {
						// if...
						//  - the dist from i to j (is yet unknown) or (would decrease if updated)
						//  - AND the dists from i to k and k to j are known
						
						// update the cost of distCost[i][j] to the new value going through k
						steps[i][j] = steps[i][k] + steps[k][j];
					}
					
				}
			}
		}
		return steps;
	}
	

}
