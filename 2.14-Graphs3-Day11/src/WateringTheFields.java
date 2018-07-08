import java.io.*;
import java.util.*;

/*
 * adj matrix version
 * times out 
 * 5/10 test cases
 */

public class WateringTheFields {
	static boolean[] visited; // visited for the MST
	static int[][] mat;
	static int N;
	static int cost = 0;
	
	public static void main(String args[]) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // num nodes
		int C = Integer.parseInt(st.nextToken()); // min cost
		
		mat = new int[N][N]; // matrix
		Pair[] fields = new Pair[N]; // locations of nodes
		
		for (int i = 0; i < N; i++) { // scan in the field locations
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			fields[i] = new Pair(a, b); 
		}
		for (int i = 0; i < N; i++) { // all fields
			for (int j = 0; j < N; j++) { // all fields
				mat[i][j] = cost(fields[i], fields[j]); // add edges
				if (mat[i][j] < C) mat[i][j] = Integer.MAX_VALUE; // edge doesn't exist if too small
			}
		}
		
		visited = new boolean[N];
		
		prim();
		System.out.println(cost);
	}
	public static void prim() {
		int S = 1; // starting node
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
			
			// look at the neighbors and push to pq
			for (int i = 0; i < N; i++) {
				int neigh = i;
				int cost = mat[node][neigh]; // cost
				
				pq.add(new Pair(cost, neigh)); // add to PQ
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
	}
	public static int cost(Pair a, Pair b) { // distance formula squared
		return (a.x-b.x)*(a.x-b.x)+(a.y-b.y)*(a.y-b.y);
	}
}
