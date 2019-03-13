import java.io.*;
import java.util.*;
/*
 * 
 */
public class S4BT {
	static long[] nt; // nt[i] = num trees with weight i (memoizing numTrees function)
	public static void main(String args[]) throws IOException {

		Scanner scan = new Scanner(System.in); 
		int N = Integer.parseInt(scan.next()); // weight
		scan.close();
		
		nt = new long[N+1];
		nt[1] = 1; // a single node
		
		System.out.println(numTrees(N));
//		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("S5.out")));
//
//		pw.println();
//		pw.close();
	}
	public static long numTrees(int w) {
//		System.out.println("w: " + w);
		if (nt[w] == 0) { // not yet processed
			
			long ct = 0;
			
			for (int k = 2; k <= w; k++) { // num subtrees
//				System.out.println("  k: " + k);
				int childWeight = w/k;
				ct += numTrees(childWeight);
			}
			
			nt[w] = ct;
		}
		
		return nt[w];
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