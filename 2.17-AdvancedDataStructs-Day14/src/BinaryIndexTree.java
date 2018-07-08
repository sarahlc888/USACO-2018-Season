import java.io.IOException;

/*
 * BIT
 * fenwick tree
 * 1...n indexing REQUIRED, 0 just does nothing
 */
public class BinaryIndexTree {
	public static void main(String args[]) throws IOException {
		
	}
	public static class BIT {
		int[] a;
		int[] bit;
		int MAX;
		
		public BIT(int n) { // 1...n
			a = new int[n+1];
			bit = new int[n+1];
			MAX = n;
		}
		public void update(int i, int v) { // O(log N)
			// a[i] += v and proper updates everywhere else
			
			while (i <= MAX) { // start from i, it's <= if max == length precisely
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
		public int query(int i, int j) { // O(log N) (bc of the other call
			return (query(j)-query(i-1));
		}
	}
}
