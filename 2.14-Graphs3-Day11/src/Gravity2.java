import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/*
 * 4/5 test cases on first attempt
 * djikstra adj lists
 */
public class Gravity2 {
	static int cost = 0;
	static boolean[] visited; // visited for the dij
	static int[] dist;

	// adj list (weight, dest)
	static ArrayList<ArrayList<Pair>> adj = new ArrayList<ArrayList<Pair>>(); 
	
	static int[][] grid;
	static int[][] node2xy;
	static int[][] xy2node;
	
	static int N;
	static int M;
	
	public static void main(String args[]) throws IOException {
		// INPUT
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		// n by m grid
		N = Integer.parseInt(st.nextToken()); 
		M = Integer.parseInt(st.nextToken()); 

		grid = new int[N][M]; // grid
		node2xy = new int[N*M][2]; // map node ID to x-y position
		xy2node = new int[N][M]; // map x-y position to node ID

		int id = 0;
		int startID = -1;
		int endID = -1;

		for (int i = 0; i < N; i++) {
			String s = br.readLine();
			for (int j = 0; j < M; j++) {
				if (s.charAt(j) == '.') { // empty cell
					grid[i][j] = 0;
				} else if (s.charAt(j) == '#') { // blocked cell
					grid[i][j] = 1;
				} else if (s.charAt(j) == 'C') { // start point
					grid[i][j] = 2;
					startID = id;
				} else if (s.charAt(j) == 'D') { // ending point
					grid[i][j] = 3;
					endID = id;
				}
				node2xy[id][0] = i;
				node2xy[id][1] = j;
				xy2node[i][j] = id;
				id++;
			}

		}

		// BUILD GRAPH
		// nodes are all the squares
		// edges are between adjacent squares and gravity flipped squares
		// weights are 1 between nodes that flip gravity, 0 between non-flips

		
		// TODO: catch the case where you're sandwiched with blocks 
		// 		you don't move with a grav flip
		// 		you go through both if statements
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				//System.out.println("i: " + i + " j: " + j);
				adj.add(new ArrayList<Pair>()); // (cost, dest)
				
				if (grid[i][j] == 1) {
					//System.out.println("  cont1");
					continue; // if its blocked, skip it
				}

				int curNode = xy2node[i][j]; // node number
				
				// MAKE THE EDGES
				if (i+1 < N && grid[i+1][j] == 1) { // if there's a block below, normal grav
					//System.out.println("  here1");
					// go left
					if (j-1 >= 0) {
						int nextNode = settle(xy2node[i][j-1], 0);
						if (nextNode != -1) {
							//System.out.println("add1");
							adj.get(curNode).add(new Pair(0, nextNode));
						}
					}
					// go right
					if (j+1 < M && grid[i][j+1] != 1) { // in bounds and not blocked
						int nextNode = settle(xy2node[i][j+1], 0);
						if (nextNode != -1) {
							//System.out.println("add2");
							adj.get(curNode).add(new Pair(0, nextNode));
						}
						
					}
					// flip gravity
					int nextNode = settle(curNode, 1);
					if (nextNode != -1) {
						//System.out.println("add3");
						adj.get(curNode).add(new Pair(1, nextNode));
					}
				}
				if (i-1 >= 0 && grid[i-1][j] == 1) { // if there's a block above, flipped grav
					//System.out.println("  here2");
					// go left
					if (j-1 >= 0 && grid[i][j-1] != 1) { // in bounds and not a block
						int nextNode = settle(xy2node[i][j-1], 1);
						if (nextNode != -1) {
							//System.out.println("add1");
							adj.get(curNode).add(new Pair(0, nextNode));
						}
						
					}
					// go right
					if (j+1 < M && grid[i][j+1] != 1) { // if in bounds and not a block
						int nextNode = settle(xy2node[i][j+1], 1);
						if (nextNode != -1) {
							//System.out.println("add2");
							adj.get(curNode).add(new Pair(0, nextNode));
						}
						
					}
					
					// flip gravity
					int nextNode = settle(curNode, 0); 
					if (nextNode != -1) {
						//System.out.println("add3");
						adj.get(curNode).add(new Pair(1, nextNode));
					}
					
				}
				// if there is no block above or below, nothing can happen
			}
		}
		// account for if the ending point is "floating"
		if (settle(endID, 0) != endID && settle(endID, 1) != endID) {
			//System.out.println("here");
			if (!(settle(endID, 0) < 0)) {
				adj.get(settle(endID, 0)).add(new Pair(1, endID));

			}
			if (!(settle(endID, 1) < 0)) {
				adj.get(settle(endID, 1)).add(new Pair(1, endID));

			}
		}
		
		
		visited = new boolean[N*M];
		dist = new int[N*M];
		Arrays.fill(dist, -1);
		
		//for (int i = 0; i < N*M; i++) {
			//System.out.println("i: " + i + " " + adj.get(i));
		//}
		//System.out.println(node2xy[startID][0] + " " + node2xy[startID][1]);
		startID = settle(startID, 0); // settle the starting point
		if (startID == -1) { // falls automatically
			System.out.println(-1);
			return;
		}
		//System.out.println(startID);
		//System.out.println(node2xy[startID][0] + " " + node2xy[startID][1]);

		//System.out.println(endID);
		//System.out.println(node2xy[endID][0] + " " + node2xy[endID][1]);
		// DIJKSTRA from start to end O(N^2 log N^2)
		dij(startID);
		//System.out.println(Arrays.toString(dist));
		System.out.println(dist[endID]);
		
	}
	public static int settle(int curNode, int grav) { // returns node id, -1 if none
		// grav == 0 means normal (down), grav == 1 means flipped (up)
		int i = node2xy[curNode][0];
		int j = node2xy[curNode][1];
		if (grav == 0) {
			//System.out.println("here");
			if (grid[i+1][j] == 1) { // block below it
				//System.out.println("  " + i + " " + j);
				return curNode; // no changes needed
			} else {
				int ni = i;
				
				while (grid[ni][j] != 1) { // while no block below it, increment
					ni++;
					
					if (ni >= N) return -1;
				}
				ni--;
				//System.out.println("  " + ni + " " + j);
				return xy2node[ni][j]; // return the changed state
			}
			
		} else { // (grav == 1)
			if (grid[i-1][j] == 1) { // block "above" it
				//System.out.println("  " + i + " " + j);
				return curNode; // no changes needed
			} else {
				int ni = i;
				if (ni -1 < 0) return -1;
				while (grid[ni][j] != 1) { // while no block below it, decrement
					ni--;
					if (ni < 0) return -1;
				}
				ni++;
				//System.out.println("  " + ni + " " + j);
				return xy2node[ni][j]; // return the changed state
			}		
		}
	}
	public static void dij(int S) { // S = starting node
		PriorityQueue<Pair> pq = new PriorityQueue<Pair>();
		// add the edges that cross from S to VS
		pq.add(new Pair(0, S)); // weight, destination
		while (!pq.isEmpty()) {
			// let (weight, node) be at top
			Pair p = pq.poll();
			int weight = p.x;
			int node = p.y;


			// check if node is visited
			if (visited[node]) {
				continue; // if already visited, don't add it
			} else {
				visited[node] = true; // mark visited and proceed
			}

			dist[node] = weight; // assign weight


			// look at the neighbors and push to pq
			for (int i = 0; i < adj.get(node).size(); i++) {
				Pair neigh = adj.get(node).get(i); // neighbor
				pq.add(new Pair(neigh.x + dist[node], neigh.y)); // add to PQ
			}

			// add weight to the MST
			cost += weight;
		}
	}
	public static class Pair implements Comparable<Pair> {
		int x; // weight
		int y; // dest
		public Pair(int a, int b) {
			x = a;
			y = b;
		}
		@Override
		public int compareTo(Pair o) { // sort by edge
			return x-o.x;
		}
		public String toString() {
			return x + " " + y;
		}

	}
}
