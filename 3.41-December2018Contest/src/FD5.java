import java.io.*;
import java.util.*;
/*
 * see v4, this is trash
 */
public class FD5 {
	static int[][] dist;
	static boolean[] visited; // visited for the dij
	static ArrayList<ArrayList<Pair>> adj = new ArrayList<ArrayList<Pair>>(); // adj list (weight, dest)
	static boolean[] hasHaybale;
	static int N;
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("dining.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // num pastures
		int M = Integer.parseInt(st.nextToken()); // num trails
		int K = Integer.parseInt(st.nextToken()); // num haybales
		for (int i = 0; i < N; i++) {
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
		
		// loop through haybales, decrement paths that end on a haybale
		for (int i = 0; i < N; i++) {
			if (tasty[i] == 0) continue;
	
			int curHay = i;
//			System.out.println("curHay: " + curHay);
			for (int j = 0; j < adj.get(curHay).size(); j++) {
//				System.out.println("j: " + j);
				Pair cur = adj.get(curHay).get(j);
				
				adj.get(cur.dest).add(new Pair(cur.weight-tasty[curHay], curHay, true));
			}
		}
		
//		for (int i = 0; i < N; i++) {
//			System.out.println(adj.get(i));
//		}
		
		
		hasHaybale = new boolean[N];
		
		visited = new boolean[N];
		dist = new int[N][2]; // distance to reach using or not using a negative edge weight
		for (int i = 0; i < N; i++) {
			Arrays.fill(dist[i], Integer.MAX_VALUE);
		}
		
		
		dij(N-1);
		
		for (int i = 0; i < N; i++) {
			System.out.println(Arrays.toString(dist[i]));
		}
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("dining.out")));
		for (int i = 0; i < N-1; i++) {
			if (hasHay[i]) { // if there is a haybale at the starting point
				System.out.println(1);
				pw.println(1);
			} else if (dist[i][1] == -1 || dist[i][1] > dist[i][0]) {
				System.out.println(0);
				pw.println(0);
			} else {
				System.out.println(1);
				pw.println(1);
			}
		}
		pw.close();
	}
	public static void dij(int S) { // S = starting node
		PriorityQueue<Pair> pq = new PriorityQueue<Pair>();
		// add the edges that cross from S to VS
		pq.add(new Pair(0, S)); // weight, destination
		while (!pq.isEmpty()) {
			//System.out.println(Arrays.toString(dist));
			// let (weight, node) be at top
			Pair p = pq.poll();
			int weight = p.weight;
			int node = p.dest;
			
			if (p.toHay && !p.alreadyHay) { // going to a haybale

				if (visited[node]) {
					continue; // if already visited, don't add it
				} else {
					visited[node] = true; // mark visited and proceed
				}
				
				dist[node][1] = weight; // assign weight
				
				// look at the neighbors and push to pq
				for (int i = 0; i < adj.get(node).size(); i++) {
					Pair neigh = adj.get(node).get(i); // neighbor
					pq.add(new Pair(neigh.weight + dist[node][1], neigh.dest, true, true)); // add to PQ
				}
			} else { // no haybale
				// check if node is visited
				// MARK VISITED HERE AFTER POPPING FROM PQ
				// to make sure that you get the shortest path to that edge
				// if you marked visited after pushing initially, you could miss a shorter route
				if (visited[node]) {
					continue; // if already visited, don't add it
				} else {
					visited[node] = true; // mark visited and proceed
				}
				
				dist[node][0] = weight; // assign weight
				
				// look at the neighbors and push to pq
				for (int i = 0; i < adj.get(node).size(); i++) {
					Pair neigh = adj.get(node).get(i); // neighbor
					pq.add(new Pair(neigh.weight + dist[node][0], neigh.dest, p.alreadyHay)); // add to PQ
				}
			}
			
			
			
		}
	}
	public static class Pair implements Comparable<Pair> {
		int weight;
		int dest;
		boolean toHay = false;
		boolean alreadyHay = false;
		
		public Pair(int a, int b) {
			weight = a;
			dest = b;
		}
		public Pair(int a, int b, boolean c) {
			weight = a;
			dest = b;
			toHay = c;
		}
		public Pair(int a, int b, boolean c, boolean d) {
			weight = a;
			dest = b;
			toHay = c;
			alreadyHay = d;
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
