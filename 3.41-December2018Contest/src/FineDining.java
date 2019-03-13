import java.io.*;
import java.util.*;
/*
 * 
 */
public class FineDining {
	
	static int[] dist;
	static boolean[] visited; // visited for the dij
	static ArrayList<ArrayList<Pair>> adj = new ArrayList<ArrayList<Pair>>(); // adj list (weight, dest)
	static boolean[] hasHaybale;
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("dining.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); // num pastures
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
		int[] hays = new int[K]; // haybales with tastiness
		int[] tasty = new int[N];
		for (int i = 0; i < K; i++) { 
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken())-1;
			int b = Integer.parseInt(st.nextToken());
			hays[i] = a;
			tasty[a] = b;
		}
		br.close();
		
		// loop through haybales, decrement paths that end on a haybale
		for (int i = 0; i < K; i++) { 
			int curHay = hays[i];
			System.out.println("curHay: " + curHay);
			for (int j = 0; j < adj.get(curHay).size(); j++) {
				System.out.println("j: " + j);
				Pair cur = adj.get(curHay).get(j);
				int dest = cur.dest;
				for (int k = 0; k < adj.get(dest).size(); k++) {
					System.out.println("k :" + k);
					if (adj.get(dest).get(k).dest != curHay) continue;
					adj.get(dest).get(k).weight -= tasty[curHay];
				}
			}
		}
		
//		for (int i = 0; i < N; i++) {
//			System.out.println(adj.get(i));
//		}
		
		
		hasHaybale = new boolean[N];
		
		visited = new boolean[N];
		dist = new int[N];
		Arrays.fill(dist, Integer.MAX_VALUE);
		
		dij(N-1);
		
		System.out.println(Arrays.toString(dist));

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("dining.out")));

		pw.println();
		pw.close();
	}
	public static void dij(int S) { // S = starting node
		// TODO: store paths by storing parent
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
			
			dist[node] = weight; // assign weight

			
			// look at the neighbors and push to pq
			for (int i = 0; i < adj.get(node).size(); i++) {
				Pair neigh = adj.get(node).get(i); // neighbor
				pq.add(new Pair(neigh.weight + dist[node], neigh.dest)); // add to PQ
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
