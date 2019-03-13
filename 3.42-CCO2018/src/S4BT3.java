import java.io.*;
import java.util.*;
/*
 * 
 */
public class S4BT3 {
	static int MAXLEN = 9977799;
	static long[] nt; // nt[i] = num trees with weight i (memoizing numTrees function)
	public static void main(String args[]) throws IOException {

		Scanner scan = new Scanner(System.in); 
		int N = Integer.parseInt(scan.next()); // weight
		scan.close();

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
				
				int k = 2; // num subtrees
				
				while (k <= w) {
					
					int childWeight = w/k;
//					System.out.println("  k: " + k + " wt: " + childWeight);
					int curAmt = 0;
					
					
					while (w/k == childWeight) {
//						System.out.println("    k: " + k);
						curAmt++;
						k++;
					}
					
					long curNT = numTrees(childWeight);
					System.out.println("    w: "  + w + " curAmt: " + curAmt + " curWeight: " + childWeight + " curNT: " + curNT);
					ct += curAmt*curNT;
					
//					System.out.println("  increment ct: " + ct);
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