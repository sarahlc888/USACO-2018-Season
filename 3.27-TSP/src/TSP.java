import java.io.*;
import java.util.*;
/*
 * Traveling salesman problem
 * not totally tested, probably correct
 */
public class TSP {
	static int[][] g;
	static int N;
	public static void main(String args[]) throws IOException {
		// starting point is 0
		BufferedReader br = new BufferedReader(new FileReader("tsp.in"));
		N = Integer.parseInt(br.readLine()); // N cities, index 0...N-1
		g = new int[N][N];
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				int d = Integer.parseInt(st.nextToken());
				g[i][j] = d; // distances
				g[j][i] = d;
			}
		}
		for (int i = 0; i < N; i++) { // adj list
			System.out.println(Arrays.toString(g[i]));
		}
		System.out.println(C());
	}
	public static int C() {
		// shortest route from 1 to k while visiting all other vertices exactly once
		ArrayList<Integer> all = new ArrayList<Integer>();
		for (int i = 0; i < N; i++) {
			all.add(i);
		}
		long cmin = Integer.MAX_VALUE;
		
		for (int k = 1; k < N; k++) { // "last" stop
			System.out.println("K: " + k + "  " );
			System.out.println(cmin + " " + (D(all, k)+g[k][0]));
			cmin = Math.min(cmin, D(all, k)+g[k][0]);
			
		}
		return (int)cmin;
	}
	public static int D(ArrayList<Integer> S, int k) {
		// shortest route from 1 to k that visits all vertices in S.
		// basecase: S contains vertices 1, k
		
		System.out.println("D(" + S + ", " + k + ")");
		
		if (S.size() == 2 && S.get(0) == 0 && S.get(1) == k || S.get(0) == k && S.get(1) == 0) {
			System.out.println("  BASECASE " + g[0][k]);
			
			return g[0][k];
		}
		
		long cmin = Integer.MAX_VALUE;
		
		for (int i = 0; i < S.size(); i++) {
			/* D(S, k) = min(D( S\{k}, j) + dist(j, k) ) j in S but j != 1 or k 
			S\{j} means S without j
			Check through all possibilities j of the “previous” vertex */
			int prev = S.get(i);
			if (prev == 0 || prev == k) continue;
			
			System.out.println("  prev: " + prev);
			
			ArrayList<Integer> S2 = (ArrayList<Integer>) S.clone();
			S2.remove((Object)k); // remove prev from the list
			
			int val = D(S2, prev) + g[prev][k];
			System.out.println("  " + cmin + " " + val);
			cmin = Math.min(val, cmin);
		}
		System.out.println("D(" + S + ", " + k + ")" + "  ANS: " + cmin);
		return (int)cmin;
	}
}
