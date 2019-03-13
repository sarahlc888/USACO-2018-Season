import java.io.*;
import java.util.*;

/*
 * post competition redo of FD based off of online solution
 * 12/24/18
 */
public class RedoFD {
	static boolean[] hasHaybale;
	static int N;
	static int[][] dist;
	static boolean[] visited; // visited for the dij
	static ArrayList<ArrayList<Pair>> adj = new ArrayList<ArrayList<Pair>>(); // adj list (weight, dest)
	static int cost = 0;
	
	
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("dining.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // num pastures
		int M = Integer.parseInt(st.nextToken()); // num trails
		int K = Integer.parseInt(st.nextToken()); // num haybales
		for (int i = 0; i <= N; i++) {
			adj.add(new ArrayList<Pair>());
		}
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken())-1;
			int b = Integer.parseInt(st.nextToken())-1;
			int c = Integer.parseInt(st.nextToken());
			adj.get(a).add(new Pair(c, b));
			adj.get(b).add(new Pair(c, a));
		}
		boolean[] hasHay = new boolean[N];
		int[] hays = new int[K]; // haybales with tastiness
		int[] tasty = new int[N];
		for (int i = 0; i < K; i++) { 
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken())-1;
			int b = Integer.parseInt(st.nextToken());
			hays[i] = a;
			hasHay[a] = true;
			tasty[a] = Math.max(tasty[a], b);
		}
		br.close();
			
		
		dist = new int[2][N+1]; // distance to reach N-1 not using a haybale or using a haybale
		Arrays.fill(dist[0], Integer.MAX_VALUE-100001);
		Arrays.fill(dist[1], Integer.MAX_VALUE-100001);
		
		visited = new boolean[N+1];
		dij(N-1, 0);
		
		// delete paths to N-1 so that cow must use haybale
//		while (!adj.get(N-1).isEmpty()) {
//			adj.get(N-1).remove((int)0);
//		}
		// loop through haybales, decrement paths from haybale to N-1
//		for (int i = 0; i < N; i++) {
//			if (tasty[i] == 0) continue; // continue if no haybale
//			adj.get(N-1).add(new Pair(dist[0][i]-tasty[i], i));
//		}
		for (int i = 0; i < N; i++) {
			if (tasty[i] == 0) continue; // continue if no haybale
			adj.get(N).add(new Pair(dist[0][i]-tasty[i], i));
		}
//		System.out.println(adj.get(N-1));
//		System.out.println(adj.get(3));
		visited = new boolean[N+1];
//		dij(N-1, 1);
		dij(N, 1);
		
//		System.out.println(Arrays.toString(dist[0]));
//		System.out.println(Arrays.toString(dist[1]));
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("dining.out")));
		for (int i = 0; i < N-1; i++) {
			if (dist[1][i] <= dist[0][i]) {
//				System.out.println(1);
				pw.println(1);
			} else {
//				System.out.println(0);
				pw.println(0);
			}
		}
		pw.close();
	}
	public static void dij(int S, int dim) { // S = starting node
		PriorityQueue<Pair> pq = new PriorityQueue<Pair>();
		// add the edges that cross from S to VS
		pq.add(new Pair(0, S)); // weight, destination
		while (!pq.isEmpty()) {
			//System.out.println(Arrays.toString(dist));
			// let (weight, node) be at top
			Pair p = pq.poll();
			int weight = p.weight;
			int node = p.dest;
			
			
			// check if node is visited
			// MARK VISITED HERE AFTER POPPING FROM PQ
			// to make sure that you get the shortest path to that edge
			// if you marked visited after pushing initially, you could miss a shorter route
			if (visited[node]) {
				continue; // if already visited, don't add it
			} else {
				visited[node] = true; // mark visited and proceed
			}
			
			dist[dim][node] = weight; // assign weight

			
			// look at the neighbors and push to pq
			for (int i = 0; i < adj.get(node).size(); i++) {
				Pair neigh = adj.get(node).get(i); // neighbor
				pq.add(new Pair(neigh.weight + dist[dim][node], neigh.dest)); // add to PQ
			}
			
			cost += weight;
		}
	}
	public static void dij2(int S, int dim) { // S = starting node
		PriorityQueue<Pair> pq = new PriorityQueue<Pair>();
		// add the edges that cross from S to VS
		pq.add(new Pair(0, S)); // weight, destination
		dist[dim][S] = 0;
		while (!pq.isEmpty()) {
			// let (weight, node) be at top
			Pair p = pq.poll();
			int node = p.dest;


			// look at the neighbors and push to pq
			for (int i = 0; i < adj.get(node).size(); i++) {
				Pair neigh = adj.get(node).get(i); // neighbor
				if (dist[dim][neigh.dest] == Integer.MAX_VALUE-100001 || dist[dim][neigh.dest] > dist[dim][node]+neigh.weight) {
					
					dist[dim][neigh.dest] = dist[dim][node]+neigh.weight;
					pq.add(new Pair(dist[dim][neigh.dest], neigh.dest)); // add to PQ
				}
			}
		}
	}
	public static class Pair implements Comparable<Pair> {
		int weight;
		int dest;

		public Pair(int a, int b) {
			weight = a;
			dest = b;
		}
		@Override
		public int compareTo(Pair o) { // sort by x, then y
			if (weight == o.weight) return dest-o.dest;
			return weight-o.weight;
		}
		public String toString() {
			return weight + " " + dest;
		}
	}

}
