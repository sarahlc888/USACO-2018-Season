import java.io.*;
import java.util.*;
/*
 * Graph
 * DSU?
 */
public class MooTube {
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("mootube.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); // num videos
		int Q = Integer.parseInt(st.nextToken()); // num queries
		
		ArrayList<ArrayList<Pair>> adj = new ArrayList<ArrayList<Pair>>(); // graph (dest, weight)
		for (int i = 0; i < N; i++) { // init adj lists
			adj.add(new ArrayList<Pair>());
		}
		for (int i = 1; i < N; i++) { // scan in connections
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken())-1; // adjust indexing
			int b = Integer.parseInt(st.nextToken())-1;
			int c = Integer.parseInt(st.nextToken()); // weight
			adj.get(a).add(new Pair(b, c));
			adj.get(b).add(new Pair(a, c));
		}
		Pair[] queries = new Pair[Q];
		for (int i = 0; i < Q; i++) {
			st = new StringTokenizer(br.readLine());
			int k = Integer.parseInt(st.nextToken()); // min relevance
			int v = Integer.parseInt(st.nextToken())-1; // starting video
			queries[i] = new Pair(k, v);
		}
		br.close();
		Arrays.sort(queries);
		System.out.println(Arrays.toString(queries));

		for (int i = 0; i < Q; i++) { // queries
			
		}
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("mootube.out")));

		pw.println();
		pw.close();
	}
	public static class Pair implements Comparable<Pair> {
		int x;
		int y;

		public Pair(int a, int b) {
			x = a;
			y = b;
		}
		@Override
		public int compareTo(Pair o) { // sort by x, then y
			if (x == o.x) return y-o.y;
			return x-o.x;
		}
		public String toString() {
			return x + " " + y;
		}
	}


}
