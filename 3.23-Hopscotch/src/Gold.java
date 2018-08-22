import java.io.*;
import java.util.*;

/*
 * USACO 2015 February Contest, Gold
 * Problem 1. Cow Hopscotch (Gold)
 * 
 * Similar idea as silver but uses BITs
 * first 3 test cases from the silver program
 * 
 * had to look at answer but this isn't exactly the same
 * 
 * 13/15, last two time out
 */
public class Gold {

	static int MOD = 1000000007;
	public static void main(String args[]) throws IOException {

		// INPUT
		BufferedReader br = new BufferedReader(new FileReader("hopscotch.in"));

		StringTokenizer st = new StringTokenizer(br.readLine());
		int R = Integer.parseInt(st.nextToken()); // rows
		int C = Integer.parseInt(st.nextToken()); // cols
		int K = Integer.parseInt(st.nextToken()); // ids from 1...K

		int[][] grid = new int[R+1][C+1]; // 1...N indexing
		for (int i = 1; i <= R; i++) { // scan everything in
			st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= C; j++) {
				grid[i][j] = Integer.parseInt(st.nextToken());
			}
//			System.out.println(Arrays.toString(grid[i]));
		}
		br.close();

//		System.out.println();
		
		// BITs
		BIT[] bits = new BIT[K+1]; // [color, 0 = all] -> column, value
		bits[0] = new BIT(R+1);
		
		
		// DP[i][j] = sum of DP[a][b] for all a < i and b < j (if color is different)
		// not explicitly defined

		bits[0].update(1, 1); // basecase in general BIT
		bits[grid[1][1]] = new BIT(R+1);
		bits[grid[1][1]].update(1, 1); // color grid[1][1], column 1, value 1
		
		LinkedList<State> toUpdate = new LinkedList<State>();
		
//		long[][] vals = new long[R+1][C+1];
		
		long ans = -1;
		
		for (int i = 2; i <= R; i++) { // row 1 will be empty except (1, 1)
			for (int j = 1; j <= C; j++) {
//				if (i == R && j == C) continue; // end case
				int color = grid[i][j];
				
//				System.out.println("i: " + i + " j: " + j + " color: " + color);

				// all colors, vals in columns 1...j-1
				long val = bits[0].query(j-1); 

				long colorval = 0;
				if (bits[color] != null) {
					// just the one color, vals in columns 1...j-1
					colorval = bits[color].query(j-1); 
					val -= colorval;
				}
				
				val %= MOD;
				if (val < 0) val += MOD;
				
//				System.out.println("    val: " + val);

				toUpdate.add(new State(j, val));
				
//				vals[i][j] = val;
				if (i == R && j == C) {
//					System.out.println("VAL: " + val);
					ans = val;
					break; // end case
				}
				
			}
			
			while (!toUpdate.isEmpty()) {
				State cur = toUpdate.removeFirst();
				int color = grid[i][cur.j];
				// update general BIT
				bits[0].update(cur.j, cur.val); 
				if (bits[color] == null)
					bits[color] = new BIT(R+1);
			
				bits[color].update(cur.j, cur.val); 
			}
		}
//		for (int i = 1; i <= R; i++) {
//			System.out.println(Arrays.toString(vals[i]));
//		}
//		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("hopscotch.out")));
//		System.out.println(ans);
		pw.println(ans);
		pw.close();
	}
	public static class State {

		int j;
		long val;

		public State(int b, long c) {

			j = b;
			val = c;
		}
		public String toString() {
			return j + " " + val;
		}
	}

	public static class BIT {
		// 1...n indexing REQUIRED, 0 just does nothing
		
		long[] a;
		long[] bit;
		int MAX;
		
		public BIT(int n) { // 1...n
			a = new long[n+1];
			bit = new long[n+1];
			MAX = n;
		}
		public void update(int i, long v) { // O(log N)
			// a[i] += v and proper updates everywhere else
			a[i] += v;
			while (i <= MAX) { // start from i, it's <= if max == length precisely
				bit[i] += v;
				i += (i & (-i)); // add least significant bit (it works out)
			}
		}
		public long query(int i) { // O(log N)
			// returns sum of a[1]... a[i]
			
			long sum = 0;
			while (i > 0) { // start from i
				sum += bit[i];
				i -= (i & (-i)); // subtract least significant bit (it works out)
			}
			return sum;
		}
		public long query(int i, int j) { // O(log N) (bc of the other call
			return (query(j)-query(i-1));
		}
	}

}