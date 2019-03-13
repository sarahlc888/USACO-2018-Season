import java.util.*;
import java.io.*;
/*
 * dysfunctional
 * BIT
 * fenwick tree
 * 1...n indexing REQUIRED, 0 just does nothing
 */
public class BITMin {
	public static void main(String args[]) throws IOException {
		int[] arr = {2, 8, 9, 6};
		BIT bit = new BIT(arr.length, arr);
		System.out.println(bit.query(1));
		bit.updateVerbose(1, 3);
		System.out.println(bit.query(1));
	}
	public static class BIT { // 1...n indexing REQUIRED, 0 just does nothing
		int[] a;
		int[] bit;
		int MAX;
		
		public BIT(int n) { // 1...n
			a = new int[n+1];
			bit = new int[n+1];
			MAX = n;
		}
		public BIT(int n, int[] arrIn) { // 1...n
			a = new int[n+1];
			bit = new int[n+1];
			MAX = n;
			
			if (arrIn.length == n) { // 0...n-1 indexing, need to convert
				for (int i = 0; i < n; i++) update(i+1, arrIn[i]);
			} else if (arrIn.length == n+1) { // 1...n indexing, no need to convert
				for (int i = 1; i <= n; i++) update(i, arrIn[i]);
			}
		}
		public void update(int i, int v) { // O(log N)
			int oldVal = bit[i];
			while (i <= n+1) {
				  if (v > bit[i]) {
				    if (oldVal == bit[i]) {
				      v = min(v, a[i])
				      for-each child {
				        v = min(v, bit[child])
				      }
				    } else break
				  }
				  if (v == bit[i]) break
				  bit[i] = v
				  i = parentOf(i, bit)
				}
		}
		public int query(int i) { // O(log N)
			// returns sum of a[1]... a[i]
			// different view of the tree
			int sum = 0;
			while (i > 0) { // start from i
				sum += bit[i];
				i -= (i & (-i)); // subtract least significant bit (it works out)
				// that brings you to the "parent" 
				// bit[i] has sum of parent+1 to i
				// will eventually get back to root 0
			}
			return sum;
		}
		public int query(int i, int j) { // O(log N) (bc of the other call
			return (query(j)-query(i-1));
		}
	}
}
