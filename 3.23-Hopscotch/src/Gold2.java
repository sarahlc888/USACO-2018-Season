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
 * 
 * replaced BIT[] bit with treemap bit
 */
public class Gold2 {
	static int i;
	static int j;
	static int MOD = 1000000007;
	public static void main(String args[]) throws IOException {

		// INPUT
		BufferedReader br = new BufferedReader(new FileReader("hopscotch.in"));

		StringTokenizer st = new StringTokenizer(br.readLine());
		int R = Integer.parseInt(st.nextToken()); // rows
		int C = Integer.parseInt(st.nextToken()); // cols
//		int K = Integer.parseInt(st.nextToken()); // ids from 1...K

		int[][] grid = new int[R+1][C+1]; // 1...N indexing
		for (i = 1; i <= R; i++) { // scan everything in
			st = new StringTokenizer(br.readLine());
			for (j = 1; j <= C; j++) {
				grid[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		br.close();
		
		// BITs
		// map color (0 == all) to BIT of (column, value)
//		HashMap<Integer, BIT> bits = new HashMap<Integer, BIT>();
		TreeMap<Integer, BIT> bits = new TreeMap<Integer, BIT>();
		
		// DP[i][j] = sum of DP[a][b] for all a < i and b < j (if color is different)
		// not explicitly defined

		BIT b1 = new BIT(R+1);
		b1.update(1, 1); // basecase
		bits.put(0, b1); // general BIT
		
		BIT b2 = new BIT(R+1);
		b2.update(1, 1); // color grid[1][1], column 1, value 1
		bits.put(grid[1][1], b2);
		
		LinkedList<State> toUpdate = new LinkedList<State>();
		
		long ans = -1;
		
		for (i = 2; i <= R; i++) { // row 1 will be empty except (1, 1)
			for (int j = 1; j <= C; j++) {
				int color = grid[i][j];
				
				// all colors, vals in columns 1...j-1
				long val = bits.get(0).query(j-1); 

				if (bits.get(color) != null) { // TODO: make sure this work, otherwise check keyset
					// just the one color, vals in columns 1...j-1
					long colorval = bits.get(color).query(j-1); 
					val -= colorval;
				}
				
				val %= MOD;
				if (val < 0) val += MOD;
				

				toUpdate.add(new State(j, val));
				
				if (i == R && j == C) {
					ans = val;
				}
				
			}
			
			while (!toUpdate.isEmpty()) {
				State cur = toUpdate.removeFirst();
				int color = grid[i][cur.j];
				// update general BIT
				bits.get(0).update(cur.j, cur.val); 
				if (bits.get(color) == null)
					bits.put(color, new BIT(R+1));
			
				bits.get(color).update(cur.j, cur.val); 
			}
		}

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
		
		long[] bit;
		int MAX;
		
		public BIT(int n) { // 1...n
//			a = new long[n+1];
			bit = new long[n+1];
			MAX = n;
		}
		public void update(int i, long v) { // O(log N)
			// a[i] += v and proper updates everywhere else
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