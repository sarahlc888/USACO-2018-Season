import java.io.*;
import java.util.*;
/*
 * 
 */
public class S4BT4 {
	static int MAXLEN = 9999999;
	static HashMap<Integer, Long> ntMap = new HashMap<Integer, Long>();
	static long[] nt; // nt[i] = num trees with weight i (memoizing numTrees function)
	public static void main(String args[]) throws IOException {

		Scanner scan = new Scanner(System.in); 
		int N = Integer.parseInt(scan.next()); // weight
		scan.close();

		nt = new long[Math.min(MAXLEN, N+1)];
		nt[1] = 1; // a single node
		
		System.out.println(numTrees(N));
	}
	static long ct;
	static int i;
	public static long numTrees(int w) {
		
		if (w < MAXLEN) {
			if (nt[w] == 0) { // not yet processed
//				System.out.println("w: " + w);
				ct = 0;
				
				for (int i = 1; i <= w/2; i++) {
					
					if ((w/i)*(i+1) > w)
						ct += ( w/i-w/(i+1) ) *numTrees(i);
				}
				nt[w] = ct;
//				System.out.println("w: " + w + " ct: " + ct);
			}
			
			return nt[w];
		} else {
			if (!ntMap.containsKey(w)) {
//				System.out.println("w: " + w);
				ct = 0;
				
				for (int i = 1; i <= w/2; i++) {
					
					if ((w/i)*(i+1) > w)
						ct += ( w/i-w/(i+1) ) *numTrees(i);
				}
				ntMap.put(w, ct);
			}
			return ntMap.get(w);
		}
	}
}