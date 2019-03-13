import java.io.*;
import java.util.*;
/*
 * 
 */
public class S4BT2 {
	static int MAXLEN = 9977799;
	static long[] nt; // nt[i] = num trees with weight i (memoizing numTrees function)
	public static void main(String args[]) throws IOException {

//		Scanner scan = new Scanner(System.in); 
//		int N = Integer.parseInt(scan.next()); // weight
//		scan.close();
		
		int N = 5;
		
		nt = new long[Math.min(MAXLEN, N+1)];
		nt[1] = 1; // a single node
		
		System.out.println(numTrees(N));
//		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("S5.out")));
//
//		pw.println();
//		pw.close();
	}
	public static long numTrees(int w) {
//		System.out.println("w: " + w);
		if (w < MAXLEN) {
			if (nt[w] == 0) { // not yet processed
				
				long ct = 0;
				
				for (int k = 2; k <= w; k++) { // num subtrees
					
					int childWeight = w/k;
					long curNT = numTrees(childWeight);
					ct += curNT;
					System.out.println("  childWeight: " + childWeight + " curNT: " + curNT);
				}
				
				nt[w] = ct;
			}
			
			return nt[w];
		} else {
			long ct = 0;
			
			for (int k = 2; k <= w; k++) { // num subtrees
//				System.out.println("  k: " + k);
				int childWeight = w/k;
				ct += numTrees(childWeight);
			}
			return ct;
		}
	}
}