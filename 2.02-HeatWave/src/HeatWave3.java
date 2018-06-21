import java.io.*;
import java.util.*;




/*
 * uses Method1, doesn't work
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
public class HeatWave3 {
	
	static int Ts;
	static int Te;
	
	static AdjList[] adjs;
	static int N;
	
	public static void main(String args[]) throws IOException {
		
		Scanner scan = new Scanner(System.in);
		StringTokenizer st = new StringTokenizer(scan.nextLine());
		
		int N = Integer.parseInt(st.nextToken()); // number of nodes/towns
		int C = Integer.parseInt(st.nextToken()); // number of connections
		Ts = Integer.parseInt(st.nextToken()); // starting town
		Te = Integer.parseInt(st.nextToken()); // ending town
		
					
		adjs = new AdjList[N]; // array of lists of neighbors for node 0...N-1
		for (int i = 0; i < N; i++) {
			adjs[i] = new AdjList();
		}
		
		// initially, steps will be a copy of the adjacency matrix
		int[][] steps = new int[N][N]; // steps[i][j] = weighted distance from i to j
		
		
		
		for (int i = 0; i < C; i++) { // scan in the connections from a to b
			st = new StringTokenizer(scan.nextLine());
			int a = Integer.parseInt(st.nextToken())-1; // 0...T-1 indexing
			int b = Integer.parseInt(st.nextToken())-1; // 0...T-1 indexing
			int cost = Integer.parseInt(st.nextToken());
			//g.addEdge(a, b, cost); // add edge
			if (steps[a][b]!= 0) {
				steps[a][b] = Math.min(steps[a][b], cost); // add to adj matrix
			} else {
				steps[a][b] = cost;
			}
			
		}
		
		
		
		
		
		// process the adjacency matrix
		for (int i = 0; i < N; i++) { // node i
			for (int j = 0; j < N; j++) { // to node j
				int cost = steps[i][j];
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
		
		scan.close();
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
		
		for (int i = 0; i < steps.length; i++) {
			System.out.println(steps[Ts-1][Te-1]);
		}
		
		
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
	
	public static class Graph {
		int[] nodes;
		AdjList[] adjLists;

		public Graph(int n) { // make a graph with n elements
			nodes = new int[n];
			adjLists = new AdjList[n];
		}
	}
	
	
}
