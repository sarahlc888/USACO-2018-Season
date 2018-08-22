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
 * replaced BIT[] bit with treemap bit
 * got rid of to update
 * 
 * had to look at solution to figure out the last 2 cases and have them pass
 * 
 * settled with 13/15 because the answer times out too!
 */
public class Gold3 {
	static int i;
	static int j;
	static int MOD = 1000000007;
	static int ind;
	
	public static void main(String args[]) throws IOException {

//		long t = System.currentTimeMillis();
		
		// INPUT
		BufferedReader br = new BufferedReader(new FileReader("hopscotch.in"));

		StringTokenizer st = new StringTokenizer(br.readLine());
		int R = Integer.parseInt(st.nextToken()); // rows
		int C = Integer.parseInt(st.nextToken()); // cols
		int K = Integer.parseInt(st.nextToken()); // ids from 1...K

		int[][] grid = new int[R+1][C+1]; // 1...N indexing
		for (i = 1; i <= R; i++) { // scan everything in
			st = new StringTokenizer(br.readLine());
			for (j = 1; j <= C; j++) {
				grid[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		br.close();
		
		// map color to the columns it's present in
		ArrayList<Integer>[] colcol = new ArrayList[K+1];
		for (int i = 0; i <= K; i++ ) colcol[i] = (new ArrayList<Integer>());
		
		for (j = 1; j <= C; j++) {
			for (i = 1; i <= R; i++) {
				int color = grid[i][j];
				if (colcol[color].isEmpty() || 
						colcol[color].get(colcol[color].size()-1) != j) {
					colcol[color].add(j); // add column
				}
			}
		}
		
		
		
//		System.out.println(colcol);
		
		// BITs
		// map color (0 == nothing) to BIT of (column, value)
		
//		HashMap<Integer, BIT> bits = new HashMap<Integer, BIT>();
		TreeMap<Integer, BIT> bits = new TreeMap<Integer, BIT>();
		
		
		for (int i = 1; i <= K; i++) { // loop through colors
			if (colcol[i].size() != 0) {
				bits.put(i, new BIT(colcol[i].size()+1));
			}
		}
		
		// DP[i][j] = sum of DP[a][b] for all a < i and b < j (if color is different)
		// not explicitly defined

		BIT gen = new BIT(R+1); // general BIT
		gen.update(1, 1); // basecase
		
		
		// find index of col 1 in colcol.get(grid[1][1]), ind >= 1
		ind = bSearch(colcol[grid[1][1]], 1) + 1; // scoot up the indexing for BIT
		// color grid[1][1], column 1, value 1
		bits.get(grid[1][1]).update(ind, 1);;
	
		long ans = -1;
		int[] added = new int[K+1];
		
		for (i = 2; i <= R; i++) { // row 1 will be empty except (1, 1)
			
			// reset added stuff
			for (int j = 0; j <= K; j++) {
				added[j] = 0;
			}
			
			for (j = 1; j <= C; j++) {
				int color = grid[i][j];
				
//				System.out.println("i: " + i + " j: " + j + " color: " + color);
				
				// all colors, vals in columns 1...j-1
				long val = gen.query(j-1); 

//				System.out.println("  first val: " + val);
				
				if (bits.get(color) != null) {
					// just the one color, vals in columns 1...j-1
					
					ind = greatestBelow(colcol[grid[i][j]], j);
					
					if (ind != -1) { // if found

						ind++; // scoot up the indexing for BIT

//						System.out.println("  ind: " + ind + " val: " + colcol.get(grid[i][j]).get(ind-1));

						long colorval = bits.get(color).query(ind); 
						val = val - colorval + added[color]; // add back in the stuff in this row (they don't count)
//						System.out.println("  colorval: " + colorval);
					}
				}
				
//				System.out.println("  row: " + added[0]);
				val -= added[0]; // subtract out all things previously added in this row
//				System.out.println("  val: " + val);
				
				while (val < 0) val += MOD;
				val %= MOD;
				
				
				added[0] += val;
				added[0] %= MOD;
				added[color] += val;
				added[color] %= MOD;
				
//				vals[i][j] = val;
				
				if (i == R && j == C) {
//					System.out.println("VAL: " + val);
					ans = val;
					break;
				}
				
				if (val == 0) continue;
				
				// update general BIT
				gen.update(j, val); 

				ind = bSearch(colcol[grid[i][j]], j) + 1; // scoot up the indexing for BIT
//				System.out.println(ind); // with proper index, ind should be >= 1
				
				bits.get(color).update(ind, val); 
			}
//			System.out.println(Arrays.toString(vals[i]));
		}

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("hopscotch.out")));
		System.out.println(ans);
		pw.println(ans);
		pw.close();
//		System.out.println(System.currentTimeMillis()-t);
	}
	public static int greatestBelow(ArrayList<Integer> arr, long val) {
		// returns greatest i < val

		int lo = 0;
		int hi = arr.size()-1;

		if (arr.get(lo) >= val) return -1;
		
		while (lo < hi) {
			
			int mid = (lo + hi + 1)/2; // +1 so lo can become hi in 1st if

//			System.out.println("    mid: " + mid + " val: " + arr.get(mid));
			
			if (arr.get(mid) < val) { // if mid is in range, increase
				lo = mid;
			} else { // mid is not in range, decrease
				hi = mid - 1;
			}
		}
		if (arr.get(hi) < val) return hi;
		return -1;
	}
	public static int bSearch(ArrayList<Integer> arr, long val) {
		// returns smallest i >= val
		int lo = 0;
		int hi = arr.size()-1;
		int mid = 0;
		
		if (arr.get(lo) > val || arr.get(hi) < val) return -1;
		
		while (lo <= hi) {
			mid = (lo + hi)/2; // no +1 because you want hi to become lo in 1st if
//			System.out.println("lo: " + lo + " hi: " + hi + " mid: " + mid);

			if (arr.get(mid) > val) { // if mid is in range
				hi = mid-1;
			} else if (arr.get(mid) < val){ // mid is not in range
				lo = mid+1;
			} else { // == val
				break;
			}
		}
		if (arr.get(mid) == val) return mid; // make sure it works
		return -1;
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
	}

}