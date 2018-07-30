import java.io.*;
import java.util.*;
/*
 * 
 */
public class CowAtLarge {
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("atlarge.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); // num barns
		int K = Integer.parseInt(st.nextToken()); // starting barn
		
		ArrayList<ArrayList<Integer>> adj = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < N; i++) { // init adj lists
			adj.add(new ArrayList<Integer>());
		}
		
		for (int i = 1; i < N; i++) { // scan in connections
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken())-1; 
			int b = Integer.parseInt(st.nextToken())-1; 
			adj.get(a).add(b);
			adj.get(b).add(a);
		}
		br.close();
		
//		System.out.println(adj);

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("atlarge.out")));

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
			return o.x-x;
		}
		public String toString() {
			return x + " " + y;
		}
	}


}
