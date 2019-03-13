import java.io.*;
import java.util.*;
/*
 * USACO 2018 January Contest, Gold
 * Problem 3. Stamp Painting
 */
public class StampPainting {
	public static void main(String args[]) throws IOException {
		long MOD = 1000000007;
		BufferedReader br = new BufferedReader(new FileReader("spainting.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); // length of canvas
		int M = Integer.parseInt(st.nextToken()); // num colors
		int K = Integer.parseInt(st.nextToken()); // length of each stamp
		br.close();
		
		// DP[i] = num ways to paint up to i without having k consecutive of one color
		long[] DP = new long[N+1]; 
		DP[1] = M; // basecase (any of the M colors for first slot)
		for (int i = 2; i <= N; i++) {
			if (i < K) {
				DP[i] = (DP[i-1]*M)%MOD;
			} else {
				DP[i] = 0;
				for (int j = Math.max(0, i-K+1); j < i; j++) {
					DP[i] += DP[j]*(M-1)%MOD;
					DP[i] %= MOD;
				}
			}
		}
//		System.out.println(Arrays.toString(DP));
		long total = 1;
		for (int i = 0; i < N; i++) {
			total *= M;
			total %= MOD;
		}
//		System.out.println(total);
//		System.out.println(DP[N]);
		
		long val = (total-DP[N])%MOD;
		while (val < 0) val+= MOD;
		
//		System.out.println(val);

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("spainting.out")));
		pw.println(val);
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
