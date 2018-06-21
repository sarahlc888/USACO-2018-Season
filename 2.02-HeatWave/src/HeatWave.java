import java.io.*;
import java.util.*;


/*
 * uses SDM1
 * 
 * 
 * WORKS!!!!!!!
 * ignore all other versions
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
public class HeatWave {
	public static void main(String args[]) throws IOException {

		Scanner scan = new Scanner(System.in);
		StringTokenizer st = new StringTokenizer(scan.nextLine());
		
		int T = Integer.parseInt(st.nextToken()); // number of towns
		int C = Integer.parseInt(st.nextToken()); // number of connections
		int Ts = Integer.parseInt(st.nextToken()); // starting town
		int Te = Integer.parseInt(st.nextToken()); // ending town
		
		//Graph g = new Graph(T, C); // create the graph
		int[][] adjMatrix = new int[T][T]; // adjacency matrix
		
		for (int i = 0; i < C; i++) { // scan in the connections from a to b
			st = new StringTokenizer(scan.nextLine());
			int a = Integer.parseInt(st.nextToken())-1; // 0...T-1 indexing
			int b = Integer.parseInt(st.nextToken())-1; // 0...T-1 indexing
			int cost = Integer.parseInt(st.nextToken());
			//g.addEdge(a, b, cost); // add edge
			if (adjMatrix[a][b]!= 0) {
				adjMatrix[a][b] = Math.min(adjMatrix[a][b], cost); // add to adj matrix
				adjMatrix[b][a] = Math.min(adjMatrix[b][a], cost); // add to adj matrix
			} else {
				adjMatrix[a][b] = cost;
				adjMatrix[b][a] = cost;
			}
			
		}
		/*
		for (int i = 0; i < adjMatrix.length; i++) { // for every list
			System.out.println();
			for (int j = 0; j < adjMatrix[i].length; j++) { // for every adjacency inside
				System.out.print(adjMatrix[i][j] + " ");
			}
		}
		System.out.println();*/
		scan.close();


		
		
		// graph has now been completely scanned in and initialized
		
		
		
		for (int k = 0; k < T; k++) {
			for (int i = 0; i < T; i++) {
				if (i==k) continue; // i, j, and k must be unique
				for (int j = 0; j < T; j++) {
					if (j==i) continue; // i, j, and k must be unique
					if (j==k) continue; // i, j, and k must be unique
					
					// check the cost of going from i to j through node k
					
					if ((adjMatrix[i][j] == 0 || adjMatrix[i][k] + adjMatrix[k][j] < adjMatrix[i][j])
							&& adjMatrix[i][k] != 0 && adjMatrix[k][j] != 0) {
						// if...
						//  - the dist from i to j (is yet unknown) or (would decrease if updated)
						//  - AND the dists from i to k and k to j are known
						
						// update the cost of distCost[i][j] to the new value going through k
						adjMatrix[i][j] = adjMatrix[i][k] + adjMatrix[k][j];
					}
					
				}
			}
		}
		
		
		System.out.println(adjMatrix[Ts-1][Te-1]);
		
		
		
	}
	/*
	public static class Graph { // not fully tested
		int[] nodes; // nodes
		AdjList[] edges; // adjacency lists
		
		public Graph(int n, int e) { // make a graph with n elements
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
*/
}
