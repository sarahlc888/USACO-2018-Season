import java.io.*;
import java.util.*;
/*
 * 10/10 test cases
 * loop through cows from tallest to shortest, get BIT sum on either side
 * 1...N indexing
 */
public class BalancedPhotoBIT {
	public static void main(String args[]) throws IOException {
		// INPUT
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		
		Pair[] sortedh = new Pair[N]; // cows sorted by height (pos, height)
		
		for (int i = 0; i < N; i++) {
			sortedh[i] = new Pair(i+1, Integer.parseInt(br.readLine()));
		}
		br.close();
		
		Arrays.sort(sortedh);
		
		// binary index tree for all cows
		BIT b = new BIT(N);
		int count = 0;
		
		// loop through the cows from tallest to shortest
		for (int i = 1; i <= N; i++) {
			Pair cur = sortedh[i-1]; 

		//	System.out.println("cur: " + cur );
//			
		//	System.out.println("  " + Arrays.toString(b.a));
			b.update(cur.x, 1); // update index with a 1 to mark 1 taller cow
			//System.out.println("  " + Arrays.toString(b.a));
			int left = b.query(cur.x-1); // number of taller cows on the right
			int right = b.query(cur.x+1, N); // number of taller cows on the right
			//System.out.println("  left: " + left);
			//System.out.println("  right: " + right);
			
			if (left > right*2 || right > left*2) {
			//	System.out.println("HERE");
				count++;
			}
		}
		System.out.println(count);
	}
	public static class BIT {
		int[] a; // values
		int[] bit; // sums
		int MAX;
		public BIT(int n) { // 1...n
			a = new int[n+1];
			bit = new int[n+1];
			MAX = n;
		}
		public void update(int i, int v) { // O(log N)
			// a[i] += v and proper updates everywhere else
			a[i] += v; // TODO: comment out if you don't want to change a
			while (i <= MAX) { // start from i
				bit[i] += v;
				i += (i & (-i)); // add least significant bit (it works out)
			}
		}
		public int query(int i) { // O(log N)
			// returns sum of a[1]... a[i]
			
			int sum = 0;
			while (i > 0) { // start from i
				sum += bit[i];
				i -= (i & (-i)); // subtract least significant bit (it works out)
			}
			return sum;
		}
		public int query(int i, int j) { // O(log N) (bc of the other call)
			// inclusive
			//System.out.println("start: " + i + " end: " + j);
			return (query(j)-query(i-1));
		}
	}
	public static class Pair implements Comparable<Pair> {
		int x; 
		int y; 

		public Pair(int a, int b) {
			x = a;
			y = b;
		}
		@Override
		public int compareTo(Pair o) { // sort by y
			if (y == o.y) {
				return o.x-x;
			}
			return o.y-y;
		}
		public String toString() {
			return "pos: " + x + "  height: " + y ;
		}
		public boolean equals(Pair o) {
			return (x == o.x && y == o.y);
		}

	}
}
