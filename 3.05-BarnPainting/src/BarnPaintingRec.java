import java.io.*;
import java.util.*;
/* 
 * USACO 2017 December Contest, Gold
 * Problem 2. Barn Painting
 * recursive idea
 * 
 * remember to mod
 * 
 * 2/10 test cases
 * 10/10 after modifying the adj set up
 */
public class BarnPaintingRec {
	static int N;
	static ArrayList<ArrayList<Integer>> adj;
	static long[][] DP;
	static int[] color;
	static int MOD = 1000000007;
	public static void main(String args[]) throws IOException {
		// INPUT
		BufferedReader br = new BufferedReader(new FileReader("barnpainting.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // num barns
		int K = Integer.parseInt(st.nextToken()); // num barns already painted
		
		adj = new ArrayList<ArrayList<Integer>>(); // links par to child
		color = new int[N]; // color[i] = color of i (0 to 2 if it has one, -1 if not)
		for (int i = 0; i < N; i++) {
			color[i] = -1; // init as no parent and no color
			adj.add(new ArrayList<Integer>()); // init with empty children
		}
		for (int i = 0; i < N-1; i++) { // connections between barns
			st = new StringTokenizer(br.readLine());
			// connected barns with adjusted indexing
			int a = Integer.parseInt(st.nextToken()) - 1;
			int b = Integer.parseInt(st.nextToken()) - 1;
			
			adj.get(a).add(b);
			adj.get(b).add(a);
		}
		for (int i = 0; i < K; i++) {
			st = new StringTokenizer(br.readLine());
			int barn = Integer.parseInt(st.nextToken()) - 1;
			int col = Integer.parseInt(st.nextToken()) - 1;
			color[barn] = col;
		}
		br.close();
		
		int start = 0;
//		System.out.println(Arrays.toString(color));
//		System.out.println(adj);
		
		// DP[N][3] where DP[node][color] = number of ways to color
		DP = new long[N][3];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < 3; j++) {
				DP[i][j] = -1;
			}
		}
		long n1 = func(start, 0, -1, -1) % MOD;
//		DP[0][0] = n1;
		long n2 = func(start, 1, -1, -1) % MOD;
//		DP[0][1] = n2;
		long n3 = func(start, 2, -1, -1) % MOD;
//		DP[0][2] = n3;
		
//		System.out.println(n1);
//		System.out.println(n2);
//		System.out.println(n3);
//		System.out.println(n1+n2+n3);
////		for (int i = 0; i < N; i++) {
////			System.out.println(Arrays.toString(DP[i]));
////		}
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("barnpainting.out")));

		pw.println((n1+n2+n3)%MOD);
		pw.close();
	}
	public static long func(int node, int col, int par, int parColor) {
		// returns DP[node][color] which is number of ways to color it this way
		
		if (DP[node][col] != -1) { // already done
//			System.out.println("Dbarn: " + node + " col: " + col + " par: " + par + " pcol: " + parColor + " numways: " + numWays);
			return DP[node][col];
		}
		
		if (col == parColor || color[node] != -1 && col != color[node]) { // basecase
			DP[node][col] = 0;
			return DP[node][col];
		}
		
		DP[node][col] = 1;
		
		for (int k = 0; k < adj.get(node).size(); k++) { // children of node
			int sum = 0;
			int child = adj.get(node).get(k); // get child
			if (child == par) continue;
			for (int m = 0; m < 3; m++) { // color 
				if (m == col) continue;
				sum += func(child, m, node, col);
			}
//			System.out.println("child: " + child + " sum: " + sum);
			DP[node][col] *= sum;
			DP[node][col] %= MOD;
		}
//		System.out.println("Cbarn: " + node + " col: " + col + " par: " + par + " pcol: " + parColor + " numways: " + numWays);
		return DP[node][col];
	}
}
