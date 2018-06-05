import java.io.*;
import java.util.*;

// bug: didn't deal with multiple diamonds of the same size
// uses the same binary search as counting haybales but modified to account for duplicates
// 10/10 test cases (almost timed out - had to use buffered reader)

public class DiamondCollector {
	static int[] diamonds;
	/*public static void main(String args[]) throws IOException {
		for (int i = 1; i <= 10; i++) {
			String filename = "testData/" + i + ".in";
			processOneFile(filename);
		}
	}*/
	public static void main(String args[]) throws IOException {
	//public static void processOneFile(String filename) throws IOException {
		//Scanner scan = new Scanner(new File(filename));
		BufferedReader br = new BufferedReader(new FileReader("diamond.in"));
		//Scanner scan = new Scanner(new File("diamond.in"));
		//BufferedReader br = new BufferedReader(new FileReader(filename));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
	
		diamonds = new int[N]; // stores sizes of the diamonds
		for (int i = 0; i < N; i++) {
			diamonds[i] = Integer.parseInt(br.readLine());
		}
		br.close();
		
		Arrays.sort(diamonds);
		
		PrintWriter pw = new PrintWriter(new File("diamond.out"));
		if (diamonds[N-1] - diamonds[0] <= K) {
			pw.println(N);
			return;
		}
		
		int maxDC = 0; // max number of diamonds able to be displayed in two cases
		
		int[] kInterval = new int[N]; 
		// kInterval[i] = end of interval (inclusive) starting from diamonds[i]
		for (int i = 0; i < N; i++) {
			kInterval[i] = -1;
		}
		for (int i = 0; i < N; i++) {
			// starting point is diamonds[i], intervals are diamonds[L1, H1] and [L2, H2]
			
			// index of the beginning and end of the first interval
			int L1 = i;
			int H1;
			// check to see if the kInteval[L1] has already been calculated
			if (kInterval[i] > -1) { // if it is calculated
				H1 = kInterval[L1];
			} else {
				// binary search for H1
				H1 = bSearchSmallSide(diamonds[L1] + K, diamonds.length-1, 0);
				kInterval[i] = H1;
			}
			
			int D1 = H1 - L1 + 1; // number of diamonds in the first interval
			
			for (int j = 1; j < N; j++) { 
				if (H1 + j >= N) {
					break;
				}
				// j-1 = amount of space / number of indices between H1 and L2
				
				int L2 = H1 + j;
				int H2;
				if (kInterval[L2] > -1) {
					H2 = kInterval[L2];
				} else {
					H2 = bSearchSmallSide(diamonds[L2] + K, diamonds.length-1, 0);
					kInterval[L2] = H2;
				}
				
				// catch overflow of the edges
				if (H2 >= N) {
					H2 = N-1;
				}
				
				int DC = D1 + H2 - L2 + 1; // final diamond count
				
				if (DC > maxDC) { // update
					maxDC = DC;
				}

			}
		}
		pw.println(maxDC);
		pw.close();
		System.out.println(maxDC);
	}
	
	public static int bSearchSmallSide(int hbound, int hi, int lo) {
		// returns the index of the largest entry less than or equal to hbound
		// values -- basecases for when hi - lo range reduced already
		
		// exception catching
		if (hbound >= diamonds[hi]) { // hi must be a valid index, inclusive!
			return hi; 
		} else if (hbound < diamonds[lo]) { // no point are in the interval
			return -1;
		}
		
		// basecase for when hi - lo range is 1
		// now hbound < pts[hi] and hbound >= pts[lo]
		if (hi - lo == 1) {
			return lo;
		}
		
		int mid = (hi + lo)/2;
		
		// narrow down
		if (diamonds[mid] < hbound) {
			// go bigger
			lo = mid; // keep mid in the pool of possible answers
			return bSearchSmallSide(hbound, hi, lo);
		} else if (diamonds[mid] > hbound) {
			// go smaller
			//hi = mid - 1;
			hi = mid;
			return bSearchSmallSide(hbound, hi, lo);
		} else {
			while (diamonds[mid] == diamonds[mid+1]) mid++; // get the highest possible ind for same val
			
			return mid;
			// last case is that pts[mid] == hbound --> desired result
		}
		
	}
}
