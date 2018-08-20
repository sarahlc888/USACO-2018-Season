import java.io.*;
import java.util.*;

/*
 * sssp WITH negative edges mixed in (dijkstra can't handle those)
 * O(VE)
 * reference: https://www.geeksforgeeks.org/bellman-ford-algorithm-dp-23/
 * 
 * untested
 */
public class BellmanFord {
	static ArrayList<ArrayList<Pair>> adj = new ArrayList<ArrayList<Pair>>(); // graph
	static int[] dist;
	public static void main(String args[]) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("bf.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); // nodes
		int M = Integer.parseInt(st.nextToken()); // edges
		int K = Integer.parseInt(st.nextToken())-1; // source
		for (int i = 0; i < M; i++) { // scan edges in, adjust indexing
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken()) - 1; 
			int b = Integer.parseInt(st.nextToken()) - 1;
			int c = Integer.parseInt(st.nextToken());
			adj.get(a).add(new Pair(b, c));
		}
		dist = new int[N];
		for (int i = 0; i < N; i++) {
			dist[i] = Integer.MAX_VALUE; // init all nodes as unreachable
		}
		dist[K] = 0; // except the source
		
		// loop through and calc dists
		for (int i = 0; i < N-1; i++) { // N-1 edges is longest possible path
			for (int a = 0; a < adj.size(); a++) {
				for (int k = 0; k < adj.get(a).size(); k++) {
					Pair p = adj.get(a).get(k);
					int b = p.dest;
					int c = p.weight;
					
					// if a is reachable, consider the path from src...a...b
					if (dist[a] != Integer.MAX_VALUE && dist[b] > dist[a] + c) {
						dist[b] = dist[a] + c;
					}
					
				}
			}
		}
		
		// check for negative weight cycles (if you can still get a shorter path--forever!)
		for (int a = 0; a < adj.size(); a++) {
			for (int k = 0; k < adj.get(a).size(); k++) {
				Pair p = adj.get(a).get(k);
				int b = p.dest;
				int c = p.weight;
				
				if (dist[a] != Integer.MAX_VALUE && dist[b] > dist[a] + c) {
					System.out.println("NEGATIVE WEIGHT CYCLE");
				}
			}
		}
		
	}
	public static class Pair implements Comparable<Pair> {
		int dest;
		int weight;

		public Pair(int a, int b) {
			dest = a;
			weight = b;
		}
		@Override
		public int compareTo(Pair o) { // sort by x, then y
			if (dest == o.dest) return weight-o.weight;
			return o.dest-dest;
		}
		public String toString() {
			return dest + " " + weight;
		}
	}
}
