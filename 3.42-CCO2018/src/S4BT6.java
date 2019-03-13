import java.io.*;
import java.util.*;
/*
 * 12/28
 * 15/15 after lesson
 * Based off of Dr. Ming's code
 */
public class S4BT6 {
	static int MAXLEN = 9999999;
	static HashMap<Integer, Long> nt = new HashMap<Integer, Long>();
	// nt[i] = num trees with weight i (memoizing numTrees function)
	
	public static void main(String args[]) throws IOException {
		Scanner scan = new Scanner(System.in); 
		int N = Integer.parseInt(scan.next()); // weight
		scan.close();

		nt.put(1, 1l); // a single node
		nt.put(2, 1l); // must have at least 2 children
		nt.put(3, 2l); // 2 or 3 children with weight 1 each
		// ^put the 3 here to reduce recursion, need it to pass
		System.out.println(numTrees(N));
	}
	static long ct;
	public static long numTrees(int w) {
		if (nt.containsKey(w)) return nt.get(w); // memoize
//		System.out.println("w: " + w);
		
		// from 2 to m children, few duplicates, just go through
		ct = 0;
		int m = (int)Math.sqrt(w);
		for (int j = 2; j <= m; j++) { 
			ct += numTrees(w/j);
		}
//		System.out.println(w+ " ct1: " + ct);
		// from m+1 to w/2 children, many duplicates, group them instead of recalculating
		int k = m+1; // num children
		while (k <= w/2) {
			
			int nextWeight = w/k; // weight for k children
			int maxChildren = w/nextWeight; // max number of children possible with nextWeight
			
//			System.out.println("  k: " + k + " nw: " + nextWeight + " mcwtw: " + maxChildrenWithThatWeight);

			ct += (maxChildren-k+1) *numTrees(nextWeight); // possibilities
			k = maxChildren+1; 
		}
//		System.out.println(w+ " ct2: " + ct);
		// >w/2 children, all have numTrees(w) == 1
		ct += (w+1)/2;

//		System.out.println(w+ " ct3: " + ct);
		nt.put(w, ct);
		return ct;
	}
}